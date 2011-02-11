package fr.prunetwork.communicator.network.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * C'est dans cette classe que les protocoles d'échange sont définis (cycle de lecture/ecriture)
 * @author jpierre03
 */
public class CommunicationClientServeur implements Runnable {

	/**
	 */
	private boolean DEBUG = true;
	/**
	 */
	Socket client = null;
	/**
	 */
	private BufferedReader entree;
	/**
	 */
	private PrintWriter sortie;
	/**
	 */
	private boolean onContinue = true;
	/**
	 */
	MonServeurMultiClient msMC = null;

	/**
	 * permet de définir quel est l'objet qui a instancié cet objet
	 * Est utilisé pour avoir un accès aux méthodes publiques
	 * @param msMC
	 * @return
	 */
	public CommunicationClientServeur setMonServeurMulticlient(MonServeurMultiClient msMC) {
		this.msMC = msMC;
		return this;
	}

	/**
	 * @param socket Est un socket pour communiquer avec le client
	 */
	public CommunicationClientServeur(Socket socket) {
		System.err.println("CommunicationClientServeur");
		client = socket;

		try {
			// on recupère les canaux de communication
			// avec des filtres de lecture ecriture de donnees texte
			entree = new BufferedReader(new InputStreamReader(client.getInputStream()));
			sortie = new PrintWriter(client.getOutputStream());
		} catch (IOException e) {
			System.err.println("PB création des streams");
			System.exit(1);
		}
		if (entree == null) {
			System.out.println("pas d'entrée !!!");
		}
		if (sortie == null) {
			System.out.println("pas de sortie !!!");
		}
	}

	/**
	 * Lecture d'un message envoyé par le serveur
	 * @return le message envoyé par le serveur
	 */
	public String lireClient() {
		String ligne = null;
		try {
			//if(entree.ready())
			ligne = entree.readLine();
		} catch (IOException e) {
			System.err.println("rien a lire");
		}
		return ligne;
	}

	/**
	 * Envoie des données au client.
	 * @param ligne les caractères à envoyer
	 */
	public void ecrireClient(String ligne) {
		if (sortie == null) {
			System.out.println("pas de sortie !!! : écrire ?");
		} else {
			//if(ligne!=null){
			sortie.println(ligne);
			sortie.flush();
			System.out.println("CommunicationClientServeur " + this + "message : " + ligne);
			//}
		}
	}

	/**
	 * Pour afficher un message avec tous les personnes connectées
	 * @param ligne un message
	 */
	public void ecrireTousClient(String ligne) {
		System.err.println("ecrireTousClient");
		if (msMC != null) {
			if (ligne != null) {
				msMC.ecrireTousClient(ligne);
				System.err.println("ecrireTousClient - ok");
			}

		} else {
			if (ligne != null) {
				ecrireClient(ligne);
			}
			System.err.println("ecrireTousClient - msMC ==null");
		}
	}

	/**
	 * Teste la connexion.
	 *@return un booléen notifiant l'état de la connexion
	 */
	public boolean clientOK() {
		return client.isConnected();
	}

	/**
	 * Fermeture du socket.
	 * @return
	 */
	public CommunicationClientServeur fermer() {
		// il faut fermer "proprement" les stream avant les Sockets
		onContinue = false;
		try {
			entree.close();
			sortie.close();
			if (client != null) {
				client.close();
			}
			System.out.println("Fermeture ok");
		} catch (IOException e) {
			System.err.println("Erreur à la fermeture des flux!");
		}
		return this;
	}

	/**
	 */
	@Override
	protected void finalize() {
		// méthode executee par le ramasse miettes avant de libérer la mémoire
		// pb : on ne sait jamais trop quand !!!
		fermer();
	}

	/**
	 */
	@Override
	public void run() {
		while (onContinue) {
			String message = lireClient();
			if (message != null) {
				ecrireTousClient(message);
				System.out.println("ecrireClient(" + message + ")");
			}
		}
	}
}
