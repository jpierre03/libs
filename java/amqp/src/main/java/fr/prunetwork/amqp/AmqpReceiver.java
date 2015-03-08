package fr.prunetwork.amqp;

import org.jetbrains.annotations.NotNull;

/**
 * This interface defines a generic object that receive data through AMQP.
 *
 * @author Jean-Pierre PRUNARET
 * @since 2014-09-08
 */
public interface AmqpReceiver<T extends AmqpReceivedMessage> {

    /**
     * Define settings to use AMQP broker and connect
     * <p>
     * This method must be called right after object construction.
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
    @NotNull
    T consume() throws Exception;

    /**
     * Close connexion with AMQP broker
     *
     * @throws Exception Exception is thrown when something fail.
     */
    void close() throws Exception;
}
