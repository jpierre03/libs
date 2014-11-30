package fr.prunetwork.amqp;

import java.io.IOException;

/**
 * @author Jean-Pierre PRUNARET
 *         Created on 08/09/14.
 */
public interface AmqpReceiver<T extends AmqpReceivedMessage> {

    void configure() throws Exception;

    T consume() throws Exception;

    void close() throws IOException;
}
