package fr.prunetwork.amqp;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Jean-Pierre PRUNARET
 * @since 28/08/2014
 */
public interface AmqpReceivedMessage extends Serializable {

    String getBody();

    String getRoutingKey();

    public void displayReceived();

    Date getReceivedDate();
}
