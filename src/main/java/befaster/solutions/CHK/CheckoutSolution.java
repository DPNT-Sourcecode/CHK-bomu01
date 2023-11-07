package befaster.solutions.CHK;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CheckoutSolution {

    record SpecialOffer(int quantity, int price) {
    }

    static class ItemProcessed {
        private int price;
        private int quantity = 0;
        private SpecialOffer specialOffer = null;

        public ItemProcessed(int price) {
            this.price = price;
        }

        public ItemProcessed(int price, SpecialOffer specialOffer) {
            this.price = price;
            this.specialOffer = specialOffer;
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

        public void setQuantity(int quantity) {
            this.quantity = quantity; //todo
        }
    }


    private Map<String, Objects> store = new HashMap<>() {{
        put("A", );
        put("B", );
        put("C", );
        put("D", );
    }};


    public Integer checkout(String skus) {

        if (skus == null) {
            return -1;
        } else if (skus.equals("")) { //check this
            return 0;
        }

        Arrays.stream(skus.split(""))
                .toList();


        return 0; //TODO
    }

    /*public static void main(String[] args) {
        //Arrays.stream("ABC".split("")).peek(a -> System.out.println(a)).toList();
    }*/
}










