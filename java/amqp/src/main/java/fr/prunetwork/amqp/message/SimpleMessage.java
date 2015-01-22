package fr.prunetwork.amqp.message;

import fr.prunetwork.amqp.AmqpReceivedMessage;

/**
 * @author Jean-Pierre PRUNARET
 * @since 28/08/2014
 */
public final class SimpleMessage
        extends AmqpReceivedMessageImpl
        implements AmqpReceivedMessage {

    public SimpleMessage(String routingKey, String body) {
        super(routingKey, body);
    }
}
