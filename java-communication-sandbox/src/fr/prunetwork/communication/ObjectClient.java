package fr.prunetwork.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ObjectClient implements Communicator {

    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;
    private Socket socket = null;

    public ObjectClient(String hostName, int portNumber) throws IOException {
        // open a socket connection
        socket = new Socket(hostName, portNumber);
        connect();
    }

    @Override
    public void connect() throws IOException {
        // open I/O streams for objects
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void disconnect() {
        try {
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(ObjectClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ois.close();
        } catch (IOException ex) {
            Logger.getLogger(ObjectClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ObjectClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Serializable read() {
        Serializable object = null;

        try {
            if (isWellConnected() && ois.available() > 0) {
                object = (Serializable) ois.readObject();
            }
        } catch (IOException ex) {
            Logger.getLogger(ObjectClient.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("read IOException:" + ex);
        } catch (Exception ex) {
            System.out.println("read Exception:" + ex);
        }

        return object;
    }

    @Override
    public boolean write(Serializable object) {
        boolean ret = false;

        try {
            oos.writeObject(object);
            oos.flush();
            ret = true;
        } catch (Exception e) {
            System.out.println("write exception:" + e);
        }
        return ret;
    }

    @Override
    public boolean isWellConnected() {
        return socket.isConnected()
                && !socket.isClosed()
                && !socket.isInputShutdown()
                && !socket.isOutputShutdown();
    }

    @Override
    public void run() {
        try {
            while (isWellConnected() && ois.available() > 0) {
                Serializable read = read();
                if (read != null) {
                    System.out.println(read);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ObjectClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void finalize() {
        try {
            super.finalize();
        } catch (Throwable ex) {
            Logger.getLogger(ObjectClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        disconnect();
    }
}
