package amqp.consumer;

import java.util.Date;

/**
 * @author Jean-Pierre PRUNARET
 * @since 01/07/2014
 */
class ElaLocalizedMessage implements AmqpReceivedMessage {

    private final AmqpReceivedMessage message;
    private final String antennaName;
    private final String area;
    private Date receptionDate;

    ElaLocalizedMessage(AmqpReceivedMessage message) {
        this.message = message;

        final String[] split = message.getRoutingKey().split("\\.");

        if (split.length >= 1) {
            area = split[0].toLowerCase().trim();
        } else {
            area = "";
        }
        if (split.length >= 2) {
            antennaName = split[1].toLowerCase().trim();
        } else {
            antennaName = "";
        }
        receptionDate = message.getReceptionDate();
    }

    public String getAntennaName() {
        return antennaName;
    }

    public String getArea() {
        return area;
    }

    public boolean isConsistent() {
        boolean result = true;

        if (message == null) {
            result = false;
        }

        if (area.isEmpty()) {
            result = false;
        }

        if (antennaName == null) {
            result = false;
        }

        return result;
    }

    @Override
    public String getBody() {
        return message.getBody();
    }

    @Override
    public String getRoutingKey() {
        return message.getRoutingKey();
    }

    @Override
    public Date getReceptionDate() {
        return receptionDate;
    }

    public void displayReceived() {
        System.out.printf(" [x] Received '%s':'%s' for area: '%s'%n", getRoutingKey(), getBody(), getArea());
    }
}
