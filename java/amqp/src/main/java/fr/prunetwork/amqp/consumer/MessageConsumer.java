package fr.prunetwork.amqp.consumer;

import com.rabbitmq.client.QueueingConsumer;
import fr.prunetwork.amqp.AmqpReceivedMessage;

/**
 * @author Jean-Pierre PRUNARET
 * @since 01/07/2014
 */
public interface MessageConsumer<T extends AmqpReceivedMessage> {

    void init(QueueingConsumer consumer);

    boolean isConnected();

    T consume() throws Exception;
}
