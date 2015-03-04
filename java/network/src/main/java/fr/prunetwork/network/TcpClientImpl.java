package fr.prunetwork.network;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
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

    @NotNull
    private static final AtomicInteger count = new AtomicInteger(0);
    @NotNull
    private final String hostname;
    private final int port;
    @NotNull
    private final Socket socket = new Socket();
    @Nullable
    private BufferedReader entree;
    @Nullable
    private PrintWriter sortie;
    private boolean isConnected = false;

    TcpClientImpl(@NotNull final String hostname, int portNumber) throws Exception {
        count.incrementAndGet();
        if (hostname.isEmpty()) {
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
        assert socket.isConnected() : ASSERT_CONNECTED;

        isConnected = false;

        try {
            if (sortie != null) {
                sortie.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (entree != null) {
                entree.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert !isConnected : ASSERT_CONNECTED;
        assert socket.isConnected() : ASSERT_CONNECTED;
    }

    @NotNull
    @Override
    public Character receiveFromServer() throws Exception {
        final Character reference;

        if (entree == null) {
            throw new IllegalStateException("read buffer not defined");
        }
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

    @NotNull
    @Override
    public String getHostname() {
        return hostname;
    }

    @Override
    public Integer getPort() {
        return port;
    }
}
