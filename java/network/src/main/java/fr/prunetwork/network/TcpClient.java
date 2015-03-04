package fr.prunetwork.network;

import org.jetbrains.annotations.NotNull;

/**
 * @author Jean-Pierre PRUNARET
 */
public interface TcpClient {

    void connect() throws Exception;

    void disconnect() throws Exception;

    @NotNull
    Character receiveFromServer() throws Exception;

    @NotNull
    public String getHostname();

    @NotNull
    Integer getPort();
}
