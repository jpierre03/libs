package com.cor.cep.subscriber;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Just a convenience interface to let us easily contain the Esper statements with the Subscribers -
 * just for clarity so it's easy to see the statements the subscribers are registered against.
 */
public interface StatementSubscriber<T> {

    /**
     * Get the EPL Statement the Subscriber will listen to.
     *
     * @return EPL Statement
     */
    @NotNull
    public String getStatement();

    /**
     * Listener method called when Esper has detected a pattern match.
     *
     * @param eventMap
     */
    public void update(@NotNull final Map<String, T> eventMap);
}
