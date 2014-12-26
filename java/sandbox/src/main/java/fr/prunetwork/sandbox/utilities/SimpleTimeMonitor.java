package fr.prunetwork.sandbox.utilities;

/**
 * @author Jean-Pierre PRUNARET
 * @since 06/05/2014
 */
public class SimpleTimeMonitor {

    private long before = Long.MAX_VALUE;
    private long after = Long.MAX_VALUE;

    public SimpleTimeMonitor() {
    }

    public void start() {
        before = getNow();
    }

    public void stop() {
        after = getNow();
    }

    private long getNow() {
        return System.currentTimeMillis();
    }

    public long duration() {
        return after - before;
    }
}
