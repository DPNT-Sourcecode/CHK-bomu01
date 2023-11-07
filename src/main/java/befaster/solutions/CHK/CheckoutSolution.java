package befaster.solutions.CHK;

import java.util.Arrays;

public class CheckoutSolution {




    public Integer checkout(String skus) {

        if (skus == null) {
            return -1;
        } else if (skus.equals("")) { //check this
            return 0;
        }




        return 0; //TODO
    }

    public static void main(String[] args) {
        Arrays.stream("ABC".split("")).peek(a -> System.out.println(a)).toList();
    }
}








