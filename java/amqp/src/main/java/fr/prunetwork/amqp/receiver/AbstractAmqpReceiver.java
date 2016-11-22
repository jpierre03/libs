package fr.prunetwork.amqp.receiver;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import fr.prunetwork.amqp.AmqpConfiguration;
import fr.prunetwork.amqp.AmqpReceivedMessage;
import fr.prunetwork.amqp.AmqpReceiver;
import fr.prunetwork.amqp.ExchangeType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Jean-Pierre PRUNARET
 * @since 14/05/2014
 */
public abstract class AbstractAmqpReceiver<T extends AmqpReceivedMessage> implements AmqpReceiver<T> {

    final boolean isDurable;
    @NotNull
    private final URI uri;
    @NotNull
    private final String exchange;
    @NotNull
    private final Collection<String> bindingKeys;
    @NotNull
    private final ExchangeType exchangeType;
    @Nullable
    private QueueingConsumer consumer;

    protected AbstractAmqpReceiver(@NotNull final URI uri,
                                @NotNull final String exchange,
                                @NotNull final Collection<String> bindingKeys,
                                @NotNull final ExchangeType exchangeType,
                                final boolean isDurable) {
        this.uri = uri;
        this.exchange = exchange;
        this.bindingKeys = new ArrayList<>(bindingKeys);
        this.exchangeType = exchangeType;
        this.isDurable = isDurable;
    }

    protected AbstractAmqpReceiver(@NotNull final String uri,
                                @NotNull final String exchange,
                                @NotNull final Collection<String> bindingKeys,
                                @NotNull final ExchangeType exchangeType,
                                final boolean isDurable) throws Exception {
        this(new URI(uri), exchange, bindingKeys, exchangeType, isDurable);
    }

    protected AbstractAmqpReceiver(@NotNull final AmqpConfiguration conf) throws Exception {
        this(conf.getUri(), conf.getExchange(), conf.getBindingKeys(), conf.getExchangeType(), conf.isDurable());
    }

    @Override
    public void configure() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(uri);
        factory.setAutomaticRecoveryEnabled(true);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.basicQos(10);

        channel.exchangeDeclare(exchange, exchangeType.name(), isDurable);
        String queueName = channel.queueDeclare().getQueue();

        for (String bindingKey : bindingKeys) {
            channel.queueBind(queueName, exchange, bindingKey);
        }

        consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, false, consumer);
    }

    @NotNull
    @Override
    public abstract T consume() throws Exception;

    @Override
    public void close() throws Exception {
        if (consumer != null) {
            consumer.getChannel().close();
        }
    }

    @Nullable
    protected QueueingConsumer getConsumer() {
        return consumer;
    }
}
