package fr.prunetwork.amqp;

/**
 * This interface defines a generic object that receive data through AMQP.
 *
 * @author Jean-Pierre PRUNARET
 *         Created on 08/09/14.
 */
public interface AmqpReceiver<T extends AmqpReceivedMessage> {

    /**
     * Define settings to use AMQP broker and connect
     *
     * @throws Exception
     */
    void configure() throws Exception;

    /**
     * This method is used to fetch messages one-by-one.
     *
     * @return a fully build java object or fail with exception
     * @throws Exception Exception is thrown if one or more field is missing or when something fail.
     */
    T consume() throws Exception;

    /**
     * Close connexion with AMQP broker
     *
     * @throws Exception Exception is thrown when something fail.
     */
    void close() throws Exception;
}
