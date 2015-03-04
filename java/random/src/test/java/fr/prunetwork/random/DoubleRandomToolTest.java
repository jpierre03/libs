package fr.prunetwork.random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Jean-Pierre PRUNARET
 * @date 04/03/2015.
 */
public class DoubleRandomToolTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_fail_arg_too_small() {
        RandomToolBox.nextDouble(-1, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_fail_arg_wrong_order() {
        RandomToolBox.nextDouble(100, 10);
    }

    @Test
    public void should_be_in_range() {
        final int iteration = 1000 * 1000;
        final double max = 10000.;
        final double min = 1.;
        double sum = 0;

        for (int i = 0; i < iteration; i++) {
            final double d = RandomToolBox.nextDouble(min, max);
            assertTrue(d > min);
            assertTrue(d < max);

            sum += d;
        }

        final double median = (max - min) / 2;
        final double highLimit = median + 5. / 100. * median;
        final double lowLimit = median - 5. / 100. * median;

        final double averageValue = sum / iteration;
        assertTrue(averageValue < highLimit);
        assertTrue(averageValue > lowLimit);
    }
}
