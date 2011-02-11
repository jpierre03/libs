package fr.prunetwork.communication.example.tp34.client;

import fr.prunetwork.communication.example.tp34.Constants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Create a TCP connection with a server. Strings are exchanged
 * @author jpierre03
 */
public class StringClient {

	/**
	 */
	private Socket client = null;
	/**
	 */
	private BufferedReader input;
	/**
	 */
	private PrintWriter output;

	/**
	 * Create a TCP connection with a server. Strings are exchanged.
	 * @param hostName The destinattion hostname (FQDN or not). Work with IP addresse too.
	 * @param portNumber The server port number.
	 * @throws IOException if Connexion failed.
	 */
	public StringClient(String hostName, int portNumber) throws IOException {
		initConnexion(hostName, portNumber);
	}

	private void initConnexion(String hostName, int portNumber) throws IOException {
		try {
			// Convert a FQDN in InetAddress, DNS resolution
			// (Fully Qualified Domain Name)
			InetAddress ipAddress = InetAddress.getByName(hostName);
			// Socket's creation
			client = new Socket(ipAddress, portNumber);
			client.setSoTimeout(Constants.DEFAULT_SERVER_TIMEOUT);
		} catch (UnknownHostException e) {
			System.err.println("Server unknown : " + hostName);
			throw e;
		} catch (SocketException e) {
			System.err.println("Connexion error or timeout");
			throw e;
		} catch (IOException e) {
			System.err.println("Probleme de connexion sur:" + hostName);
			throw e;
		}
		System.out.println("Connexion OK sur " + hostName);
		try {
			input = new BufferedReader(new InputStreamReader(client.getInputStream())); //crée un flux à partir du socket client
			output = new PrintWriter(client.getOutputStream()); //crée un flux à partir du socket client
		} catch (IOException e) {
			System.err.println("PB creation des streams");
			System.exit(1);
		}
	}

	/**
	 * lit les caracteres envoyés par le serveur.
	 * @return un objet String qui contient l'ensemble des caractères lus
	 */
	public String read() {
		String line = null;
		try {
			line = input.readLine();
		} catch (IOException e) {
			//System.err.println("nothing to read");
		}
		return line;
	}

	/**
	 * Return a socket abject
	 * @return A socket abject
	 */
	Socket getClient() {
		return client;
	}

	/**
	 * Envoie des données au serveur.
	 * @param message les caractères à envoyer
	 */
	public void write(String message) {
		output.println(message);
		output.flush();
	}

	/**
	 * Teste la connexion.
	 * @return un booleen notifiant l'état de la connexion
	 */
	public boolean estConnect() {
		return client.isConnected();
	}

	/**
	 * Fermeture du socket & du lclient en général.
	 */
	public void close() {
		try {
			input.close();
			output.close();
			if (client != null) {
				client.close();
			}
			System.out.println("Fermeture ok");
		} catch (IOException e) {
			System.err.println("Erreur à la fermeture des flux!");
		}
	}

	/**
	 * Méthode invoquée lors du passage du ramasse miette
	 */
	@Override
	protected void finalize() {
		try {
			super.finalize();
		} catch (Throwable ex) {
			Logger.getLogger(StringClient.class.getName()).log(Level.SEVERE, null, ex);
		}
		close();
	}
}
