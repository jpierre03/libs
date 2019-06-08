package fr.prunetwork.ping.main;

import fr.prunetwork.ping.LongTaskListener;
import fr.prunetwork.ping.WithHostname;

import java.util.Map;

public final class ListenerWrapper implements LongTaskListener {

    private static final Object MUTEX = new Object();
    private final WithHostname withHostname;
    private final LongTaskListener listener;
    private final Map<String, String> runningTasks;

    public ListenerWrapper(WithHostname withHostname, LongTaskListener listener, final Map<String, String> runningTasks) {
        this.withHostname = withHostname;
        this.listener = listener;
        this.runningTasks = runningTasks;
    }

    @Override
    public void setWorking(boolean isWorking) {
        synchronized (MUTEX) {
            listener.setWorking(isWorking);

            if (isWorking) {
                runningTasks.put(withHostname.getHostname(), withHostname.getHostname());
            } else {
                runningTasks.remove(withHostname.getHostname());
            }
        }
    }
}
