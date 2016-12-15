package fr.prunetwork.amqp.gui;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jean-Pierre PRUNARET
 * @since 01/07/2014
 */
class CommandPanel extends JPanel {

    @NotNull
    private final CommandPanelAction action;
    @NotNull
    private final AtomicInteger counter = new AtomicInteger(0);

    CommandPanel(@NotNull final CommandPanelAction action) {
        this.action = action;

        build();
    }

    private void build() {
        setLayout(new GridLayout(4, 2));

        Map<String, ActionListener> buttons = new TreeMap<>();

        buttons.put(" Hello World !", e -> {
            try {
                action.sendHelloWorld();
            } catch (Exception ex) {
                notifyUser(ex);
            }

        });

        buttons.put(" +1h de fonctionnement", e -> {
            try {
                action.sendAddRunningTimeMinute(60);
            } catch (Exception ex) {
                notifyUser(ex);
            }
        });


        final int MAX = 100*1000*1000;
        buttons.put(MAX + " Messages en boucle", e -> {
            for (int count = 0; count < MAX; count++) {
                try {
                    action.sendHelloWorld();
                    //Thread.sleep(1250);
                } catch (Exception ex) {
                    notifyUser(ex);
                }
            }
        });

        buttons.put("Envoi de " + MAX + " valeurs de temperature", e -> {
            for (int count = 0; count < MAX; count++) {
                try {
                    action.sendRandomTemperature();
                    //Thread.sleep(1);
                } catch (Exception ex) {
                    notifyUser(ex);
                }
            }
        });

        buttons.put(counter.toString(), e -> {                /* do nothing */ });

        /**
         * Build buttons and add
         */
        buttons.forEach((k, v) -> buildAndAddButton(this, counter, k, v));

        setMinimumSize(new Dimension(200, 200));
    }

    private static JButton buildAndAddButton(JPanel panel, AtomicInteger counter, String description, ActionListener l) {
        counter.incrementAndGet();
        @NotNull JButton button = new JButton(description);
        button.addActionListener(l);
        panel.add(button);
        return button;
    }

    private void notifyUser(@NotNull final Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
    }
}
