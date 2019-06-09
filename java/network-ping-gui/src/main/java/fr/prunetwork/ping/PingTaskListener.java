package fr.prunetwork.ping;

/**
 * @author Jean-Pierre PRUNARET
 */
public interface PingTaskListener {

    void interrogationStarted();

    void interrogationFinished(final boolean isReachable);
}
