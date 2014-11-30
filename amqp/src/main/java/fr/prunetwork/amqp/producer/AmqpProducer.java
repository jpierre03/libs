package fr.prunetwork.amqp.producer;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

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

    //private static final String URI = "amqp://jpierre03:toto@bc.antalios.com";
    //private static final String URI = "amqp://jpierre03:toto@localhost";
    private static final String URI = "amqp://jpierre03:toto@172.16.201.201";
    //private static final String EXCHANGE = "anta.bc";
    private static final String EXCHANGE = "dev.tmp";
    private static final String ROUTING_KEY = "";
    private final Channel channel;
    private final Connection connection;
    private final String uri;
    private final String exchange;
    private final String routingKey;

    /**
     * Default constructor
     *
     * @throws Exception If something fail, an exception is thrown.
     */
    public AmqpProducer() throws Exception {
        this(URI, EXCHANGE, ROUTING_KEY);
    }


    /**
     * This constructor allow user to define usual AMQP settings.
     * <p/>
     * If an error occur an exception is trow and the object must be deleted (invalid).
     *
     * @throws Exception If something fail, an exception is thrown.
     */
    public AmqpProducer(String uri, String exchange, String routingKey) throws Exception {
        this.uri = uri;
        this.exchange = exchange;
        this.routingKey = routingKey;

        assert this.uri != null : "must be defined";
        assert this.exchange != null : "must be defined";
        assert this.routingKey != null : "must be defined";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(uri);
        connection = factory.newConnection();

        channel = connection.createChannel();

        //channel.exchangeDeclare(exchange, "fanout");
        channel.exchangeDeclare(exchange, "topic");

        if (exchange.equals("")) {
            channel.queueDeclare(routingKey, false, true, false, null);
        } else {
        }
    }

    /**
     * If communication with server is still ok, a string parameter is send.
     *
     * @param message A message to be send to AMQP broker.
     * @throws java.io.IOException
     */
    public void publish(final String message, final String routingKey) throws IOException {
        if (channel != null) {
            assert channel.isOpen();

            channel.basicPublish(exchange, routingKey, null, message.getBytes());
            //System.out.println(message);
        } else {
            throw new IOException("channel not defined");
        }
    }

    public void publish(String message) throws IOException {
        publish(message, routingKey);
    }

    public void close() throws IOException {
        channel.close();
        connection.close();
    }
}
