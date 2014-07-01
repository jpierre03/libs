package amqp.consumer;

/**
* @author Jean-Pierre PRUNARET
* @since 01/07/2014
*/
interface AmqpReceivedMessage {

    String getBody();

    String getRoutingKey();
}
