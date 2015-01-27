package fr.prunetwork.amqp.consumer;

import com.rabbitmq.client.QueueingConsumer;
import fr.prunetwork.amqp.message.SimpleMessage;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jean-Pierre PRUNARET
 * @since 14/12/14
 */
public class SimpleMessageConsumer implements MessageConsumer<SimpleMessage> {

    protected QueueingConsumer consumer;

    @Override
    public SimpleMessage consume() throws InterruptedException {
        final QueueingConsumer.Delivery delivery = consumer.nextDelivery();
        @NotNull final String message = new String(delivery.getBody());
        final String routingKey = delivery.getEnvelope().getRoutingKey();

        @NotNull final SimpleMessage receivedMessage = new SimpleMessage(routingKey, message);

        receivedMessage.displayFullMessage();
        return receivedMessage;
    }

    @Override
    public void init(@NotNull QueueingConsumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public boolean isConnected() {
        return consumer != null;
    }
}
