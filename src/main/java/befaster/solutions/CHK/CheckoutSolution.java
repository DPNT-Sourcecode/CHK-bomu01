package befaster.solutions.CHK;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CheckoutSolution {


    private Map<String, Objects> store = new HashMap<>(){{
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









