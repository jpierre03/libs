package fr.prunetwork.amqp.gui;

import fr.prunetwork.amqp.producer.AmqpProducer;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * @author Jean-Pierre PRUNARET
 * @since 01/07/2014
 */
public class CommandPanelActionImpl implements CommandPanelAction {

    @NotNull
    private final Random random = new Random();
    @NotNull
    private AmqpProducer amqpProducer;

    public CommandPanelActionImpl(@NotNull final AmqpProducer amqpProducer) {
        this.amqpProducer = amqpProducer;
    }

    private void publish(@NotNull final String message) throws Exception {
        amqpProducer.publish(message);
    }

    @Override
    public void sendHelloWorld() throws Exception {
        publish("Hello World !");
    }

    @Override
    public void sendAddRunningTimeMinute(int duration) throws Exception {
        publish("(laveuse) + " + duration + " min");
    }

    @Override
    public void sendRandomTemperature() throws Exception {
        publish(random.nextInt(500) + "");
    }
}
