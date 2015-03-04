package fr.prunetwork.random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Jean-Pierre PRUNARET
 * @date 04/03/2015.
 */
public class IdentifierGeneratorTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_fail() {
        RandomToolBox.getId(0);
    }

    @Test
    public void should_be_asked_length() {
        for (int i = 1; i < 50; i++) {
            final String s = RandomToolBox.getId(i);

            assertEquals(i, s.length());
        }
    }
}
