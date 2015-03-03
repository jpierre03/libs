package fr.prunetwork.amqp.message;

import fr.prunetwork.amqp.AmqpReceivedMessage;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jean-Pierre PRUNARET
 * @since 28/08/2014
 */
public final class SimpleMessage
        extends AmqpReceivedMessageImpl
        implements AmqpReceivedMessage {

    public SimpleMessage(@NotNull final String routingKey, @NotNull final String body) {
        super(routingKey, body);
    }
}
