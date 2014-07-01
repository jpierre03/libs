package amqp.consumer;

/**
 * @author Jean-Pierre PRUNARET
 * @since 01/07/2014
 */
public class AmqpReceivedMessageImpl implements AmqpReceivedMessage {

    private final String routingKey;
    private final String body;

    public AmqpReceivedMessageImpl(String routingKey, String body) {
        this.routingKey = routingKey;
        this.body = body;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public String getRoutingKey() {
        return routingKey;
    }

    public void displayFullMessage() {
        StringBuffer sb = new StringBuffer();

        sb.append("->").append("\n");
        sb.append("-date:").append(new java.util.Date().toString()).append("\n");
        sb.append("-key :").append(getRoutingKey()).append("\n");
        sb.append("-body:").append(getBody()).append("\n");
        sb.append("<-").append("\n");

        System.out.print(sb.toString());
    }
}
