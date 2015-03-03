package fr.prunetwork.amqp.receiver;

import com.rabbitmq.client.QueueingConsumer;
import fr.prunetwork.amqp.AmqpReceiver;
import fr.prunetwork.amqp.ExchangeType;
import fr.prunetwork.amqp.message.SimpleMessage;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

/**
 * @author Jean-Pierre PRUNARET
 * @since 14/05/2014
 */
public final class SimpleAmqpReceiver
        extends AbstractAmqpReceiver<SimpleMessage>
        implements AmqpReceiver<SimpleMessage> {

    public SimpleAmqpReceiver(@NotNull final URI uri,
                              @NotNull final String topic,
                              @NotNull final Collection<String> bindingKeys,
                              @NotNull final ExchangeType exchangeType,
                              final boolean isDurable) {
        super(uri, topic, bindingKeys, exchangeType, isDurable);
    }

    public SimpleAmqpReceiver(@NotNull final String uri,
                              @NotNull final String topic,
                              @NotNull final Collection<String> bindingKeys,
                              @NotNull final ExchangeType exchangeType,
                              final boolean isDurable) throws URISyntaxException {
        this(new URI(uri), topic, bindingKeys, exchangeType, isDurable);
    }

    @NotNull
    @Override
    public SimpleMessage consume() throws Exception {
        if (getConsumer() != null) {
            @NotNull final QueueingConsumer.Delivery delivery = getConsumer().nextDelivery();
            @NotNull final String message = new String(delivery.getBody());
            @NotNull final String routingKey = delivery.getEnvelope().getRoutingKey();

            @NotNull final SimpleMessage m = new SimpleMessage(routingKey, message);

            // m.displayMessage();

            getConsumer().getChannel().basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            return m;
        } else {
            throw new IllegalStateException("consumer is null");
        }
    }
}
