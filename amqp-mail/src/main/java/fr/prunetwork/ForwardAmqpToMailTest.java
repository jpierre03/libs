package fr.prunetwork;

import fr.prunetwork.amqp.ExchangeType;
import fr.prunetwork.amqp.message.JsonMessage;
import fr.prunetwork.amqp.receiver.JsonAmqpReceiver;

import static fr.prunetwork.amqp.AmqpDefaultProperties.*;

/**
 * @author Jean-Pierre PRUNARET
 * @since 13/12/14
 */
public class ForwardAmqpToMailTest {

    public static void main(String... argv) throws Exception {

        JsonAmqpReceiver receiver = new JsonAmqpReceiver(URI, EXCHANGE, ROUTING_KEYS, ExchangeType.topic);
        receiver.configure();

        while (true) {
            try {
                final JsonMessage message = receiver.consume();


                message.displayReceived();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


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
