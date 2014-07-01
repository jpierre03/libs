package amqp.consumer;

import com.rabbitmq.client.QueueingConsumer;

/**
 * @author Jean-Pierre PRUNARET
 * @since 01/07/2014
 */
interface MessageConsumer {

    void init(QueueingConsumer consumer);

    boolean isConnected();

    void consume() throws InterruptedException;
}
