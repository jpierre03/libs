package fr.prunetwork.communication.example.tp34.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * @author Marc-Emmanuel Bellemare Jan. 2005
 * @author jpierre03
 */
public class MonServeur {

    /** Socket de communication, d'écoute générale*/
    private ServerSocket serveur = null;
    /** Socket de communication avec un client*/
    private Socket client = null;
    /** Flux d'entrée*/
    private BufferedReader entree;
    /** Flux de sortie*/
    private PrintWriter sortie;
    /** Délais d'attente d'un client*/
    private int dureeTimeout = 100;

    /** Contructeur qui specifie le port écoute devraît etre au dessus de 1024
     * @param port le numero du port
     */
    public MonServeur(int port) {
        /** On considère les ports réservés et on affecte une valeur par défaut*/
        if (port < 1023) {
            System.err.println("erreur de choix du port ! 2222 par défaut");
            port = 2222;
        }
        try {
            /**on crée un serveur sur le port spécifie*/
            serveur = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Impossible d'écouter le port " + port);
            System.exit(1);
        }
        System.out.println("J'ecoute le port: " + port + "...");
        try {
            serveur.setSoTimeout(dureeTimeout * 1000); // attend au max duréeTimeout secondes
            /** s'il y a une requête sur le port
             * on crée un Socket pour communiquer avec le client
             * On attend jusqu'à ce qu'il y ait une requête
             **/
            /**"client" est le Socket*/
            client = serveur.accept();

        } catch (SocketTimeoutException e) {
            System.err.println("On quitte : TimeOut");
            System.exit(1);

        } catch (IOException e) {
            System.err.println("Client refusé !.");
            System.exit(1);
        }
        System.out.println("client accepté !");
        try {
            // on recupere les canaux de communication
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

    /** lit les caractères envoyés par le client.
     * @return un objet String qui contient l'ensemble des caractères lus
     */
    public String lireClient() {
        String ligne = null;
        try {
            ligne = entree.readLine();
        } catch (IOException e) {
            System.err.println("rien a lire");
        }
        return ligne;
    }

    /** Envoie des données au client.
     * @param ligne les caractères à envoyer
     */
    public void ecrireClient(String ligne) {
        if (sortie == null) {
            System.out.println("pas de sortie !!! : écrire ?");
        } else {
            sortie.println(ligne);
            sortie.flush();
        }
    }

    /** teste la connexion.
     *@return un booléen notifiant l'état de la connexion
     */
    public boolean clientOK() {
        return client.isConnected();
    }

    /** Fermeture du socket.
     */
    public void fermer() {
        // il faut fermer "proprement" les stream avant les Sockets
        try {
            entree.close();
            sortie.close();
            if (client != null) {
                client.close();
            }
            if (serveur != null) {
                serveur.close();
            }
            System.out.println("Fermeture ok");
        } catch (IOException e) {
            System.err.println("Erreur à la fermeture des flux!");
        }
    }

    /**
     *
     */
    @Override
    protected void finalize() {
        // méthode executee par le ramasse miettes avant de libérer la mémoire
        // pb : on ne sait jamais trop quand !!!
        fermer();
    }
}
