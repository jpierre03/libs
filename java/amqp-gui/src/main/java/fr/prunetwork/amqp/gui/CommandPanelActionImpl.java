package fr.prunetwork.amqp.gui;

import fr.prunetwork.amqp.producer.AmqpProducer;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
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

    private void publish(@NotNull final String message) {
        try {
            amqpProducer.publish(message);
            //Thread.sleep(1);
        } catch (Exception ex) {
            notifyUser(ex);
        }
    }

    @Override
    public void sendHelloWorld() {
        publish("Hello World !");
    }

    @Override
    public void sendAddRunningTimeMinute(int duration) {
        publish("(laveuse) + " + duration + " min");
    }

    @Override
    public void sendRandomTemperature() {
        publish(random.nextInt(500) + "");
    }

    private static void notifyUser(@NotNull final Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
    }
}
