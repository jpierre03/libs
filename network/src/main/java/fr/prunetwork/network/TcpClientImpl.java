package fr.prunetwork.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Louati
 * @author Jean-Pierre PRUNARET
 */
public final class TcpClientImpl implements TcpClient {

    private static final AtomicInteger count = new AtomicInteger(0);
    private final String hostname;
    private final int port;
    private Socket socket;
    private BufferedReader entree;
    private PrintWriter sortie;
    private boolean isConnected = false;

    TcpClientImpl(String hostname, int portNumber) throws Exception {
        count.incrementAndGet();
        if (hostname == null
                || hostname.isEmpty()) {
            throw new IllegalArgumentException("Hostname shouldn't be empty");
        }
        if (portNumber < 1024
                || portNumber > 65534) {
            throw new IllegalArgumentException("Port number over limit [1024; 65534]");
        }
        if (count.get() > 1) {
//            throw new IllegalStateException("Constructor should be called only once (this special case), called: #" + count.get());
        }
        this.hostname = hostname;
        this.port = portNumber;
    }

    @Override
    public void connect() throws Exception {
//        assert !isConnected : "Should not be asked to connect more than once";
//        assert socket == null;

        if (!isConnected) {
            socket = new Socket();
            socket.connect(new InetSocketAddress(hostname, port), 1 * 1000);
            entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            sortie = new PrintWriter(socket.getOutputStream());
            isConnected = true;
        }

//        assert isConnected : "should be connected";
//        assert socket != null : "socket is null connected";
//        assert socket.isConnected() : "socket is not connected";
    }

    @Override
    public void disconnect() throws Exception {
        final String ASSERT_CONNECTED = "Should be connected first";
        assert isConnected : ASSERT_CONNECTED;
        assert socket != null && socket.isConnected() : ASSERT_CONNECTED;

        isConnected = false;

        if (sortie != null) {
            sortie.close();
        }

        try {
            if (socket != null) {
                socket.close();
            }

            if (entree != null) {
                entree.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert !isConnected : ASSERT_CONNECTED;
        assert socket != null && socket.isConnected() : ASSERT_CONNECTED;
    }

    @Override
    public Character receiveFromServer() throws Exception {
        Character reference = null;
        try {
            reference = (char) entree.read();
//            reference = entree.readLine();

            Thread.yield();
//            if (reference == null) {
//                try {
//                    Thread.sleep(5);
//                } catch (Exception ignored) {
//                }
//            }
        } catch (Exception e) {
            TcpClientFactory.connexions.get(getHostname()).remove(getPort());
            this.disconnect();
            throw e;
        }

        return reference;
    }

    @Override
    public String getHostname() {
        return hostname;
    }

    @Override
    public int getPort() {
        return port;
    }
}
