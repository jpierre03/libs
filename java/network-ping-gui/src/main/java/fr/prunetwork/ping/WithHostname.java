package fr.prunetwork.ping;

import org.jetbrains.annotations.NotNull;

/**
 * @author Jean-Pierre PRUNARET
 * @since 19/04/2014
 */
public interface WithHostname {

    @NotNull
    String getHostname();

    void setHostname(@NotNull final String hostname);
}
