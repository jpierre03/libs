package fr.prunetwork.network;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jean-Pierre PRUNARET
 * @since 14/05/2014
 */
public final class TcpClientFactory {

    static final Map<String, Map<Integer, TcpClient>> connexions = new ConcurrentHashMap<>();
    private static final Object staticMutex = new Object();

    private TcpClientFactory() {
    }

    public static TcpClient getConnexion(String hostname, int portNumber) throws Exception {
        synchronized (staticMutex) {

            final Map<Integer, TcpClient> hostnameConnexions = connexions.get(hostname);
            if (hostnameConnexions == null
                    || hostnameConnexions.get(portNumber) == null) {
                connexions.put(hostname, (hostnameConnexions != null ? hostnameConnexions : new ConcurrentHashMap<Integer, TcpClient>()));
                connexions.get(hostname).put(portNumber, new TcpClientImpl(hostname, portNumber));
            }

            assert connexions.get(hostname).get(portNumber) != null : "should exist or throw an exception earlier";
            return connexions.get(hostname).get(portNumber);
        }
    }

    public static TcpClient getConnectedConnexion(String hostname, int portNumber) throws Exception {
        final TcpClient connexion = getConnexion(hostname, portNumber);
        connexion.connect();

        return connexion;
    }
}
