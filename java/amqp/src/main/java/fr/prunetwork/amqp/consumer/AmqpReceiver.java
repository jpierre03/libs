package fr.prunetwork.amqp.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import fr.prunetwork.amqp.AmqpConfiguration;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jean-Pierre PRUNARET
 * @since 14/05/2014
 */
public class AmqpReceiver {

    @NotNull
    private final MessageConsumer externalConsumer;
    private QueueingConsumer consumer;

    @NotNull
    private final AmqpConfiguration configuration;

    public AmqpReceiver(@NotNull final AmqpConfiguration configuration,
                        @NotNull final MessageConsumer consumer) {
        this.configuration = configuration;
        this.externalConsumer = consumer;
    }

    public void configure() throws Exception {
        @NotNull final ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(configuration.getUri());
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.exchangeDeclare(configuration.getExchangeName(), configuration.getExchangeType().name(), configuration.isDurable());
        String queueName = channel.queueDeclare().getQueue();

        for (final String bindingKey : configuration.getBindingKeys()) {
            channel.queueBind(queueName, configuration.getExchangeName(), bindingKey);
        }

        consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);
        channel.basicQos(5);

        externalConsumer.init(consumer);
    }
}
