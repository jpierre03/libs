package fr.prunetwork;

import fr.prunetwork.amqp.ExchangeType;
import fr.prunetwork.amqp.message.JsonMessage;
import fr.prunetwork.amqp.receiver.JsonAmqpReceiver;
import fr.prunetwork.mail.Mail;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
                final JSONObject json = message.getJson();

                final String subject = json.getString("subject");
                final String body = json.getString("body");
                final List<String> destination = new ArrayList<>();

                final JSONArray destinations = json.getJSONArray("destination");
                for (int i = 0; i < destinations.length(); i++) {
                    final String d = destinations.getString(i);
                    destination.add(d);
                }

                final Mail mail = new Mail("toto@example.com", destination, subject, body);

                message.displayReceived();

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(100);
            } catch (Exception e) {

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
