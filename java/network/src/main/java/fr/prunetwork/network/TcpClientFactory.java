package fr.prunetwork.network;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jean-Pierre PRUNARET
 * @since 14/05/2014
 */
public final class TcpClientFactory {

    @NotNull
    static final Map<String, Map<Integer, TcpClient>> connexions = new ConcurrentHashMap<>();
    @NotNull
    private static final Object staticMutex = new Object();

    private TcpClientFactory() {
    }

    public static TcpClient getConnexion(@NotNull final String hostname, int portNumber) throws Exception {
        synchronized (staticMutex) {

            @Nullable final Map<Integer, TcpClient> hostnameConnexions = connexions.get(hostname);

            if (hostnameConnexions == null
                    || hostnameConnexions.get(portNumber) == null) {

                connexions.put(hostname, (hostnameConnexions != null ? hostnameConnexions : new ConcurrentHashMap<>()));
                connexions.get(hostname).put(portNumber, new TcpClientImpl(hostname, portNumber));
            }

            assert connexions.get(hostname).get(portNumber) != null : "should exist or throw an exception earlier";
            return connexions.get(hostname).get(portNumber);
        }
    }

    public static TcpClient getConnectedConnexion(@NotNull final String hostname, int portNumber) throws Exception {
        final TcpClient connexion = getConnexion(hostname, portNumber);
        connexion.connect();

        return connexion;
    }
}
