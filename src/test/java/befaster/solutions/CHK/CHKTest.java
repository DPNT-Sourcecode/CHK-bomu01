package befaster.solutions.CHK;

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
        assertThat(chk.checkout(""), equalTo(0));
        assertThat(chk.checkout(" "), equalTo(0));
        assertThat(chk.checkout("."), equalTo(-1));
        assertThat(chk.checkout("aBC"), equalTo(-1));
        assertThat(chk.checkout("AAA"), equalTo(130));
        assertThat(chk.checkout("AA"), equalTo(100));
        assertThat(chk.checkout("AAAA"), equalTo(130 + 50));
        assertThat(chk.checkout("BB"), equalTo(45));
        assertThat(chk.checkout("BBB"), equalTo(45 + 30));
        assertThat(chk.checkout("C"), equalTo(20));
        assertThat(chk.checkout("D"), equalTo(15));
        assertThat(chk.checkout("AAAABBBCD"), equalTo(130 + 50 + 45 + 30 + 20 + 15));
        assertThat(chk.checkout("EE"), equalTo(80));
        assertThat(chk.checkout("EEB"), equalTo(80));
        assertThat(chk.checkout("EEBB"), equalTo(80 + 30));
        assertThat(chk.checkout("AAAAAA"), equalTo(200 + 50));
        assertThat(chk.checkout("AAAAAAAAA"), equalTo(200 + 130 + 50));
        assertThat(chk.checkout("EEEEBB"), equalTo(160));
        assertThat(chk.checkout("BEBEEE"), equalTo(160));
        assertThat(chk.checkout("FF"), equalTo(20));
        assertThat(chk.checkout("FFF"), equalTo(20));
        assertThat(chk.checkout("FFFFFF"), equalTo(40));
        assertThat(chk.checkout("ABCDEFGHIJKLMNOPQRSTUVWXYZ"), equalTo(50 + 30 + 20 + 15 + 40 + 10 + 20 + 10 + 35 + 60 + 70 + 90 + 15 + 40 + 10 + 50 + 30 + 50 + 20 + 40 + 50 + 20 + 17 + 45));
        assertThat(chk.checkout("HHHHHHHHHHHHHHH"), equalTo(80 + 45));
        assertThat(chk.checkout("KKK"), equalTo(120 + 70));
        assertThat(chk.checkout("PPPPPP"), equalTo(200 + 50));
        assertThat(chk.checkout("QQQQ"), equalTo(80 + 30));
        assertThat(chk.checkout("V"), equalTo(50));
        assertThat(chk.checkout("VV"), equalTo(90));
        assertThat(chk.checkout("VVV"), equalTo(130));
        assertThat(chk.checkout("NNNM"), equalTo(120));
        assertThat(chk.checkout("RRRQ"), equalTo(150));
        assertThat(chk.checkout("UUUU"), equalTo(120));

        assertThat(chk.checkout("ZZZ"), equalTo(45));
        assertThat(chk.checkout("TTT"), equalTo(45));
        assertThat(chk.checkout("YYY"), equalTo(45));
        assertThat(chk.checkout("ZZZ"), equalTo(45));
        assertThat(chk.checkout("STXYZZ"), equalTo(45 + 45));
        assertThat(chk.checkout("ZSSZXXX"), equalTo(45 + 45 + 17));
    }
}

