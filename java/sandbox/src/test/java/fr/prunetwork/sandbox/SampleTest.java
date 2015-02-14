package fr.prunetwork.sandbox;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SampleTest {
    Object object;

    @Before
    public void setUp() throws Exception {
        object = new Object();
    }

    @After
    public void tearDown() throws Exception {
        object = null;
    }

    @Test
    public void shouldInitialize() {
        assertTrue(object != null);
    }

    @Test(timeout = 500)
    public void shouldBeFast() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        assertTrue(true);
    }
}
