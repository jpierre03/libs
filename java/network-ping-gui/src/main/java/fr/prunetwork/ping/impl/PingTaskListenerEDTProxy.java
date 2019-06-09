package fr.prunetwork.ping.impl;

import fr.prunetwork.ping.PingTaskListener;

import javax.swing.*;

public class PingTaskListenerEDTProxy implements PingTaskListener {

    private final PingTaskListener listener;

    public PingTaskListenerEDTProxy(final PingTaskListener listener) {
        this.listener = listener;
    }

    @Override
    public void interrogationStarted() {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(listener::interrogationStarted);
        } else {
            listener.interrogationStarted();
        }
    }

    @Override
    public void interrogationFinished(boolean isReachable) {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(() -> listener.interrogationFinished(isReachable));
        } else {
            listener.interrogationFinished(isReachable);
        }
    }
}
