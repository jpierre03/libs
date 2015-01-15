package fr.prunetwork.ping;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jean-Pierre PRUNARET
 * @since 19/04/2014
 */
public class StatusPanel extends JPanel
        implements StatusHook, WithHostname, WithLabel, LongTaskListener {

    private final JPanel center = new JPanel();
    private boolean status = false;
    private final JLabel hostname = new JLabel();
    private final JLabel label = new JLabel();
    private boolean isWorking = true;

    public StatusPanel() {
        setLayout(new BorderLayout());

        final JPanel north = new JPanel();
        north.setLayout(new GridLayout(1, 2));
        north.add(this.hostname);
        north.add(label);

        add(north, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }

    @Override
    public void setStatus(boolean status) {
        this.status = status;

        updateColor();
    }

    private void updateColor() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                if (isWorking) {
                    center.setBackground(Color.CYAN);
                }

                if (!isWorking && status) {
                    center.setBackground(Color.GREEN);
                }
                if (!isWorking && !status) {
                    center.setBackground(Color.RED);
                }

                repaint();
            }
        });
    }

    @Override
    public String getHostname() {
        return hostname.getText();
    }

    @Override
    public void setHostname(String hostname) {
        this.hostname.setText(hostname);
    }

    @Override
    public void setLabel(String label) {
        this.label.setText(label);
    }

    @Override
    public void setWorking(boolean isWorking) {
        this.isWorking = isWorking;

        updateColor();
    }
}
