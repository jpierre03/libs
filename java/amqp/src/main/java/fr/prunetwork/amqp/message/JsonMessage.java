package fr.prunetwork.amqp.message;

import fr.prunetwork.amqp.AmqpReceivedMessage;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

/**
 * @author Jean-Pierre PRUNARET
 *         Created on 08/09/14.
 */
public class JsonMessage
        extends AmqpReceivedMessageImpl
        implements AmqpReceivedMessage {

    @NotNull
    private final JSONObject jsonObject;

    public JsonMessage(@NotNull final AmqpReceivedMessage message) throws Exception {
        this(message.getRoutingKey(), message.getBody());
    }

    public JsonMessage(@NotNull final String routingKey, @NotNull final String body) throws Exception {
        super(routingKey, body);
        jsonObject = new JSONObject(body);
    }

    @NotNull
    public JSONObject getJson() {
        return jsonObject;
    }
}
