package fr.prunetwork.amqp.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import fr.prunetwork.amqp.ExchangeType;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Jean-Pierre PRUNARET
 * @since 14/05/2014
 */
public class AmqpReceiver {

    @NotNull
    private final URI uri;
    @NotNull
    private final String topicName;
    @NotNull
    private final Collection<String> bindingKeys;
    @NotNull
    private final MessageConsumer externalConsumer;
    private QueueingConsumer consumer;

    public AmqpReceiver(@NotNull URI uri,
                        @NotNull String topic,
                        @NotNull Collection<String> bindingKeys,
                        @NotNull MessageConsumer consumer) {
        this.uri = uri;
        this.topicName = topic;
        this.externalConsumer = consumer;
        this.bindingKeys = new ArrayList<>(bindingKeys);
    }

    public AmqpReceiver(@NotNull String uri,
                        @NotNull String topic,
                        @NotNull Collection<String> bindingKeys,
                        @NotNull MessageConsumer consumer) throws URISyntaxException {

        this(new URI(uri), topic, bindingKeys, consumer);
    }

    public void configure() throws Exception {
        @NotNull ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(uri);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(topicName, ExchangeType.topic.name());
        String queueName = channel.queueDeclare().getQueue();

        for (String bindingKey : bindingKeys) {
            channel.queueBind(queueName, topicName, bindingKey);
        }

        consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

        externalConsumer.init(consumer);
    }
}
