package fr.prunetwork.ping.core;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public final class MonitoringTarget implements Comparable<MonitoringTarget> {

    private final String hostname;
    private final String label;

    public MonitoringTarget(String hostname) {
        this(hostname, hostname);
    }

    public MonitoringTarget(final String hostname, final String label) {
        this.hostname = hostname;
        this.label = label;
    }

    public String getHostname() {
        return hostname;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public int compareTo(@NotNull MonitoringTarget o) {
        return Comparator
                .comparing(MonitoringTarget::getHostname)
                .thenComparing(MonitoringTarget::getLabel)
                .compare(this, o);
    }
}
