package gui;

import amqp.producer.AmqpProducer;

import java.util.Random;

/**
 * @author Jean-Pierre PRUNARET
 * @since 01/07/2014
 */
public class CommandPanelActionner {

    private AmqpProducer amqpProducer;

    public CommandPanelActionner(AmqpProducer amqpProducer) {
        this.amqpProducer = amqpProducer;

        checkIntegrity();
    }

    private void setAmqpProducer(AmqpProducer producer) {
        this.amqpProducer = producer;
        checkIntegrity();
    }

    private void publish(String message) throws Exception {
        checkIntegrity();
        amqpProducer.publish(message);
    }

    private void checkIntegrity() {
        if (amqpProducer == null) {
            throw new IllegalStateException("amqp stream not defined");
        }
    }

    public void sendHelloWorld() throws Exception {
        publish("Hello World !");
    }

    public void sendAddRunningTimeMinute(int duration) throws Exception {
        publish("(laveuse) + " + duration + " min");
    }

    public void sendRandomTemperature() throws Exception {
        publish(new Random().nextInt(500) + "");
    }
}
