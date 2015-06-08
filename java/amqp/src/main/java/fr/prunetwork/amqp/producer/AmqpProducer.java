package fr.prunetwork.amqp.producer;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import fr.prunetwork.amqp.AmqpConfiguration;
import fr.prunetwork.amqp.ExchangeType;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * This class send message to an AMQP broker.
 * <p/>
 * This is an experimental feature.
 * Not ready for production work.
 * This feature should be used as a monitoring toolkit.
 *
 * @author Jean-Pierre PRUNARET
 * @since 2014-03-10
 */
public final class AmqpProducer {

    @NotNull
    private final Channel channel;
    @NotNull
    private final Connection connection;
    @NotNull
    private final String uri;
    @NotNull
    private final String exchange;
    @NotNull
    private final String routingKey;

    /**
     * This constructor allow user to define usual AMQP settings.
     * <p/>
     * If an error occur an exception is trow and the object must be deleted (invalid).
     *
     * @throws Exception If something fail, an exception is thrown.
     */
    public AmqpProducer(@NotNull final String uri,
                        @NotNull final String exchange,
                        @NotNull final String routingKey,
                        @NotNull final ExchangeType exchangeType,
                        final boolean isDurable) throws Exception {

        this.uri = uri;
        this.exchange = exchange;
        this.routingKey = routingKey;

        @NotNull final ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(uri);
        factory.setAutomaticRecoveryEnabled(true);
        connection = factory.newConnection();

        channel = connection.createChannel();

        channel.exchangeDeclare(exchange, exchangeType.name(), isDurable);

        if (exchange.isEmpty()) {
            channel.queueDeclare(routingKey, false, true, true, null);
        } else {
        }
    }

    public AmqpProducer(@NotNull final  AmqpConfiguration configuration) throws Exception {
        this(configuration.getUri(), configuration.getExchange(), "", configuration.getExchangeType(), configuration.isDurable());
    }

    /**
     * If communication with server is still ok, a string parameter is send.
     *
     * @param message A message to be send to AMQP broker.
     * @throws IOException
     */
    public void publish(@NotNull final String message, @NotNull final String routingKey) throws IOException {
        assert channel.isOpen();

        channel.basicPublish(exchange, routingKey, null, message.getBytes());
        //System.out.println(message);
    }

    public void publish(@NotNull String message) throws IOException {
        publish(message, routingKey);
    }

    public void close() throws IOException {
        channel.close();
        connection.close();
    }
}
