package fr.prunetwork.communication.example.tp34.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jpierre03
 */
public class MonServeurMultiClient {

	/** Détermine le nombre de threads qui sont exécutés en même temps*/
	private static Executor exec = Executors.newFixedThreadPool(10);
	/** Contient la référence de la socket*/
	private ServerSocket serveur = null;
	/** Pour savoir si on continue ou non l'execution */
	private boolean onContinue = true;
	/** Pour savoir si la communication est terminée*/
	private boolean isEnded = false;
	/** pour savor combien de temps on doit attendre le client*/
	private int dureeTimeout = 100;
	/**
	 *
	 */
	private Vector<CommunicationClientServeur> listComm = new Vector<CommunicationClientServeur>();

	/**
	 *
	 * @param port
	 */
	public MonServeurMultiClient(int port) {// création d'un serveur
		CommunicationClientServeur comm = null;
		if (port < 1023) {// ports reserves
			System.err.println("erreur de choix du port ! 2222 par defaut");
			port = 2222;
		}
		try {
			// on cree un serveur sur le port specifie & sur touttes les @IP de la machine
			serveur = new ServerSocket(port);
			System.out.println("J'ecoute le port: " + port + "...");
		} catch (IOException e) {
			System.err.println("Impossible d'écouter le port " + port);
			System.exit(1);
		}

		while (onContinue == true) {
			/** s'il y a une requete sur le portServeur
			 * on cree un Socket pour communiquer avec le client
			 * On attend jusqu'a ce qu'il y ait une requete
			 */
			try {
				/** attend au max x Secondes*/
				serveur.setSoTimeout(dureeTimeout * 1000);
			} catch (SocketException ex) {
				System.err.println("On quitte : setSoTimeOut");
				System.exit(1);
			}
			try {
				//client = serveur.accept(); //"client" est le Socket
				final Socket connexion = serveur.accept();
				comm = new CommunicationClientServeur(connexion);
				comm.setMonServeurMulticlient(this);
				listComm.add(comm);
				comm.ecrireClient("Bonjour nouveau client !");
				System.out.println("Connexion Acceptée");
			} catch (SocketTimeoutException e) {
				System.err.println("On quitte : TimeOut");
				System.exit(1);
			} catch (IOException e) {
				System.err.println("Client refusé !.");
				System.exit(1);
			}
			exec.execute(comm);

		}
	}

	/**
	 * Pout finir proprement le serveur
	 */
	public void end() {
		Iterator<CommunicationClientServeur> it = listComm.iterator();
		/** On parcours la liste de tous les client connectés et on ferme la connexion*/
		while (it.hasNext()) {
			listComm.remove(it.next().fermer());
		}
		try {
			serveur.close();
		} catch (IOException ex) {
			Logger.getLogger(MonServeurMultiClient.class.getName()).log(Level.SEVERE, null, ex);
		}
		/** A ce stade, toutes les communications doivent être terminées*/
		isEnded = true;
	}

	/**
	 * Pour savoir si on laisse le serveur lancé
	 * @return on continue ou on arrete le serveur (True/false)
	 */
	public synchronized boolean getOnContinue() {
		return onContinue;
	}

	/**
	 * Le serveur est il arrêté ? (True/False)
	 * @return
	 */
	public synchronized boolean isEnded() {
		return isEnded;
	}

	/**
	 * Pour écrire à toui
	 * @param ligne
	 */
	public void ecrireTousClient(String ligne) {
		if (ligne != null) {
			Iterator<CommunicationClientServeur> iterator = listComm.iterator();
			/** on envoie le message à tous les membres de la liste*/
			while (iterator.hasNext()) {
				//iterator.next().ecrireClient(ligne);
				CommunicationClientServeur ccs = iterator.next();
				System.out.println("ecrireTousClient - next " + ccs);
				ccs.ecrireClient(ligne);
			}
		}
	}
}
