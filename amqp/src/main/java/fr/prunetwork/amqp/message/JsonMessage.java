package fr.prunetwork.amqp.message;

import fr.prunetwork.amqp.AmqpReceivedMessage;
import org.json.JSONObject;

/**
 * @author Jean-Pierre PRUNARET
 *         Created on 08/09/14.
 */
public class JsonMessage implements AmqpReceivedMessage {

    final JSONObject jsonObject;
    private final String routingKey;
    private final String message;

    public JsonMessage(AmqpReceivedMessage message) throws Exception {
        this.routingKey = message.getRoutingKey();
        this.message = message.getBody();
        jsonObject = new JSONObject(message.getBody());
    }

    @Override
    public String getBody() {
        return message;
    }

    @Override
    public String getRoutingKey() {
        return routingKey;
    }

    public void displayReceived() {
        System.out.printf(" [x] Received '%s':'%s' for area: '%s' isConsistent: %s %n", getRoutingKey(), getBody());
    }
}
