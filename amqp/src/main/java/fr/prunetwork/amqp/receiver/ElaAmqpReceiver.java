package fr.prunetwork.amqp.receiver;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import fr.prunetwork.amqp.AmqpReceiver;
import fr.prunetwork.amqp.message.AmqpReceivedMessageImpl;
import fr.prunetwork.amqp.message.ElaLocalizedMessage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Jean-Pierre PRUNARET
 * @since 14/05/2014
 */
public final class ElaAmqpReceiver implements AmqpReceiver<ElaLocalizedMessage> {

    private final URI uri;
    private final String topicName;
    private final Collection<String> bindingKeys;
    private QueueingConsumer consumer;

    public ElaAmqpReceiver(URI uri, String topic, Collection<String> bindingKeys) {
        this.uri = uri;
        this.topicName = topic;
        this.bindingKeys = new ArrayList<>(bindingKeys);
    }

    public ElaAmqpReceiver(String uri, String topic, Collection<String> bindingKeys) throws URISyntaxException {
        this(new URI(uri), topic, bindingKeys);
    }

    @Override
    public void configure() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(uri);
        factory.setConnectionTimeout(1 * 1000);
        factory.setAutomaticRecoveryEnabled(true);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.basicQos(10);

        channel.exchangeDeclare(topicName, "topic");
        String queueName = channel.queueDeclare().getQueue();

        for (String bindingKey : bindingKeys) {
            channel.queueBind(queueName, topicName, bindingKey);
        }

        consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, false, consumer);
    }

    @Override
    public ElaLocalizedMessage consume() throws Exception {
        final QueueingConsumer.Delivery delivery = consumer.nextDelivery();
        final String message = new String(delivery.getBody());
        final String routingKey = delivery.getEnvelope().getRoutingKey();

        final ElaLocalizedMessage localizedMessage = new ElaLocalizedMessage(new AmqpReceivedMessageImpl(routingKey, message));


        // localizedMessage.displayReceived();

        consumer.getChannel().basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        return localizedMessage;
    }

    @Override
    public void close() throws IOException {
        consumer.getChannel().close();
    }
}
