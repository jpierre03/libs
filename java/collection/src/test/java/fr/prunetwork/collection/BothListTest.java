package fr.prunetwork.collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * @author Jean-Pierre PRUNARET
 */
public class BothListTest {

    private final String s1 = "sss1";
    private final String s2 = "sss222";
    private final String s3 = "sss333";

    private Collection<String> collectionA;
    private Collection<String> collectionB;

    @Before
    public void setUp() throws Exception {
        collectionA = new ArrayList<>();
        collectionB = new ArrayList<>();

        {
            collectionA.add(s1);
            collectionA.add(s2);
            collectionA.add(s3);
        }
        {
            collectionB.add(s1);
            collectionB.add(s2);
            collectionB.add(s3);
        }
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void check_initial_size() {
        assertEquals(3, collectionA.size());
        assertEquals(3, collectionB.size());
    }

    @Test
    public void check_initial_content() {
        assertTrue(collectionA.contains(s1));
        assertTrue(collectionA.contains(s2));
        assertTrue(collectionA.contains(s3));

        assertTrue(collectionB.contains(s1));
        assertTrue(collectionB.contains(s2));
        assertTrue(collectionB.contains(s3));
    }

    @Test
    public void check_remove_item() {
        assertTrue(collectionA.remove(s1));
        assertFalse(collectionA.contains(s1));

        assertEquals(2, collectionA.size());
    }

    @Test
    public void check_both_identity() {
        final Collection<String> result = CollectionUtilities.inBothLists(collectionA, collectionB);

        assertNotNull(result);

        assertEquals(collectionA.size(), result.size());
        assertEquals(collectionB.size(), result.size());

        assertTrue(result.containsAll(collectionA));
        assertTrue(result.containsAll(collectionB));

        assertTrue(collectionA.containsAll(collectionB));
    }

    @Test
    public void check_both_with_empty() {
        final Collection<String> result = CollectionUtilities.inBothLists(collectionA, new ArrayList<>());

        assertNotNull(result);

        assertEquals(0, result.size());
    }

    @Test
    public void check_both_different() {
        final Collection<String> simpleCollection = new ArrayList<>(collectionB);
        simpleCollection.remove(s1);
        simpleCollection.remove(s2);

        assertEquals(1, simpleCollection.size());
        assertTrue(simpleCollection.contains(s3));

        final Collection<String> result = CollectionUtilities.inBothLists(collectionA, simpleCollection);

        assertNotNull(result);

        assertEquals(1, result.size());
        assertTrue(result.contains(s3));
    }
}
