package fr.prunetwork.communication.example.tp34.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author jpierre03
 */
public class MonClientTest {

    /**
     * Constructeur de la classe de test
     */
    public MonClientTest() {
    }

    /**
     * Le programme commence ici.
     * Le test se fait en réalisant un connexion du client sur un serveur en console
     *
     * @param args Ce sont les paramètres de la ligne de commande
     */
    public static void main(String[] args) {
// Pour tester un protocole simple de communication
// Bien entendu il faut que serveur et client soient compatibles

// initialisation du client
// on peut passer server et port par la ligne de commande

        StringClient mc = null; //initialisation de la variable locale
        try {
            if (args.length > 1) {
                mc = new StringClient(args[0], Integer.parseInt(args[1]));
            } else {
                mc = new StringClient("localhost", 2222);
            }
            System.out.println("mc = " + mc + "client:" + mc.getClient());
        } catch (IOException e) {
            System.err.println("erreur de realisation du client" + mc);
            if (mc != null) {
                mc.close();
                mc = null; //on perd l'objet
            }
        }

        if (mc != null) {
// Que dit le serveur ?
            String ligne = mc.read();
            System.out.println("Serveur :" + ligne);
//Requete au serveur a partir de la console
            BufferedReader console = new BufferedReader(
                    new InputStreamReader(System.in));
            String entree;
            try {
                while ((entree = console.readLine()) != null) {
                    mc.write(entree);
                    ligne = mc.read();
                    System.out.println("Serveur :" + ligne);// pour voir
                    if (ligne.equals("Au revoir")) {
                        break;
                    }
                }

                console.close();

            } catch (IOException e) {
                System.err.println("Erreur de lecture console");
            }

            mc.close();
        } else {
            System.err.println("Impossible de créer le client, mc ==null !!");
        }
    }
}
