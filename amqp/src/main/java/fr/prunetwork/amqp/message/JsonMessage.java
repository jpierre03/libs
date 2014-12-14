package fr.prunetwork.amqp.message;

import fr.prunetwork.amqp.AmqpReceivedMessage;
import org.json.JSONObject;

/**
 * @author Jean-Pierre PRUNARET
 *         Created on 08/09/14.
 */
public class JsonMessage
        extends AmqpReceivedMessageImpl implements AmqpReceivedMessage {

    private final JSONObject jsonObject;

    public JsonMessage(AmqpReceivedMessage message) throws Exception {
        super(message.getRoutingKey(), message.getBody());
        jsonObject = new JSONObject(message.getBody());
    }

    @Override
    public String toString() {
        return String.format("%s -> %s", getRoutingKey(), getBody());
    }

    public JSONObject getJson() {
        return jsonObject;
    }
}
