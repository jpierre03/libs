package fr.prunetwork.gui;

import fr.prunetwork.amqp.ExchangeType;
import fr.prunetwork.amqp.producer.AmqpProducer;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

import static fr.prunetwork.amqp.AmqpDefaultProperties.*;

/**
 * @author Jean-Pierre PRUNARET
 * @since 01/07/2014
 */
public class CommandPanelTest {

    public static void main(String... args) {

        @NotNull JFrame frame = new JFrame("Un panneau de commande");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        try {
            frame.add(
                    new CommandPanel(
                            new CommandPanelActionner(
                                    new AmqpProducer(
                                            URI,
                                            EXCHANGE,
                                            ROUTING_KEY,
                                            ExchangeType.topic,
                                            false
                                    )
                            )
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        frame.pack();
        frame.setVisible(true);
    }
}
