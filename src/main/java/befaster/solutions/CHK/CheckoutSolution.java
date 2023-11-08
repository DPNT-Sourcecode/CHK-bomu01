package befaster.solutions.CHK;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CheckoutSolution {

    final static Map<String, FreeItem> freeItems = new HashMap<>() {{
        put("E", new FreeItem(2, "B"));
        put("F", new FreeItem(3, "F"));
        put("N", new FreeItem(3, "M"));
        put("R", new FreeItem(3, "Q"));
        put("U", new FreeItem(4, "U"));
    }};

    final static GroupedItems groupedItems = new GroupedItems(3, 45, List.of("S", "T", "X", "Y", "Z"));

    private static HashMap<String, ItemProcessed> generateNewCart() {
        return new HashMap<>() {{
            put("A", new ItemProcessed(50, List.of(
                    new SpecialOffer(5, 200),
                    new SpecialOffer(3, 130))));
            put("B", new ItemProcessed(30, List.of(new SpecialOffer(2, 45))));
            put("C", new ItemProcessed(20));
            put("D", new ItemProcessed(15));
            put("E", new ItemProcessed(40));
            put("F", new ItemProcessed(10));
            put("G", new ItemProcessed(20));
            put("H", new ItemProcessed(10, List.of(
                    new SpecialOffer(10, 80),
                    new SpecialOffer(5, 45))));
            put("I", new ItemProcessed(35));
            put("J", new ItemProcessed(60));
            put("K", new ItemProcessed(70, List.of(new SpecialOffer(2, 120))));
            put("L", new ItemProcessed(90));
            put("M", new ItemProcessed(15));
            put("N", new ItemProcessed(40));
            put("O", new ItemProcessed(10));
            put("P", new ItemProcessed(50, List.of(new SpecialOffer(5, 200))));
            put("Q", new ItemProcessed(30, List.of(new SpecialOffer(3, 80))));
            put("R", new ItemProcessed(50));
            put("S", new ItemProcessed(20));
            put("T", new ItemProcessed(20));
            put("U", new ItemProcessed(40));
            put("V", new ItemProcessed(50, List.of(
                    new SpecialOffer(3, 130),
                    new SpecialOffer(2, 90))));
            put("W", new ItemProcessed(20));
            put("X", new ItemProcessed(17));
            put("Y", new ItemProcessed(20));
            put("Z", new ItemProcessed(21));
        }};
    }

    record SpecialOffer(int quantity, int newPrice) {
    }

    record FreeItem(int quantity, String freeSku) {
    }

    record GroupedItems(int quantity, int newPrice, List<String> skus) {
    }

    static class ItemProcessed {
        private final int price;
        private int quantity = 0;
        private int currentValueInCart = 0;
        private List<SpecialOffer> specialOffers = List.of();

        public ItemProcessed(final int price) {
            this.price = price;
        }

        public ItemProcessed(final int price, List<SpecialOffer> specialOffers) {
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

        public void setCurrentValueInCart(final int currentValueInCart) {
            this.currentValueInCart = currentValueInCart;
        }

        public ItemProcessed add() {
            this.quantity++;
            this.setCurrentValueInCart(calculateNewValue(this.quantity));
            return this;
        }

        private void checkForFreeItems(final String sku, final Map<String, ItemProcessed> cart) {
            if (freeItems.containsKey(sku) && this.getQuantity() >= freeItems.get(sku).quantity()) {
                int quantity = (int) Math.floor((double) this.getQuantity() / freeItems.get(sku).quantity());
                cart.get(freeItems.get(sku).freeSku())
                        .setCurrentValueInCart(calculateNewValue(this.getQuantity() - quantity));
            }
        }

        public void remove(final int quantityToRemove) {
            if (quantityToRemove <= 0) {
            } else if (this.quantity - quantityToRemove <= 0) {
                this.quantity = this.quantity - quantityToRemove;
                this.setCurrentValueInCart(0);
            } else {
                this.quantity = this.quantity - quantityToRemove;
                this.setCurrentValueInCart(calculateNewValue(this.quantity));
            }
        }

        private int calculateNewValue(final int quantity) {
            if(quantity <= 0){
                return 0;
            }
            if (this.getSpecialOffer().isEmpty()) {
                return quantity * this.getPrice();
            }
            int remainingQuantity = quantity;
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
            processSkus(skus, cart);
            //removeFreeItems(cart); //TODO change this to stream
            return calculateNonGroupedItems(cart) + calculateGroupedItems(cart);

        } catch (final RuntimeException e) {
            return -1;
        }
    }

    private Integer calculateGroupedItems(final Map<String, ItemProcessed> cart) {
        final var groupedItemsCart = cart.entrySet()
                .stream()
                .filter(e -> groupedItems.skus().contains(e.getKey()))
                .map(Map.Entry::getValue)
                .toList();
        final var quantityOfGroupedItems = groupedItemsCart.stream()
                .map(ItemProcessed::getQuantity)
                .reduce(0, Integer::sum);
        final var numberOfDiscounts = Math.floor((double) quantityOfGroupedItems / groupedItems.quantity());
        var quantityLeft = new AtomicInteger((int) numberOfDiscounts * groupedItems.quantity());
        var finalValue = new AtomicInteger((int) numberOfDiscounts * groupedItems.newPrice());

        groupedItemsCart
                .stream()
                .sorted(Comparator.comparing(ItemProcessed::getPrice, Comparator.reverseOrder()))
                .forEach(i -> {
                    var itemQuantity = i.getQuantity();
                    i.remove(quantityLeft.get());
                    quantityLeft.set(quantityLeft.get() - itemQuantity);
                    finalValue.set(finalValue.get() + i.getQuantity() * i.getPrice());
                });

        return finalValue.get();
    }

    private Integer calculateNonGroupedItems(final Map<String, ItemProcessed> cart) {
        return cart.entrySet()
                .stream()
                .filter(e -> !groupedItems.skus().contains(e.getKey()))
                .map(Map.Entry::getValue)
                .map(ItemProcessed::getCurrentValueInCart)
                .reduce(0, Integer::sum);
    }

    private void processSkus(final String skus, final Map<String, ItemProcessed> cart) {
        Arrays.stream(skus.split(""))
                .map(sku -> validateIfKeyIsValid(cart, sku))
                .forEach(key -> cart.get(key).add().checkForFreeItems(key, cart));
    }

    private Map<String, ItemProcessed> removeFreeItems(final Map<String, ItemProcessed> cart) {
        cart.entrySet()
                .stream()
                .filter(entry -> freeItems.containsKey(entry.getKey()))
                .filter(entry -> entry.getValue().getQuantity() >= freeItems.get(entry.getKey()).quantity())
                .filter(entry -> cart.get(freeItems.get(entry.getKey()).freeSku()).getQuantity() > 0)
                .forEach(entry -> cart.get(freeItems.get(entry.getKey()).freeSku()).remove(calculateQuantityToRemove(entry)));
        return cart;
    }

    private int calculateQuantityToRemove(final Map.Entry<String, ItemProcessed> entry) {
        return (int) Math.floor((double) entry.getValue().getQuantity() / freeItems.get(entry.getKey()).quantity());
    }

    private String validateIfKeyIsValid(final Map<String, ItemProcessed> cart, final String sku) {
        if (cart.containsKey(sku)) {
            return sku;
        }
        throw new RuntimeException("Error Invalid Sku");
    }
}













