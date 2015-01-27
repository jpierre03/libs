package fr.prunetwork;

import fr.prunetwork.amqp.ExchangeType;
import fr.prunetwork.amqp.message.JsonMessage;
import fr.prunetwork.amqp.receiver.JsonAmqpReceiver;
import fr.prunetwork.mail.Mail;
import fr.prunetwork.mail.SimpleMail;
import org.jetbrains.annotations.NotNull;
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

        @NotNull JsonAmqpReceiver receiver = new JsonAmqpReceiver(URI, EXCHANGE, ROUTING_KEYS, ExchangeType.topic);
        receiver.configure();

        while (true) {
            try {
                @NotNull final JsonMessage message = receiver.consume();
                @NotNull final JSONObject json = message.getJson();

                final String subject = json.getString("subject");
                final String body = json.getString("body");
                @NotNull final List<String> destination = new ArrayList<>();

                final JSONArray destinations = json.getJSONArray("destination");
                for (int i = 0; i < destinations.length(); i++) {
                    final String d = destinations.getString(i);
                    destination.add(d);
                }

                @NotNull final Mail mail = new SimpleMail("toto@example.com", destination, subject, body);

                message.displayMessage();

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
