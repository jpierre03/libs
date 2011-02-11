/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.prunetwork.communication;

import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author jpierre03
 */
public interface Communicator extends Runnable {

	void connect() throws IOException;

	void disconnect();

	Serializable read();

	/**
	 * @param object bject to be writtent on the network socket
	 * @return	Indicate if the methods succeded (if true) or failed (if false).
	 *			This could happend if the socket is partially closed.
	 */
	boolean write(Serializable object);

	boolean isWellConnected();

	@Override
	void run();
}
