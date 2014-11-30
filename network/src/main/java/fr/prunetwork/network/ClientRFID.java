package fr.prunetwork.network;

/**
 * @author Jean-Pierre PRUNARET
 */
public interface ClientRFID {

    void connect() throws Exception;

    void disconnect() throws Exception;

    Character receiveFromRFIDServer() throws Exception;

    public String getHostname();

    int getPort();
}
