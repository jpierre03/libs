package fr.prunetwork.network;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jean-Pierre PRUNARET
 * @since 14/05/2014
 */
public final class ClientRFIDFactory {

    static final Map<String, Map<Integer, ClientRFID>> connexions = new ConcurrentHashMap<>();
    private static final Object staticMutex = new Object();

    private ClientRFIDFactory() {
    }

    public static ClientRFID getConnexion(String hostname, int portNumber) throws Exception {
        synchronized (staticMutex) {

            final Map<Integer, ClientRFID> hostnameConnexions = connexions.get(hostname);
            if (hostnameConnexions == null
                    || hostnameConnexions.get(portNumber) == null) {
                connexions.put(hostname, (hostnameConnexions != null ? hostnameConnexions : new ConcurrentHashMap<Integer, ClientRFID>()));
                connexions.get(hostname).put(portNumber, new ClientRFIDImpl(hostname, portNumber));
            }

            assert connexions.get(hostname).get(portNumber) != null : "should exist or throw an exception earlier";
            return connexions.get(hostname).get(portNumber);
        }
    }

    public static ClientRFID getConnectedConnexion(String hostname, int portNumber) throws Exception {
        final ClientRFID connexion = getConnexion(hostname, portNumber);
        connexion.connect();

        return connexion;
    }
}
