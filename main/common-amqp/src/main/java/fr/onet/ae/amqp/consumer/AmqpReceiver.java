package fr.onet.ae.amqp.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Jean-Pierre PRUNARET
 * @since 14/05/2014
 */
public class AmqpReceiver {

    private final URI uri;
    private final String topicName;
    private final Collection<String> bindingKeys;
    private final MessageConsumer externalConsumer;
    private QueueingConsumer consumer;

    public AmqpReceiver(URI uri, String topic, Collection<String> bindingKeys, MessageConsumer consumer) {
        this.uri = uri;
        this.topicName = topic;
        this.externalConsumer = consumer;
        this.bindingKeys = new ArrayList<>(bindingKeys);
    }

    public AmqpReceiver(String uri, String topic, Collection<String> bindingKeys, MessageConsumer consumer) throws URISyntaxException {
        this(new URI(uri), topic, bindingKeys, consumer);
    }

    public void configure() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(uri);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(topicName, "topic");
        String queueName = channel.queueDeclare().getQueue();

        for (String bindingKey : bindingKeys) {
            channel.queueBind(queueName, topicName, bindingKey);
        }

        consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

        externalConsumer.init(consumer);
    }
}
