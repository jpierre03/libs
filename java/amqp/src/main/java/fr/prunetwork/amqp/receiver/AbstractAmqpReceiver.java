package fr.prunetwork.amqp.receiver;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import fr.prunetwork.amqp.AmqpReceivedMessage;
import fr.prunetwork.amqp.AmqpReceiver;
import fr.prunetwork.amqp.ExchangeType;
import org.jetbrains.annotations.NotNull;

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

    @NotNull
    private final URI uri;
    @NotNull
    private final String topicName;
    @NotNull
    private final Collection<String> bindingKeys;
    @NotNull
    private final ExchangeType exchangeType;
    private QueueingConsumer consumer;
    private final boolean isDurable;

    protected AbstractAmqpReceiver(@NotNull URI uri,
                                   @NotNull String topic,
                                   @NotNull Collection<String> bindingKeys,
                                   @NotNull ExchangeType exchangeType,
                                   boolean isDurable) {
        this.uri = uri;
        this.topicName = topic;
        this.isDurable = isDurable;
        this.bindingKeys = new ArrayList<>(bindingKeys);
        this.exchangeType = exchangeType;
    }

    protected AbstractAmqpReceiver(@NotNull String uri,
                                   @NotNull String topic,
                                   @NotNull Collection<String> bindingKeys,
                                   @NotNull ExchangeType exchangeType,
                                   boolean isDurable) throws URISyntaxException {

        this(new URI(uri), topic, bindingKeys, exchangeType, isDurable);
    }

    @Override
    public void configure() throws Exception {
        @NotNull ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(uri);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.basicQos(10);

        channel.exchangeDeclare(topicName, exchangeType.name(), isDurable);
        String queueName = channel.queueDeclare().getQueue();

        for (String bindingKey : bindingKeys) {
            channel.queueBind(queueName, topicName, bindingKey);
        }

        consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, false, consumer);
    }

    @NotNull
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
