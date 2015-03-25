package fr.prunetwork;

import fr.prunetwork.amqp.ExchangeType;
import fr.prunetwork.amqp.producer.AmqpProducer;
import org.jetbrains.annotations.NotNull;

import static fr.prunetwork.amqp.AmqpDefaultProperties.*;

/**
 * @author Jean-Pierre PRUNARET
 * @since 13/12/14
 */
public class MailAmqpProducerTest {

    private MailAmqpProducerTest() {
    }

    public static void main(String[] args) {

        try {
            @NotNull final AmqpProducer producer = new AmqpProducer(URI, EXCHANGE, ROUTING_KEY, ExchangeType.topic, false);

            for (int i = 0; i < 1; i++) {
/*
{
    "subject": "un beau sujet",
    "destination": [
        "john.doe@example.com",
        "john.doe@example.42"
    ],
    "body": "un corps de mail très court"
}
 */
                @NotNull final String message = "{\n" +
                        "    \"subject\": \"un beau sujet\",\n" +
                        "    \"destination\": [\n" +
                        "        \"blackhole@prunetwork.fr\",\n" +
                        "        \"blackhole@prunetwork.fr\"\n" +
                        "    ],\n" +
                        "    \"body\": \"un corps de mail très court\"\n" +
                        "}";

                producer.publish(message);
                System.out.println(message);
            }

        } catch (Exception e) {
            e.printStackTrace();

            System.err.println("Main thread caught exception: " + e);
            e.printStackTrace();
            System.exit(1);
        }
    }
}
