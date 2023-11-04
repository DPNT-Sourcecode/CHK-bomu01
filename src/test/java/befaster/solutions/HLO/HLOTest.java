package befaster.solutions.HLO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class HLOTest {
    private HelloSolution hlo;

    @BeforeEach
    public void setUp() {
        hlo = new HelloSolution();
    }

    @Test
    public void compute_sum() {
        assertThat(hlo.hello("John"), equalTo("Hello, John!"));
        assertThat(hlo.hello("test"), equalTo("Hello, test!"));
        assertThat(hlo.hello(""), equalTo("Hello, !"));
    }
}
