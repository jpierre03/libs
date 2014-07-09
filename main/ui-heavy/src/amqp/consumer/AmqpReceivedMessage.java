package amqp.consumer;

import java.util.Date;

/**
 * @author Jean-Pierre PRUNARET
 * @since 01/07/2014
 */
public interface AmqpReceivedMessage {

    String getBody();

    String getRoutingKey();

    Date getReceptionDate();
}
