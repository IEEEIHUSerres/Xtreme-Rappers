import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class XtremeRappersEngineTest {

    private Main.XtremeRappersEngine xtremeRappersEngine;

    @Before
    public void setUp() throws Exception {
        this.xtremeRappersEngine = new Main.XtremeRappersEngine();
    }

    @Test
    public void firstTest() {
        final Long expected = 2L;
        final Long result = xtremeRappersEngine.run(
                4L,
                3L
        );

        Assert.assertEquals(expected, result);
    }

    @Test
    public void secondTest() {
        final Long expected = 0L;
        final Long result = xtremeRappersEngine.run(
                0L,
                10000000000L
        );

        Assert.assertEquals(expected, result);
    }
}
