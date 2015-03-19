package fr.prunetwork.mail;

import fr.prunetwork.amqp.AmqpConfiguration;
import fr.prunetwork.amqp.message.JsonMessage;
import fr.prunetwork.amqp.receiver.JsonAmqpReceiver;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Jean-Pierre PRUNARET
 * @since 2015-03-19
 */
public class AmqpToMailForwarderService {

    @NotNull
    private final JsonAmqpReceiver receiver;
    @NotNull
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private boolean isContinue;

    public AmqpToMailForwarderService(@NotNull final AmqpConfiguration amqpConfiguration) throws Exception {
        receiver = new JsonAmqpReceiver(amqpConfiguration);
        receiver.configure();
    }

    public void start() {
        isContinue = true;

        executor.execute(() -> {
            while (isContinue) {
                processMessage();
            }
        });
    }

    public void stop() {
        isContinue = false;
    }

    private void processMessage() {
        try {
            @NotNull final JsonMessage message = receiver.consume();
            @NotNull final JSONObject json = message.getJson();

            @NotNull final String subject = json.getString("subject");
            @NotNull final String body = json.getString("body");
            @NotNull final List<String> destination = new ArrayList<>();

            @NotNull final JSONArray destinations = json.getJSONArray("destination");
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
