package fr.prunetwork.amqp.receiver;

import com.rabbitmq.client.QueueingConsumer;
import fr.prunetwork.amqp.AmqpReceiver;
import fr.prunetwork.amqp.ExchangeType;
import fr.prunetwork.amqp.message.SimpleMessage;

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

    public SimpleAmqpReceiver(URI uri, String topic, Collection<String> bindingKeys, ExchangeType exchangeType) {
        super(uri, topic, bindingKeys, exchangeType);
    }

    public SimpleAmqpReceiver(String uri, String topic, Collection<String> bindingKeys, ExchangeType exchangeType) throws URISyntaxException {
        this(new URI(uri), topic, bindingKeys, exchangeType);
    }

    @Override
    public SimpleMessage consume() throws Exception {
        final QueueingConsumer.Delivery delivery = getConsumer().nextDelivery();
        final String message = new String(delivery.getBody());
        final String routingKey = delivery.getEnvelope().getRoutingKey();

        final SimpleMessage m = new SimpleMessage(routingKey, message);

        // m.displayMessage();

        getConsumer().getChannel().basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        return m;
    }
}
