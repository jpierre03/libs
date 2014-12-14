package fr.prunetwork.network;

/**
 * @author Jean-Pierre PRUNARET
 */
public interface TcpClient {

    void connect() throws Exception;

    void disconnect() throws Exception;

    Character receiveFromServer() throws Exception;

    public String getHostname();

    int getPort();
}
