package fr.prunetwork.amqp.consumer;

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

        @NotNull SimpleMessageConsumer consumer = new SimpleMessageConsumer();
        @NotNull AmqpReceiver receiver = new AmqpReceiver(URI, EXCHANGE, ROUTING_KEYS, consumer);
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
