package fr.prunetwork.amqp.consumer;

import com.rabbitmq.client.QueueingConsumer;
import fr.prunetwork.amqp.message.SimpleMessage;

/**
 * @author Jean-Pierre PRUNARET
 * @since 14/12/14
 */
public class SimpleMessageConsumer implements MessageConsumer<SimpleMessage> {

    protected QueueingConsumer consumer;

    @Override
    public SimpleMessage consume() throws InterruptedException {
        final QueueingConsumer.Delivery delivery = consumer.nextDelivery();
        final String message = new String(delivery.getBody());
        final String routingKey = delivery.getEnvelope().getRoutingKey();

        final SimpleMessage receivedMessage = new SimpleMessage(routingKey, message);

        receivedMessage.displayFullMessage();
        return receivedMessage;
    }

    @Override
    public void init(QueueingConsumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public boolean isConnected() {
        return consumer != null;
    }
}
