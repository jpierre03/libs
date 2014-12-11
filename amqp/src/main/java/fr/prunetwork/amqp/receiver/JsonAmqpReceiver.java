package fr.prunetwork.amqp.receiver;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import fr.prunetwork.amqp.AmqpReceiver;
import fr.prunetwork.amqp.ExchangeType;
import fr.prunetwork.amqp.message.AmqpReceivedMessageImpl;
import fr.prunetwork.amqp.message.JsonMessage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Jean-Pierre PRUNARET
 * @since 14/05/2014
 */
public final class JsonAmqpReceiver implements AmqpReceiver<JsonMessage> {

    private final URI uri;
    private final String topicName;
    private final Collection<String> bindingKeys;
    private final ExchangeType exchangeType;
    private QueueingConsumer consumer;

    public JsonAmqpReceiver(URI uri, String topic, Collection<String> bindingKeys, ExchangeType exchangeType) {
        this.uri = uri;
        this.topicName = topic;
        this.bindingKeys = new ArrayList<>(bindingKeys);
        this.exchangeType = exchangeType;
    }

    public JsonAmqpReceiver(String uri, String topic, Collection<String> bindingKeys, ExchangeType exchangeType) throws URISyntaxException {
        this(new URI(uri), topic, bindingKeys, exchangeType);
    }

    public void configure() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(uri);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.basicQos(10);

        channel.exchangeDeclare(topicName, exchangeType.name());
        String queueName = channel.queueDeclare().getQueue();

        for (String bindingKey : bindingKeys) {
            channel.queueBind(queueName, topicName, bindingKey);
        }

        consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, false, consumer);
    }

    @Override
    public JsonMessage consume() throws Exception {
        final QueueingConsumer.Delivery delivery = consumer.nextDelivery();
        final String message = new String(delivery.getBody());
        final String routingKey = delivery.getEnvelope().getRoutingKey();

        final JsonMessage localizedMessage = new JsonMessage(new AmqpReceivedMessageImpl(routingKey, message));

        // localizedMessage.displayReceived();

        consumer.getChannel().basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        return localizedMessage;
    }

    public void close() throws IOException {
        consumer.getChannel().close();
    }
}
