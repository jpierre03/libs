package fr.prunetwork.gui;

import fr.prunetwork.amqp.ExchangeType;
import fr.prunetwork.amqp.producer.AmqpProducer;

import javax.swing.*;

import static fr.prunetwork.amqp.AmqpDefaultProperties.*;

/**
 * @author Jean-Pierre PRUNARET
 * @since 01/07/2014
 */
public class CommandPanelTest {

    public static void main(String... args) {

        JFrame frame = new JFrame("Un panneau de commande");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        try {
            frame.add(
                    new CommandPanel(
                            new CommandPanelActionner(
                                    new AmqpProducer(
                                            URI,
                                            EXCHANGE,
                                            ROUTING_KEY,
                                            ExchangeType.topic
                                    )
                            )
                    )
            );
        } catch (Exception e) {
            System.exit(-1);
        }
        frame.pack();
        frame.setVisible(true);
    }
}
