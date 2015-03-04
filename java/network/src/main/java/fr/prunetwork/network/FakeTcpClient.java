package fr.prunetwork.network;

import org.jetbrains.annotations.NotNull;

/**
 * @author Louati
 * @author Jean-Pierre PRUNARET
 */
public final class FakeTcpClient implements TcpClient {

    @NotNull
    private final String hostname;
    private final int portNumber;

    FakeTcpClient(@NotNull final String hostname, int portNumber) throws Exception {
        if (hostname.isEmpty()) {
            throw new IllegalArgumentException("Hostname shouldn't be empty");
        }
        if (portNumber < 1024
                || portNumber > 65534) {
            throw new IllegalArgumentException("Port number over limit [1024; 65534]");
        }
        this.hostname = hostname;
        this.portNumber = portNumber;
    }

    @Override
    public void connect() throws Exception {
        // do nothing
    }

    @Override
    public void disconnect() throws Exception {
        // do nothing
    }

    @Override
    @NotNull
    public Character receiveFromServer() throws Exception {
        try {
            Thread.sleep(60 * 1000);
        } catch (InterruptedException e) {
            // Do nothing
        }

        return ' ';
    }

    @Override
    @NotNull
    public String getHostname() {
        return hostname;
    }

    @Override
    @NotNull
    public Integer getPort() {
        return portNumber;
    }
}
