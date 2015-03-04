package fr.prunetwork.random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Jean-Pierre PRUNARET
 * @date 04/03/2015.
 */
public class BooleanRandomToolTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void shouldNotFail() {
        final boolean aBoolean = RandomToolBox.nextBoolean();

        assertEquals(true, aBoolean
                || !aBoolean);
    }


    @Test
    public void should_get_equally_true_or_false() {
        int trueCounter = 0;
        int falseCounter = 0;

        for (int i = 0; i < 1000 * 1000; i++) {
            if (RandomToolBox.nextBoolean()) {
                trueCounter++;
            } else {
                falseCounter++;
            }
        }

        assertTrue(falseCounter < 500 * 1000 + 2000);
        assertTrue(trueCounter < 500 * 1000 + 2000);

        assertTrue(falseCounter > 500 * 1000 - 2000);
        assertTrue(trueCounter > 500 * 1000 - 2000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_fail_arg_too_small_successive1() {
        RandomToolBox.getSuccessiveAndRandomBoolean(0);
    }

    @Test
    public void shouldBe_50percent() {
        final int iteration = 1000 * 1000;
        int trueCounter = 0;

        for (int i = 0; i < iteration; i++) {
            final boolean b = RandomToolBox.getSuccessiveAndRandomBoolean(1);

            if (b) {
                trueCounter++;
            }
        }

        final double expected = iteration * 50. / 100.;
        final double highLimit = expected + iteration * 0.1 / 100.;
        final double lowLimit = expected - iteration * 0.1 / 100.;

        assertTrue(trueCounter < highLimit);
        assertTrue(trueCounter > lowLimit);
    }

    @Test
    public void shouldBe_25percent() {
        final int iteration = 1000 * 1000;
        int trueCounter = 0;

        for (int i = 0; i < iteration; i++) {
            final boolean b = RandomToolBox.getSuccessiveAndRandomBoolean(2);

            if (b) {
                trueCounter++;
            }
        }

        final double expected = iteration * 25. / 100.;
        final double highLimit = expected + iteration * 0.1 / 100.;
        final double lowLimit = expected - iteration * 0.1 / 100.;

        assertTrue(trueCounter < highLimit);
        assertTrue(trueCounter > lowLimit);
    }

    @Test
    public void shouldBe_12percent() {
        final int iteration = 1000 * 1000;
        int trueCounter = 0;

        for (int i = 0; i < iteration; i++) {
            final boolean b = RandomToolBox.getSuccessiveAndRandomBoolean(3);

            if (b) {
                trueCounter++;
            }
        }

        final double expected = iteration * 12.5 / 100.;
        final double highLimit = expected + iteration * 0.1 / 100.;
        final double lowLimit = expected - iteration * 0.1 / 100.;

        assertTrue(trueCounter < highLimit);
        assertTrue(trueCounter > lowLimit);
    }

    @Test
    public void shouldBe_6percent() {
        final int iteration = 1000 * 1000;
        int trueCounter = 0;

        for (int i = 0; i < iteration; i++) {
            final boolean b = RandomToolBox.getSuccessiveAndRandomBoolean(4);

            if (b) {
                trueCounter++;
            }
        }

        final double expected = iteration * 6.25 / 100.;
        final double highLimit = expected + iteration * 0.1 / 100.;
        final double lowLimit = expected - iteration * 0.1 / 100.;

        assertTrue(trueCounter < highLimit);
        assertTrue(trueCounter > lowLimit);
    }

    @Test
    public void shouldBe_078percent() {
        final int iteration = 1000 * 1000;
        int trueCounter = 0;

        for (int i = 0; i < iteration; i++) {
            final boolean b = RandomToolBox.getSuccessiveAndRandomBoolean(7);

            if (b) {
                trueCounter++;
            }
        }

        final double expected = iteration * 0.78125 / 100.;
        final double highLimit = expected + iteration * 0.1 / 100.;
        final double lowLimit = expected - iteration * 0.1 / 100.;

        assertTrue(trueCounter < highLimit);
        assertTrue(trueCounter > lowLimit);
    }
}
