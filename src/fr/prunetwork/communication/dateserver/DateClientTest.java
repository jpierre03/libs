package fr.prunetwork.communication.dateserver;

import fr.prunetwork.communication.Constants;
import fr.prunetwork.communication.ObjectClient;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DateClientTest {

	/** Détermine le nombre de threads qui sont exécutés en même temps*/
	private static Executor exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	public static void main(String argv[]) {
		try {
			//		for (int i = 0; i < 1000000; i++) {
			exec.execute(new ObjectClient("2001:470:c92d::1:2", Constants.DEFAULT_SERVER_PORT_NUMBER));
			//		}
		} catch (IOException ex) {
			Logger.getLogger(DateClientTest.class.getName()).log(Level.SEVERE, null, ex);
		}
		//-----------
		try {
			Thread.sleep(5 * 1000);
		} catch (InterruptedException ex) {
			Logger.getLogger(DateClientTest.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.exit(0);
	}

	private DateClientTest() {
	}
}
