package fr.prunetwork.collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Jean-Pierre PRUNARET
 */
public class RemoveDuplicateTest {

    private final String s1 = "sss1";
    private final String s1bis = "sss1";
    private final String s1ter = "sss1";

    private final String s2 = "sss222";
    private final String s2bis = "sss222";
    private final String s2ter = "sss222";

    private final String s3 = "sss333";
    private final String s3bis = "sss333";
    private final String s3ter = "sss333";

    private Collection<String> collection;

    @Before
    public void setUp() throws Exception {
        collection = new ArrayList<>();

        collection.add(s1);
        collection.add(s2);
        collection.add(s3);

        collection.add(s1bis);
        collection.add(s2bis);
        collection.add(s3bis);

        collection.add(s1ter);
        collection.add(s2ter);
        collection.add(s3ter);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void check_initial_size() {
        assertEquals(9, collection.size());
    }

    @Test
    public void check_initial_content() {
        assertTrue(collection.contains(s1));
        assertTrue(collection.contains(s2));
        assertTrue(collection.contains(s3));

        assertTrue(collection.contains(s1bis));
        assertTrue(collection.contains(s2bis));
        assertTrue(collection.contains(s3bis));

        assertTrue(collection.contains(s1ter));
        assertTrue(collection.contains(s2ter));
        assertTrue(collection.contains(s3ter));
    }

    @Test
    public void check_remove_duplicate_size() {
        final Collection<String> withoutDuplicates = CollectionUtilities.withoutDuplicates(collection);

        assertEquals(3, withoutDuplicates.size());
        assertEquals(9, collection.size());
    }
}
