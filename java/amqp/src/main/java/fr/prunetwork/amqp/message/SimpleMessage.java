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

    @Override
    public String toString() {
        return String.format("%s -> %s", getRoutingKey(), getBody());
    }
}
