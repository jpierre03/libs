package fr.prunetwork.random;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * @author Jean-Pierre PRUNARET
 * @since 09/11/14
 */
public class RandomEnum<E extends Enum> {

    @NotNull
    private static final Random RND = new Random();
    @NotNull
    private final E[] values;

    public RandomEnum(@NotNull final Class<E> token) {
        values = token.getEnumConstants();
    }

    @NotNull
    @SuppressWarnings("unused")
    public E random() {
        return values[RND.nextInt(values.length)];
    }

    @NotNull
    @SuppressWarnings("unused")
    public E linearRandom() {
        return values[getLinearRandomNumber(values.length)];
    }

    /**
     * @see "http://stackoverflow.com/questions/5969447/java-random-integer-with-non-uniform-distribution/5969719#5969719"
     */
    @SuppressWarnings("unused")
    private static int getLinearRandomNumber(int maxSize) {
        /** Get a linearly multiplied random number */
        int randomMultiplier = maxSize * (maxSize + 1) / 2;
        int randomInt = RND.nextInt(randomMultiplier);

        /** Linearly iterate through the possible values to find the correct one */
        int linearRandomNumber = 0;
        for (int i = maxSize; randomInt >= 0; i--) {
            randomInt -= i;
            linearRandomNumber++;
        }

        linearRandomNumber = Integer.max(0, linearRandomNumber);
        linearRandomNumber = Integer.min(maxSize, linearRandomNumber);

        if (linearRandomNumber < 0) throw new AssertionError();
        if (linearRandomNumber >= maxSize) throw new AssertionError();

        return linearRandomNumber;
    }
}
