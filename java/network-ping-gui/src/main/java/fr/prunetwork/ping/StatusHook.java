package fr.prunetwork.ping;

/**
 * @author Jean-Pierre PRUNARET
 * @since 19/04/2014
 */
public interface StatusHook {

    /**
     * Define a simple boolean status
     * @param status
     */
    void setStatus(boolean status);
}
