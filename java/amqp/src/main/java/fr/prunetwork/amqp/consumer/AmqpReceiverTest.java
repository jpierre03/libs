package fr.prunetwork.amqp.consumer;

import fr.prunetwork.amqp.AmqpConfiguration;
import fr.prunetwork.amqp.ExchangeType;
import org.jetbrains.annotations.NotNull;

import static fr.prunetwork.amqp.AmqpDefaultProperties.*;

/**
 * @author Jean-Pierre PRUNARET
 * @since 01/07/2014
 */
class AmqpReceiverTest {

    public AmqpReceiverTest() {
    }

    public static void main(String... argv) throws Exception {

        @NotNull final SimpleMessageConsumer consumer = new SimpleMessageConsumer();
        @NotNull final AmqpConfiguration configuration = new AmqpConfiguration(URI, EXCHANGE, ROUTING_KEYS, ExchangeType.topic, false);
        @NotNull final AmqpReceiver receiver = new AmqpReceiver(configuration, consumer);
        receiver.configure();

        System.out.println("*******");
        while (consumer.isConnected() == false) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                // do nothing
            }
        }
        System.out.println("*******");
        while (true) {
            consumer.consume();
        }
    }
}
