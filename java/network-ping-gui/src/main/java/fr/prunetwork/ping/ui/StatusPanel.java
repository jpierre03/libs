package fr.prunetwork.ping.ui;

import fr.prunetwork.ping.PingTaskListener;
import fr.prunetwork.ping.WithHostname;
import fr.prunetwork.ping.WithLabel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * @author Jean-Pierre PRUNARET
 * @since 19/04/2014
 */
public class StatusPanel extends JPanel
        implements WithHostname, WithLabel, PingTaskListener {

    private final JLabel center;
    private final JLabel hostname = new JLabel();
    private final JLabel label = new JLabel();
    private final Border workingBorder;

    public StatusPanel() {
        setLayout(new BorderLayout());
        center = new JLabel();
        center.setOpaque(true);
        center.setHorizontalAlignment(SwingConstants.CENTER);
        center.setFont(getFont().deriveFont(Font.BOLD));

        workingBorder = BorderFactory.createLineBorder(Color.BLACK, 5);

        @NotNull final JPanel north = new JPanel();
        north.setLayout(new GridLayout(1, 2));
        north.add(this.hostname);
        north.add(label);

        add(north, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }

    private static void enforceEDT() {
        if (!SwingUtilities.isEventDispatchThread()) {
            throw new IllegalMonitorStateException("GUI components must be invoked from EventDispatchThread");
        }
    }

    @Override
    @NotNull
    public String getHostname() {
        return hostname.getText();
    }

    @Override
    public void setHostname(@NotNull final String hostname) {
        this.hostname.setText(hostname);
    }

    @Override
    public void setLabel(@NotNull final String label) {
        this.label.setText(label);
    }

    @Override
    public void interrogationStarted() {
        enforceEDT();

        center.setBorder(workingBorder);
        center.setText("?");
    }

    @Override
    public void interrogationFinished(boolean isReachable) {
        enforceEDT();

        center.setBorder(null);
        center.setText("");

        if (isReachable) {
            center.setBackground(Color.GREEN);
        } else {
            center.setBackground(Color.RED);
        }
    }
}
