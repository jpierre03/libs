package fr.prunetwork.random;

import java.util.Random;

/**
 * @author Jean-Pierre PRUNARET
 * @since 09/11/14
 */
public class RandomEnum<E extends Enum> {

    private static final Random RND = new Random();
    private final E[] values;

    public RandomEnum(Class<E> token) {
        values = token.getEnumConstants();
    }

    public E random() {
        return values[RND.nextInt(values.length)];
    }

    public E linearRandom() {
        return values[getLinearRandomNumber(values.length)];
    }

    private static int getLinearRandomNumber(int maxSize) {
        //Get a linearly multiplied random number
        int randomMultiplier = maxSize * (maxSize + 1) / 2;
        int randomInt = RND.nextInt(randomMultiplier);

        //Linearly iterate through the possible values to find the correct one
        int linearRandomNumber = 0;
        for (int i = maxSize; randomInt >= 0; i--) {
            randomInt -= i;
            linearRandomNumber++;
        }

        return linearRandomNumber;
    }
}
