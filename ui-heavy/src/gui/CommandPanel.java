package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Jean-Pierre PRUNARET
 * @since 01/07/2014
 */
public class CommandPanel extends JPanel {

    private final CommandPanelActionner actionner;

    public CommandPanel(CommandPanelActionner actionner) {
        if (actionner == null) {
            throw new IllegalArgumentException();
        }
        this.actionner = actionner;

        build();
    }

    private void build() {
        setLayout(new GridLayout(2, 2));

        int i = 0;
        {
            JButton button = new JButton(" Hello World !");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        actionner.sendHelloWorld();
                    } catch (Exception e1) {
                        notifyUser(e1);
                    }

                }
            });
            i++;
            add(button);
        }
        {
            JButton button = new JButton(" +1h de fonctionnement");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        actionner.sendAddRunningTimeMinute(60);
                    } catch (Exception e1) {
                        notifyUser(e1);
                    }
                }
            });
            i++;
            add(button);
        }
        {
            JButton button = new JButton("100 Messages en boucle");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (int count = 0; count < 1000*1000; count++) {
                        try {
                            actionner.sendHelloWorld();
                            Thread.sleep(7);
                        } catch (Exception e1) {
                            notifyUser(e1);
                        }
                    }
                }
            });
            i++;
            add(button);
        }
        {
            JButton button = new JButton(i + "");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // do nothing
                }
            });
            i++;
            add(button);
        }

        setMinimumSize(new Dimension(200, 200));
    }

    private void notifyUser(Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
    }
}
