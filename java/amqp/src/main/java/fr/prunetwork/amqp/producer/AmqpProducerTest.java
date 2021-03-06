package fr.prunetwork.amqp.producer;

import org.jetbrains.annotations.NotNull;

import static fr.prunetwork.amqp.AmqpDefaultProperties.AMQP_CONFIGURATION;

/**
 * @author Jean-Pierre PRUNARET
 * @since 01/07/2014
 */
public class AmqpProducerTest {

    public AmqpProducerTest() {
    }

    public static void main(String... args) {

        try {
            @NotNull final AmqpProducer producer = new AmqpProducer(AMQP_CONFIGURATION);

            for (int i = 0; i < 1000 * 1000; i++) {
                if (i % (1 * 1000) == 0) {
                    System.out.println("Sender: " + i);
                }

                @NotNull final String message = "--" + "the time is " + new java.util.Date().toString() + "--";

                producer.publish(message);
            }

        } catch (Exception e) {
            e.printStackTrace();

            System.err.println("Main thread caught exception: " + e);
            e.printStackTrace();
            System.exit(1);
        }

        System.exit(1);
    }
}
