/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.prunetwork.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author jpierre03
 */
public final class ClientConnexionHandler_Echo implements Communicator {

    private Socket socket = null;
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;

    public ClientConnexionHandler_Echo(Socket clientSocket) {
        try {
            socket = clientSocket;
            connect();
            //		this.start();
        } catch (IOException ex) {
            Logger.getLogger(ClientConnexionHandler_Echo.class.getName()).log(Level.SEVERE, null, ex);
            disconnect();
        }
    }

    @Override
    public void connect() throws IOException {
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
            try {
                socket.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void disconnect() {
        try {
            // close streams and connections
            ois.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientConnexionHandler_Echo.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientConnexionHandler_Echo.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientConnexionHandler_Echo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Serializable read() {
        Serializable object = null;
        try {
            if (isWellConnected() && ois.available() > 0) {
                object = (Serializable) ois.readObject();
            } else {
                System.out.println(socket);
                System.out.println(ois);
                System.out.println("pas bon [" + "isWellConnected()=" + isWellConnected() + "]");
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientConnexionHandler_Echo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientConnexionHandler_Echo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ClientConnexionHandler_Echo.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (IOException ex) {
            Logger.getLogger(ClientConnexionHandler_Echo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public boolean isWellConnected() {
//		return socket.isConnected() && !socket.isInputShutdown() && !socket.isOutputShutdown();
        System.out.println(socket);
        return socket.isConnected();
    }

    @Override
    public void run() {

        while (isWellConnected()) {
            Serializable object = read();
            System.out.println(object);
//			if (object != null) {
//				write(object);
//			} else {
//				try {
//					Thread.sleep(1);
//				} catch (InterruptedException ex) {
//					Logger.getLogger(ClientConnexionHandler_Echo.class.getName()).log(Level.SEVERE, null, ex);
//				}
//			}
        }
        disconnect();
    }
}
