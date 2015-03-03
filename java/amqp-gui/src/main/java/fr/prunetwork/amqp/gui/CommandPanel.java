package fr.prunetwork.amqp.gui;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jean-Pierre PRUNARET
 * @since 01/07/2014
 */
public class CommandPanel extends JPanel {

    @NotNull
    private final CommandPanelActionner actionner;

    public CommandPanel(@NotNull final CommandPanelActionner actionner) {
        this.actionner = actionner;

        build();
    }

    private void build() {
        setLayout(new GridLayout(4, 2));

        int i = 0;
        {
            @NotNull JButton button = new JButton(" Hello World !");
            button.addActionListener(e -> {
                try {
                    actionner.sendHelloWorld();
                } catch (Exception ex) {
                    notifyUser(ex);
                }

            });
            i++;
            add(button);
        }
        {
            @NotNull JButton button = new JButton(" +1h de fonctionnement");
            button.addActionListener(e -> {
                try {
                    actionner.sendAddRunningTimeMinute(60);
                } catch (Exception ex) {
                    notifyUser(ex);
                }
            });
            i++;
            add(button);
        }
        {
            final int MAX = 100;
            @NotNull JButton button = new JButton(MAX + " Messages en boucle");
            button.addActionListener(e -> {
                for (int count = 0; count < MAX; count++) {
                    try {
                        actionner.sendHelloWorld();
                        Thread.sleep(1250);
                    } catch (Exception ex) {
                        notifyUser(ex);
                    }
                }
            });
            i++;
            add(button);
        }
        {
            final int MAX = 100;
            @NotNull JButton button = new JButton("Envoi de " + MAX + " valeurs de temperature");
            button.addActionListener(e -> {
                for (int count = 0; count < MAX; count++) {
                    try {
                        actionner.sendRandomTemperature();
                        //Thread.sleep(1);
                    } catch (Exception ex) {
                        notifyUser(ex);
                    }
                }
            });
            i++;
            add(button);
        }
        {
            @NotNull JButton button = new JButton(i + "");
            button.addActionListener(e -> {
                // do nothing
            });
            i++;
            add(button);
        }

        setMinimumSize(new Dimension(200, 200));
    }

    private void notifyUser(@NotNull Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
    }
}
