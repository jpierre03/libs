package fr.prunetwork.amqp;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Jean-Pierre PRUNARET
 * @since 14/05/2014
 */
public class ReceiveLogsTopic {

    static final String AMQP_TEST_URL = "amqp://localhost";
    static final String AMQP_TEST_EXCHANGE = "test";

    public static void main(String[] argv)
            throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(AMQP_TEST_URL);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(AMQP_TEST_EXCHANGE, "topic");
        String queueName = channel.queueDeclare().getQueue();

        ArrayList<String> bindingKeys = new ArrayList<>();

        if (argv.length < 1) {
            System.err.println("Usage: ReceiveLogsTopic [binding_key]...");

            //System.exit(1);
        } else {
            bindingKeys.addAll(Arrays.asList(argv));
        }

        bindingKeys.add("#");

        for (String bindingKey : bindingKeys) {
            channel.queueBind(queueName, AMQP_TEST_EXCHANGE, bindingKey);
        }

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            String routingKey = delivery.getEnvelope().getRoutingKey();

            System.out.printf(" [x] Received '%s':'%s'%n", routingKey, message);
        }
    }
}
