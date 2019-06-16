package fr.prunetwork.amqp.consumer;

import com.rabbitmq.client.QueueingConsumer;
import fr.prunetwork.amqp.message.SimpleMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Jean-Pierre PRUNARET
 * @since 14/12/14
 */
public class SimpleMessageConsumer implements MessageConsumer<SimpleMessage> {

    @Nullable
    protected QueueingConsumer consumer;

    @Override
    @NotNull
    public SimpleMessage consume() throws InterruptedException {
        if(consumer==null){
            throw new IllegalStateException("consumer is null. Init must be called before");
        }
        @NotNull final QueueingConsumer.Delivery delivery = consumer.nextDelivery();
        @NotNull final String message = new String(delivery.getBody());
        @NotNull final String routingKey = delivery.getEnvelope().getRoutingKey();

        @NotNull final SimpleMessage receivedMessage = new SimpleMessage(routingKey, message);

        receivedMessage.displayFullMessage(System.out);
        return receivedMessage;
    }

    @Override
    public void init(@NotNull final QueueingConsumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public boolean isConnected() {
        return consumer != null;
    }
}
