package fr.prunetwork.amqp;

import java.io.Serializable;
import java.util.Date;

/**
 * A generic message exchanged with AMQP.
 *
 * @author Jean-Pierre PRUNARET
 * @since 28/08/2014
 */
public interface AmqpReceivedMessage extends Serializable {

    /**
     * @return the message content
     */
    String getBody();

    /**
     * @return the key used to route message.
     */
    String getRoutingKey();

    public void displayReceived();

    /**
     * @return a date object created at reception time.
     */
    Date getReceivedDate();
}
