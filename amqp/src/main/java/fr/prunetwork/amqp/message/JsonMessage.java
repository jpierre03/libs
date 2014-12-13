package fr.prunetwork.amqp.message;

import fr.prunetwork.amqp.AmqpReceivedMessage;
import org.json.JSONObject;

import java.util.Date;

/**
 * @author Jean-Pierre PRUNARET
 *         Created on 08/09/14.
 */
public class JsonMessage implements AmqpReceivedMessage {

    final JSONObject jsonObject;
    private final String routingKey;
    private final String message;
    private final Date receptionDate;

    public JsonMessage(AmqpReceivedMessage message) throws Exception {
        this.routingKey = message.getRoutingKey();
        this.message = message.getBody();
        jsonObject = new JSONObject(message.getBody());
        this.receptionDate = new Date();
    }

    @Override
    public String getBody() {
        return message;
    }

    @Override
    public String getRoutingKey() {
        return routingKey;
    }

    @Override
    public Date getReceivedDate() {
        return receptionDate;
    }

    public void displayReceived() {
        System.out.printf(" [x] Received '%s':'%s'%n", getRoutingKey(), getBody());
    }
}
