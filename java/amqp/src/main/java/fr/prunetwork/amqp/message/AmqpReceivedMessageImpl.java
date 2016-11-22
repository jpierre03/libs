package fr.prunetwork.amqp.message;

import fr.prunetwork.amqp.AmqpReceivedMessage;
import org.jetbrains.annotations.NotNull;

import java.io.OutputStream;
import java.io.PrintStream;
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

    protected AmqpReceivedMessageImpl(@NotNull final String routingKey, @NotNull final String body) {
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
    public final void printMessage(@NotNull final PrintStream os) {
        os.println(toString());
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

    public void displayFullMessage(@NotNull final PrintStream out) {
        out.append("->").append("\n");
        out.append("-date:").append(getReceivedDate().toString()).append("\n");
        out.append("-key :").append(getRoutingKey()).append("\n");
        out.append("-body:").append(getBody()).append("\n");
        out.append("<-").append("\n");
    }
}
