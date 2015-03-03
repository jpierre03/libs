package fr.prunetwork.amqp.consumer;

import com.rabbitmq.client.QueueingConsumer;
import fr.prunetwork.amqp.AmqpReceivedMessage;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jean-Pierre PRUNARET
 * @since 01/07/2014
 */
public interface MessageConsumer<T extends AmqpReceivedMessage> {

    void init(@NotNull final QueueingConsumer consumer);

    boolean isConnected();

    T consume() throws Exception;
}
