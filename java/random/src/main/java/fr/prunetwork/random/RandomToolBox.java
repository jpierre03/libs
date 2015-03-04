package fr.prunetwork.random;

import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Jean-Pierre PRUNARET
 * @since 09/11/14
 */
public class RandomToolBox {

    @NotNull
    private static final Random random = new SecureRandom();

    private RandomToolBox() {
    }

    public static double nextDouble(double min, double max) {
        if (min <= 0) {
            throw new IllegalArgumentException("min must be positive");
        }
        if (max <= min) {
            throw new IllegalArgumentException("max must be greater than min");
        }

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

    @NotNull
    public static String getId(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("length is to small (<1)");
        }

        @NotNull final String s = RandomStringUtils.randomAlphabetic(length);

        return s;
    }
}
