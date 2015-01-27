package fr.prunetwork.gui;

import fr.prunetwork.amqp.producer.AmqpProducer;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * @author Jean-Pierre PRUNARET
 * @since 01/07/2014
 */
public class CommandPanelActionner {

    private final Random random = new Random();
    private AmqpProducer amqpProducer;

    public CommandPanelActionner(@NotNull AmqpProducer amqpProducer) {
        this.amqpProducer = amqpProducer;

        checkIntegrity();
    }

    private void setAmqpProducer(@NotNull AmqpProducer producer) {
        this.amqpProducer = producer;
        checkIntegrity();
    }

    private void publish(@NotNull String message) throws Exception {
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
        publish(random.nextInt(500) + "");
    }
}
