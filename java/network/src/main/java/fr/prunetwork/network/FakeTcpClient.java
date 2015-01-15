package fr.prunetwork.network;

/**
 * @author Louati
 * @author Jean-Pierre PRUNARET
 */
public final class FakeTcpClient implements TcpClient {

    private final String hostname;
    private final int portNumber;

    FakeTcpClient(String hostname, int portNumber) throws Exception {
        if (hostname == null
                || hostname.isEmpty()) {
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
    public Character receiveFromServer() throws Exception {
        try {
            Thread.sleep(60 * 1000);
        } catch (InterruptedException e) {
            // Do nothing
        }

        return ' ';
    }

    @Override
    public String getHostname() {
        return hostname;
    }

    @Override
    public int getPort() {
        return portNumber;
    }
}
