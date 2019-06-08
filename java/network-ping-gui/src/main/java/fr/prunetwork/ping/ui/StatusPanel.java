package fr.prunetwork.ping.ui;

import fr.prunetwork.ping.LongTaskListener;
import fr.prunetwork.ping.StatusHook;
import fr.prunetwork.ping.WithHostname;
import fr.prunetwork.ping.WithLabel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jean-Pierre PRUNARET
 * @since 19/04/2014
 */
public class StatusPanel extends JPanel
        implements StatusHook, WithHostname, WithLabel, LongTaskListener {

    @NotNull
    private final JPanel center = new JPanel();
    private boolean status = false;
    @NotNull
    private final JLabel hostname = new JLabel();
    @NotNull
    private final JLabel label = new JLabel();
    private boolean isWorking = true;

    public StatusPanel() {
        setLayout(new BorderLayout());

        @NotNull final JPanel north = new JPanel();
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
        javax.swing.SwingUtilities.invokeLater(() -> {
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
        });
    }

    @Override
    @NotNull
    public String getHostname() {
        return hostname.getText();
    }

    @Override
    @NotNull
    public void setHostname(String hostname) {
        this.hostname.setText(hostname);
    }

    @Override
    @NotNull
    public void setLabel(String label) {
        this.label.setText(label);
    }

    @Override
    @NotNull
    public void setWorking(boolean isWorking) {
        this.isWorking = isWorking;

        updateColor();
    }
}
