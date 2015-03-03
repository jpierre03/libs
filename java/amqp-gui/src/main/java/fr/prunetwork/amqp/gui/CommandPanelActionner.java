package fr.prunetwork.amqp.gui;

import fr.prunetwork.amqp.producer.AmqpProducer;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * @author Jean-Pierre PRUNARET
 * @since 01/07/2014
 */
public class CommandPanelActionner {

    @NotNull
    private final Random random = new Random();
    @NotNull
    private AmqpProducer amqpProducer;

    public CommandPanelActionner(@NotNull final AmqpProducer amqpProducer) {
        this.amqpProducer = amqpProducer;

        checkIntegrity();
    }

    private void setAmqpProducer(@NotNull final AmqpProducer producer) {
        this.amqpProducer = producer;
        checkIntegrity();
    }

    private void publish(@NotNull final String message) throws Exception {
        checkIntegrity();
        amqpProducer.publish(message);
    }

    private void checkIntegrity() {
        // do nothing
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
