package fr.prunetwork;

import fr.prunetwork.amqp.ExchangeType;
import fr.prunetwork.amqp.producer.AmqpProducer;

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
            final AmqpProducer producer = new AmqpProducer(URI, EXCHANGE, ROUTING_KEY, ExchangeType.topic);

            for (int i = 0; i < 10; i++) {
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
                final String message = "{\n" +
                        "    \"subject\": \"un beau sujet\",\n" +
                        "    \"destination\": [\n" +
                        "        \"john.doe@example.com\",\n" +
                        "        \"john.doe@example.42\"\n" +
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
