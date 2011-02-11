package fr.prunetwork.communication.dateserver;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

public class DateClient {

	public static void main(String argv[]) {
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		Socket socket = null;
		Date date = null;
		try {
			// open a socket connection
			socket = new Socket("localhost", 3000);
			// open I/O streams for objects
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			// read an object from the server
			date = (Date) ois.readObject();
			System.out.print("The date is: " + date);
			oos.close();
			ois.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
