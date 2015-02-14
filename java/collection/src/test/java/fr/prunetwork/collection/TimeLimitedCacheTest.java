package fr.prunetwork.collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TimeLimitedCacheTest {

    TimeLimitedCacheMap<String, Object> map;
    private Object object1;
    private String object1_id;
    private Object object2;
    private String object2_id;
    private Object object3;
    private String object3_id;

    @Before
    public void setUp() throws Exception {
        map = new TimeLimitedCacheMap<>(0, 200, 50, TimeUnit.MILLISECONDS);
        {
            object1 = new Object();
            object1_id = "o1";
        }
        {
            object2 = new Object();
            object2_id = "o2";
        }
        {
            object3 = new Object();
            object3_id = "o3";
        }
    }

    @After
    public void tearDown() throws Exception {
        map = null;
    }

    @Test
    public void shouldInitialize() {
        assertTrue(map != null);

        assertTrue(object1 != null);
        assertTrue(object1_id != null);

        assertTrue(object2 != null);
        assertTrue(object2_id != null);

        assertTrue(object3 != null);
        assertTrue(object3_id != null);
    }

    @Test
    public void shouldInsertObjects() {
        assertEquals(0, map.getClonedMap().size());

        map.put(object1_id, object1);
        assertEquals(1, map.getClonedMap().size());

        map.put(object2_id, object2);
        assertEquals(2, map.getClonedMap().size());

        map.put(object3_id, object3);
        assertEquals(3, map.getClonedMap().size());
    }

    @Test
    public void no_entry_duplication_even_with_same_object() {
        assertEquals(0, map.getClonedMap().size());

        map.put(object1_id, object1);
        assertEquals(1, map.getClonedMap().size());

        map.put(object1_id, object1);
        map.put(object1_id, object1);
        map.put(object1_id, object1);

        assertEquals(1, map.getClonedMap().size());
    }

    @Test
    public void no_entry_duplication_even_with_different_objects() {
        assertEquals(0, map.getClonedMap().size());

        map.put(object1_id, object3);
        map.put(object1_id, object2);
        map.put(object1_id, object1);

        assertEquals(1, map.getClonedMap().size());
    }

    @Test
    public void shouldExpired() {
        assertEquals(0, map.getClonedMap().size());

        map.put(object1_id, object1);
        map.put(object2_id, object1);
        map.put(object3_id, object1);

        assertEquals(3, map.getClonedMap().size());

        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
        }
        assertEquals(0, map.getClonedMap().size());
    }
}
