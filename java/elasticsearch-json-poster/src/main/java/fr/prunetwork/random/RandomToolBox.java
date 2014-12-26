package fr.prunetwork.random;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Jean-Pierre PRUNARET
 * @since 09/11/14
 */
public class RandomToolBox {

    private static final Random random = new SecureRandom();

    private RandomToolBox() {
    }

    public static double nextDouble(double min, double max) {
        return random.nextDouble() * (max - min) + min;
    }

    public static boolean nextBoolean() {
        return random.nextBoolean();
    }

    public static boolean getSuccessiveAndRandomBoolean(int count) {
        if (count < 1) {
            throw new IllegalArgumentException("count must be positive (>1)");
        }
        boolean result = true;

        for (int i = 0; i < count; i++) {
            result = result && random.nextBoolean();
        }
        return result;
    }

    public static String getId(int length) {
        String s = RandomStringUtils.randomAlphabetic(length);

        if (s.trim().isEmpty()) {
            s = getId(length);
        }

        return s;
    }
}
