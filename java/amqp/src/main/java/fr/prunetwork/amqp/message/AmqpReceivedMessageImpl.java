package fr.prunetwork.amqp.message;

import fr.prunetwork.amqp.AmqpReceivedMessage;

import java.util.Date;

/**
 * @author Jean-Pierre PRUNARET
 * @since 28/08/2014
 */
class AmqpReceivedMessageImpl implements AmqpReceivedMessage {

    private final String routingKey;
    private final String body;
    private final Date receptionDate;

    protected AmqpReceivedMessageImpl(String routingKey, String body) {
        this.routingKey = routingKey;
        this.body = body;
        this.receptionDate = new Date();
    }

    @Override
    public final String getBody() {
        return body;
    }

    @Override
    public final String getRoutingKey() {
        return routingKey;
    }

    @Override
    public final void displayReceived() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return String.format("%s -> %s", getRoutingKey(), getBody());
    }

    @Override
    public final Date getReceivedDate() {
        return receptionDate;
    }

    public void displayFullMessage() {
        StringBuffer sb = new StringBuffer();

        sb.append("->").append("\n");
        sb.append("-date:").append(getReceivedDate()).append("\n");
        sb.append("-key :").append(getRoutingKey()).append("\n");
        sb.append("-body:").append(getBody()).append("\n");
        sb.append("<-").append("\n");

        System.out.print(sb.toString());
    }
}
