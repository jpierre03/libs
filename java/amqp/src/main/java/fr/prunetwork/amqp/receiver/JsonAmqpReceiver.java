package fr.prunetwork.amqp.receiver;

import com.rabbitmq.client.QueueingConsumer;
import fr.prunetwork.amqp.AmqpReceiver;
import fr.prunetwork.amqp.ExchangeType;
import fr.prunetwork.amqp.message.JsonMessage;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

/**
 * @author Jean-Pierre PRUNARET
 * @since 14/05/2014
 */
public final class JsonAmqpReceiver
        extends AbstractAmqpReceiver<JsonMessage>
        implements AmqpReceiver<JsonMessage> {

    public JsonAmqpReceiver(URI uri, String topic, Collection<String> bindingKeys, ExchangeType exchangeType) {
        super(uri, topic, bindingKeys, exchangeType);
    }

    public JsonAmqpReceiver(String uri, String topic, Collection<String> bindingKeys, ExchangeType exchangeType) throws URISyntaxException {
        this(new URI(uri), topic, bindingKeys, exchangeType);
    }

    @Override
    public JsonMessage consume() throws Exception {
        final QueueingConsumer.Delivery delivery = getConsumer().nextDelivery();
        final String message = new String(delivery.getBody());
        final String routingKey = delivery.getEnvelope().getRoutingKey();

        final JsonMessage localizedMessage = new JsonMessage(routingKey, message);

        // localizedMessage.displayMessage();

        getConsumer().getChannel().basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        return localizedMessage;
    }
}
