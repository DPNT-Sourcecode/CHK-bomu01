package befaster.solutions.CHK;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutSolution {

    final static Map<String, FreeItem> freeItems = new HashMap<>() {{
        put("E", new FreeItem(2, "B"));
    }};

    private static HashMap<String, ItemProcessed> generateNewCart() {
        return new HashMap<>() {{
            put("A", new ItemProcessed(50, List.of(
                    new SpecialOffer(5, 200),
                    new SpecialOffer(3, 130))));
            put("B", new ItemProcessed(30, List.of(new SpecialOffer(2, 45))));
            put("C", new ItemProcessed(20));
            put("D", new ItemProcessed(15));
            put("E", new ItemProcessed(40));
        }};
    }

    record SpecialOffer(int quantity, int newPrice) {
    }

    record FreeItem(int quantity, String freeSku) {
    }

    static class ItemProcessed {
        private final int price;
        private int quantity = 0;
        private int currentValueInCart = 0;
        private List<SpecialOffer> specialOffers = List.of();

        public ItemProcessed(int price) {
            this.price = price;
        }

        public ItemProcessed(int price, List<SpecialOffer> specialOffers) {
            this.price = price;
            this.specialOffers = specialOffers;
        }

        public int getCurrentValueInCart() {
            return currentValueInCart;
        }

        public int getPrice() {
            return price;
        }

        public int getQuantity() {
            return quantity;
        }

        public List<SpecialOffer> getSpecialOffer() {
            return specialOffers;
        }

        public void setCurrentValueInCart(int currentValueInCart) {
            this.currentValueInCart = currentValueInCart;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public void add() {
            this.quantity++;
            this.setCurrentValueInCart(calculateNewValue());
        }

        public void remove() {
            this.quantity--;
            this.setCurrentValueInCart(calculateNewValue());
        }

        private int calculateNewValue() {
            if (this.getSpecialOffer().isEmpty()) {
                return this.getQuantity() * this.getPrice();
            }
            int remainingQuantity = this.getQuantity();
            int currentValue = 0;
            for (final SpecialOffer offer : this.getSpecialOffer()) {
                final var quantityToDiscount = (int) Math.floor((double) remainingQuantity / offer.quantity());
                remainingQuantity = remainingQuantity - (quantityToDiscount * offer.quantity());
                currentValue = currentValue + (quantityToDiscount * offer.newPrice());
            }
            return currentValue + remainingQuantity * this.getPrice();
        }
    }

    public Integer checkout(String skus) {
        if (skus == null) {
            return -1;
        } else if (skus.trim().isEmpty()) {
            return 0;
        }
        try {
            final Map<String, ItemProcessed> cart = generateNewCart();
            Arrays.stream(skus.split(""))
                    .map(sku -> validateIfKeyIsValid(cart, sku))
                    .map(cart::get)
                    .forEach(ItemProcessed::add);
            return removeFreeItems(cart)
                    .values()
                    .stream()
                    .map(ItemProcessed::getCurrentValueInCart)
                    .reduce(0, Integer::sum);

        } catch (final RuntimeException e) {
            return -1;
        }
    }

    private Map<String, ItemProcessed> removeFreeItems(final Map<String, ItemProcessed> cart) {
        cart.entrySet()
                .stream()
                .filter(entry -> freeItems.containsKey(entry.getKey()))
                .filter(entry -> entry.getValue().getQuantity() >= freeItems.get(entry.getKey()).quantity())
                .filter(entry -> cart.get(freeItems.get(entry.getKey()).freeSku()).getQuantity() > 0)
                .forEach(entry -> cart.get(freeItems.get(entry.getKey()).freeSku()).remove());

        return cart;
    }

    private String validateIfKeyIsValid(final Map<String, ItemProcessed> cart, final String sku) {
        if (cart.containsKey(sku)) {
            return sku;
        }
        throw new RuntimeException("Error Invalid Sku");
    }
}









