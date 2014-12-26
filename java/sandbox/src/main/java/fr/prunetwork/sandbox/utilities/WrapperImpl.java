package fr.prunetwork.sandbox.utilities;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 * An implementation pf the wrapper.
 * This is a simple one. It can be used as is, or as example.
 *
 * @author Jean-Pierre PRUNARET
 */
public class WrapperImpl<T extends Serializable>
        implements Wrapper<T>, Serializable {

    private final transient Logger logger = Logger.getAnonymousLogger();
    private T wrapped;

    public WrapperImpl() {
    }

    @Override
    public T get() {
        return wrapped;
    }

    @Override
    public void set(T object) {
        assert object != null;

        wrapped = object;
    }
}
