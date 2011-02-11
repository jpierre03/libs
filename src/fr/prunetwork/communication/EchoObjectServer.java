package fr.prunetwork.communication;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EchoObjectServer extends Thread {

	private ServerSocket dateServer;
	/** Détermine le nombre de threads qui sont exécutés en même temps*/
	private static Executor exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	public static void main(String argv[]) throws Exception {
		new EchoObjectServer();
	}

	public EchoObjectServer() throws Exception {
		dateServer = new ServerSocket(Constants.DEFAULT_SERVER_PORT_NUMBER);
		System.out.println("Server listening on port " + Constants.DEFAULT_SERVER_PORT_NUMBER + ".");
		this.start();
	}

	@Override
	public void run() {
		while (true) {
			try {
//				System.out.println("Waiting for connections.");
				Socket client = dateServer.accept();
				System.out.println("Accepted a connection from: " + client.getInetAddress()+" port:"+client.getLocalPort());
				ClientConnexionHandler_Echo c = new ClientConnexionHandler_Echo(client);
				exec.execute(c);
			} catch (Exception e) {
				System.out.println("run Exception");
			}
		}
	}
}
