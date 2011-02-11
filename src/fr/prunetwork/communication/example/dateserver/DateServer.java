package fr.prunetwork.communication.example.dateserver;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DateServer extends Thread {

	private ServerSocket dateServer;
	/** Détermine le nombre de threads qui sont exécutés en même temps*/
	private static Executor exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

	public static void main(String argv[]) throws Exception {
		new DateServer();
	}

	public DateServer() throws Exception {
		dateServer = new ServerSocket(3000);
		System.out.println("Server listening on port 3000.");
		this.start();
	}

	@Override
	public void run() {
		while (true) {
			try {
//				System.out.println("Waiting for connections.");
				Socket client = dateServer.accept();
//				System.out.println("Accepted a connection from: "+ client.getInetAddress());
				ClientConnexion c = new ClientConnexion(client);
				exec.execute(c);
			} catch (Exception e) {
			}
		}
	}
}

class ClientConnexion extends Thread {

	private Socket client = null;
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;

	public ClientConnexion() {
	}

	public ClientConnexion(Socket clientSocket) {
		client = clientSocket;
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
//		this.start();
	}

	@Override
	public void run() {
		try {
			Date date = new Date();
			for (int j = 0; j < 1000; j++) {
				oos.writeObject(date);
			}
//			oos.writeObject(new Date());
			oos.flush();
			// close streams and connections
			ois.close();
			oos.close();
			client.close();
		} catch (Exception e) {
		}
	}
}
