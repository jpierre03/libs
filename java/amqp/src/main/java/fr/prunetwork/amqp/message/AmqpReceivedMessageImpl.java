package fr.prunetwork.amqp.message;

import fr.prunetwork.amqp.AmqpReceivedMessage;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

/**
 * @author Jean-Pierre PRUNARET
 * @since 28/08/2014
 */
class AmqpReceivedMessageImpl implements AmqpReceivedMessage {

    @NotNull
    private final String routingKey;
    @NotNull
    private final String body;
    @NotNull
    private final Date receptionDate;

    protected AmqpReceivedMessageImpl(@NotNull String routingKey, @NotNull String body) {
        this.routingKey = routingKey;
        this.body = body;
        this.receptionDate = new Date();
    }

    @NotNull
    @Override
    public final String getBody() {
        return body;
    }

    @NotNull
    @Override
    public final String getRoutingKey() {
        return routingKey;
    }

    @Override
    public final void displayMessage() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return String.format("%s -> %s", getRoutingKey(), getBody());
    }

    @NotNull
    @Override
    public final Date getReceivedDate() {
        return receptionDate;
    }

    public void displayFullMessage() {
        @NotNull final StringBuffer sb = new StringBuffer();

        sb.append("->").append("\n");
        sb.append("-date:").append(getReceivedDate()).append("\n");
        sb.append("-key :").append(getRoutingKey()).append("\n");
        sb.append("-body:").append(getBody()).append("\n");
        sb.append("<-").append("\n");

        System.out.print(sb.toString());
    }
}
