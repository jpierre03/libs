package fr.prunetwork.amqp.consumer;

import com.rabbitmq.client.QueueingConsumer;
import fr.prunetwork.amqp.AmqpReceivedMessage;

/**
 * @author Jean-Pierre PRUNARET
 * @since 01/07/2014
 */
public interface MessageConsumer {

    void init(QueueingConsumer consumer);

    boolean isConnected();

    AmqpReceivedMessage consume() throws Exception;
}
