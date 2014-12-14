package fr.prunetwork.amqp;

import java.io.IOException;

/**
 * @author Jean-Pierre PRUNARET
 *         Created on 08/09/14.
 */
public interface AmqpReceiver<T extends AmqpReceivedMessage> {

    /**
     * Define settings to use AMQP broker and connect
     * @throws Exception
     */
    void configure() throws Exception;

    /**
     * This method is used to fetch messages one-by-one.
     * @return a fully constred java object or fail with exception
     * @throws Exception Exception is thrown if one or more field is missing.
     */
    T consume() throws Exception;

    /**
     * Close connexion with AMQP broker
     * @throws IOException
     */
    void close() throws IOException;
}
