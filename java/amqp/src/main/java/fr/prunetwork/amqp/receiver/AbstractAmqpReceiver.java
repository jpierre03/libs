package fr.prunetwork.amqp.receiver;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import fr.prunetwork.amqp.AmqpReceivedMessage;
import fr.prunetwork.amqp.AmqpReceiver;
import fr.prunetwork.amqp.ExchangeType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Jean-Pierre PRUNARET
 * @since 14/05/2014
 */
public abstract class AbstractAmqpReceiver<T extends AmqpReceivedMessage> implements AmqpReceiver<T> {

    private final URI uri;
    private final String topicName;
    private final Collection<String> bindingKeys;
    private final ExchangeType exchangeType;
    private QueueingConsumer consumer;

    public AbstractAmqpReceiver(URI uri, String topic, Collection<String> bindingKeys, ExchangeType exchangeType) {
        this.uri = uri;
        this.topicName = topic;
        this.bindingKeys = new ArrayList<>(bindingKeys);
        this.exchangeType = exchangeType;
    }

    public AbstractAmqpReceiver(String uri, String topic, Collection<String> bindingKeys, ExchangeType exchangeType) throws URISyntaxException {
        this(new URI(uri), topic, bindingKeys, exchangeType);
    }

    @Override
    public void configure() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(uri);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.basicQos(10);

        channel.exchangeDeclare(topicName, exchangeType.name(), true);
        String queueName = channel.queueDeclare().getQueue();

        for (String bindingKey : bindingKeys) {
            channel.queueBind(queueName, topicName, bindingKey);
        }

        consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, false, consumer);
    }

    @Override
    public abstract T consume() throws Exception;

    @Override
    public void close() throws IOException {
        consumer.getChannel().close();
    }

    protected QueueingConsumer getConsumer() {
        return consumer;
    }
}
