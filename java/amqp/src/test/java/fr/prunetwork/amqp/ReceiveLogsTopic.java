package fr.prunetwork.amqp;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

import static fr.prunetwork.amqp.AmqpDefaultProperties.EXCHANGE;
import static fr.prunetwork.amqp.AmqpDefaultProperties.URI;

/**
 * @author Jean-Pierre PRUNARET
 * @since 14/05/2014
 */
public class ReceiveLogsTopic {

    public static void main(@NotNull String[] argv)
            throws Exception {

        @NotNull ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(URI);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE, ExchangeType.topic.toString());
        String queueName = channel.queueDeclare().getQueue();

        @NotNull ArrayList<String> bindingKeys = new ArrayList<>();

        if (argv.length < 1) {
            System.err.println("Usage: ReceiveLogsTopic [binding_key]...");

            //System.exit(1);
        } else {
            bindingKeys.addAll(Arrays.asList(argv));
        }

        bindingKeys.add("#");

        for (String bindingKey : bindingKeys) {
            channel.queueBind(queueName, EXCHANGE, bindingKey);
        }

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        @NotNull QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            @NotNull String message = new String(delivery.getBody());
            String routingKey = delivery.getEnvelope().getRoutingKey();

            System.out.printf(" [x] Received '%s':'%s'%n", routingKey, message);
        }
    }
}
