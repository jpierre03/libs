package fr.prunetwork.communication.dateserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DateServer extends Thread {

	private ServerSocket dateServer;
	/** Détermine le nombre de threads qui sont exécutés en même temps*/
	private static Executor exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	public static void main(String argv[]) throws Exception {
		new DateServer();
	}

	public DateServer() throws Exception {
		dateServer = new ServerSocket(3000);
		System.out.println("Server listening on port " + 3000 + ".");
		this.start();
	}

	@Override
	public void run() {
		while (true) {
			try {
//				System.out.println("Waiting for connections.");
				Socket client = dateServer.accept();
				System.out.println("Accepted a connection from: " + client.getInetAddress());
				ClientConnexionDateServer c = new ClientConnexionDateServer(client);
				exec.execute(c);
			} catch (Exception e) {
			}
		}
	}
}

class ClientConnexionDateServer implements Runnable {

	private Socket client = null;
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;

	public ClientConnexionDateServer(Socket clientSocket) {
		try {
			client = clientSocket;
			connect();
			//		this.start();
		} catch (IOException ex) {
			Logger.getLogger(ClientConnexionDateServer.class.getName()).log(Level.SEVERE, null, ex);
			disconnect();
		}
	}

	public void connect() throws IOException {
		try {
			ois = new ObjectInputStream(client.getInputStream());
			oos = new ObjectOutputStream(client.getOutputStream());
		} catch (Exception e1) {
			try {
				client.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return;
		}
	}

	public void disconnect() {
		try {
			// close streams and connections
			ois.close();
		} catch (IOException ex) {
			Logger.getLogger(ClientConnexionDateServer.class.getName()).log(Level.SEVERE, null, ex);
		}
		try {
			oos.close();
		} catch (IOException ex) {
			Logger.getLogger(ClientConnexionDateServer.class.getName()).log(Level.SEVERE, null, ex);
		}
		try {
			client.close();
		} catch (IOException ex) {
			Logger.getLogger(ClientConnexionDateServer.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public Serializable read() throws IOException, ClassNotFoundException {
		return (Serializable) ois.readObject();
	}

	public void write(Serializable object) throws IOException {
		oos.writeObject(object);
		oos.flush();
	}

	public void run() {
		try {
			Date date = new Date();
			for (int j = 0; j < 1000; j++) {
				write(date);
			}
		} catch (Exception e) {
		}
		disconnect();
	}
}
