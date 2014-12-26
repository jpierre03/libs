package fr.prunetwork.sandbox.utilities;

/**
 * A wrapper encapsulate an object into him.
 * This pattern is commonly used to implement specific behaviours without touching original object.
 * This is not a decorator pattern implementation.
 *
 * @author Jean-Pierre PRUNARET
 */
public interface Wrapper<T> {

    /**
     * Get wrapped object
     *
     * @return wrapped object
     */
    T get();

    /**
     * Encapsulate an object
     *
     * @param object object to wrap
     */
    void set(T object);
}
