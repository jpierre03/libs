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
    private final boolean isDurable;
    private QueueingConsumer consumer;

    public AmqpReceiver(@NotNull final URI uri,
                        @NotNull final String topic,
                        @NotNull final Collection<String> bindingKeys,
                        @NotNull final MessageConsumer consumer,
                        final boolean isDurable) {
        this.uri = uri;
        this.topicName = topic;
        this.externalConsumer = consumer;
        this.bindingKeys = new ArrayList<>(bindingKeys);
        this.isDurable = isDurable;
    }

    public AmqpReceiver(@NotNull final String uri,
                        @NotNull final String topic,
                        @NotNull final Collection<String> bindingKeys,
                        @NotNull final MessageConsumer consumer,
                        final boolean  isDurable) throws URISyntaxException {

        this(new URI(uri), topic, bindingKeys, consumer, isDurable);
    }

    public void configure() throws Exception {
        @NotNull final ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(uri);
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.exchangeDeclare(topicName, ExchangeType.topic.name(), isDurable);
        String queueName = channel.queueDeclare().getQueue();

        for (final String bindingKey : bindingKeys) {
            channel.queueBind(queueName, topicName, bindingKey);
        }

        consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

        externalConsumer.init(consumer);
    }
}
