package befaster.solutions.CHK;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CheckoutSolution {

    record SpecialOffer(int quantity, int newPrice) {
    }

    record FreeItem(int quantity, String freeSku) {
    }

    static class ItemProcessed {
        private final int price;
        private int quantity = 0;

        private int currentValueInCart = 0;
        private SpecialOffer specialOffer = null;

        public ItemProcessed(int price) {
            this.price = price;
        }

        public ItemProcessed(int price, SpecialOffer specialOffer) {
            this.price = price;
            this.specialOffer = specialOffer;
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

        public SpecialOffer getSpecialOffer() {
            return specialOffer;
        }

        public void setCurrentValueInCart(int currentValueInCart) {
            this.currentValueInCart = currentValueInCart;
        }

        public void add() {
            this.quantity++;
            this.setCurrentValueInCart(calculateNewValue());
        }

        private int calculateNewValue() {
            if (this.getSpecialOffer() != null) {
                final var quantityToDiscount = (int) Math.floor((double) this.getQuantity() / this.getSpecialOffer().quantity());
                final var discountedPrice = quantityToDiscount * this.getSpecialOffer().newPrice();
                final var numberOfOffers = (quantityToDiscount * this.getSpecialOffer().quantity());
                final var remainingPrice = (this.getQuantity() - numberOfOffers) * this.getPrice();
                return discountedPrice + remainingPrice;
            }
            return this.getQuantity() * this.getPrice();
        }
    }

    final Map<String, FreeItem> freeItems = new HashMap<>() {{ //TODO create a way to discover the free item
        put("E", new FreeItem(2, "B"));
    }};

    public Integer checkout(String skus) {

        final Map<String, ItemProcessed> cart = new HashMap<>() {{
            put("A", new ItemProcessed(50, new SpecialOffer(3, 130)));
            put("B", new ItemProcessed(30, new SpecialOffer(2, 45)));
            put("C", new ItemProcessed(20));
            put("D", new ItemProcessed(15));
            put("E", new ItemProcessed(40));
        }};

        if (skus == null) {
            return -1;
        } else if (skus.trim().isEmpty()) {
            return 0;
        }

        try {
            Arrays.stream(skus.split(""))
                    .map(sku -> validateIfKeyIsValid(cart, sku))
                    .map(cart::get)
                    .forEach(ItemProcessed::add);
            return cart.values()
                    .stream()
                    .map(ItemProcessed::getCurrentValueInCart)
                    .reduce(0, Integer::sum);

        } catch (final RuntimeException e) {
            return -1;
        }
    }

    private String validateIfKeyIsValid(final Map<String, ItemProcessed> cart, final String sku) {
        if (cart.containsKey(sku)) {
            return sku;
        }
        throw new RuntimeException("Error Invalid Sku");
    }
}










