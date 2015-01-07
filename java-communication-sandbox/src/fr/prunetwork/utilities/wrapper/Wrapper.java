package fr.prunetwork.utilities.wrapper;

import java.io.Serializable;


/**
 * Défini les méthode permettant de récupérer l'objet interne à une encapsulation.
 *
 * @param <T>
 * @author jpierre03
 */
public interface Wrapper<T>
        extends Serializable {
    //~ Methods ....................................................................................

    /**
     * @param object
     * @return
     */
    Wrapper<T> setObject(T object);

    /**
     * @return
     */
    T getObject();
} // end Wrapper
