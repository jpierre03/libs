package fr.onet.ae.gui;

import fr.onet.ae.amqp.producer.AmqpProducer;

import javax.swing.*;

/**
 * @author Jean-Pierre PRUNARET
 * @since 01/07/2014
 */
public class CommandPanelTest {

    public static void main(String... args) {
        JFrame frame = new JFrame("Un panneau de commande");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        try {
            frame.add(new CommandPanel(new CommandPanelActionner(new AmqpProducer())));
        } catch (Exception e) {
            System.exit(-1);
        }
        frame.pack();
        frame.setVisible(true);

    }
}
