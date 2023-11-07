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
        assertThat(chk.checkout(null), equalTo(-1));
        assertThat(chk.checkout(""), equalTo(0)); //check this to see if this is an invalid input
        assertThat(chk.checkout(" "), equalTo(0)); //check this to see if this is an invalid input
        assertThat(chk.checkout("."), equalTo(-1));
        assertThat(chk.checkout("K"), equalTo(-1));
        assertThat(chk.checkout("KZ"), equalTo(-1));
        assertThat(chk.checkout("aBC"), equalTo(-1)); //check this to see if this is an invalid input
        assertThat(chk.checkout("AAAABBBCD"), equalTo(130 + 50 + 45 + 30 + 20 + 15));
    }
}

