package fr.prunetwork.amqp.message;

import fr.prunetwork.amqp.AmqpReceivedMessage;

/**
 * @author Jean-Pierre PRUNARET
 * @since 28/08/2014
 */
public final class AmqpReceivedMessageImpl implements AmqpReceivedMessage {

    private final String routingKey;
    private final String body;

    public AmqpReceivedMessageImpl(String routingKey, String body) {
        this.routingKey = routingKey;
        this.body = body;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public String getRoutingKey() {
        return routingKey;
    }

    @Override
    public void displayReceived() {
        System.out.printf(" [x] Received '%s':'%s' for area: '%s' isConsistent: %s %n", getRoutingKey(), getBody());
    }
}
