package fr.prunetwork.sandbox.utilities;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Jean-Pierre PRUNARET
 * @since 23/04/2014
 */
public final class RandomUtilities {

    /**
     * Usually this can be a field rather than a method variable
     */
    static final Random rand = new SecureRandom();


    private RandomUtilities() {
    }

    /**
     * Returns a pseudo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimum value
     * @param max Maximum value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static int randInt(int min, int max) {
        assert max < Integer.MAX_VALUE : "max too big";

        /**
         * nextInt is normally exclusive of the top value, so add 1 to make it inclusive
         */
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
