package befaster.solutions.CHK;

import befaster.solutions.HLO.HelloSolution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CHKTest {
    private CheckoutSolution chk;

    @BeforeEach
    public void setUp() {
        chk = new CheckoutSolution();
    }

    @Test
    public void compute_sum() {
        //assertThat(chk.checkout(null), equalTo(-1));
        //assertThat(chk.checkout(""), equalTo(0));
        //assertThat(chk.checkout(" "), equalTo(0)); //TODO check this
        //assertThat(chk.checkout("."), equalTo(-1));
        //assertThat(chk.checkout("K"), equalTo(-1));
        //assertThat(chk.checkout("KZ"), equalTo(-1));
        //assertThat(chk.checkout("aBC"), equalTo(-1));
        //assertThat(chk.checkout("AAA"), equalTo(130));
        //assertThat(chk.checkout("AA"), equalTo(100));
        //assertThat(chk.checkout("AAAA"), equalTo(130+50));
        assertThat(chk.checkout("BB"), equalTo(45));
        assertThat(chk.checkout("BBB"), equalTo(45+30));
        assertThat(chk.checkout("C"), equalTo(20));
        assertThat(chk.checkout("D"), equalTo(15));
        assertThat(chk.checkout("AAAABBBCD"), equalTo(130 + 50 + 45 + 30 + 20 + 15));
    }
}





