package fr.prunetwork.communication.example.tp34.server;

/**
 * @author jpierre03
 */
public class MonServeurTest {

	/**
	 * Constructeur par défaut
	 */
	public MonServeurTest() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//	Pour tester un protocole simple de communication
//	Bien entendu il faut que serveur et client soient compatibles

		/** Affiche ou non les messages échangés sur la console*/
		boolean afficheConsole = false;
		/**initialisation du serveur*/
		MonServeur ms;
		if (args.length > 0) {
			/** Récupère le n° de port depsui la console*/
			ms = new MonServeur(Integer.parseInt(args[0]));
		} else {
			ms = new MonServeur(0);
		}
		System.out.println("On communique");

		/** Envoie un message d'accueil*/
		ms.ecrireClient("Bienvenue sur MonServeur - Pour quitter : taper \"fin\" ");

		/** Ecoute du client*/
		boolean continuer = true;
		/** Contient la ligne a échanger*/
		String ligne;
		while (continuer && ms != null && ms.clientOK()) {
			ligne = ms.lireClient();
			if (ligne.equalsIgnoreCase("fin")) {//peu importe la casse
				continuer = false;
				ms.ecrireClient("Au revoir");
			}
			if (afficheConsole) {
				System.out.println("Client : " + ligne);
			}
			ms.ecrireClient("recu :" + ligne);
		}
		System.out.println("On termine");
		ms.fermer();
	}
}
