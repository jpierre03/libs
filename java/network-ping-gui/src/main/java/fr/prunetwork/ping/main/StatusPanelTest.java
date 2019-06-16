package fr.prunetwork.ping.main;

import fr.prunetwork.ping.ui.StatusPanel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author Jean-Pierre PRUNARET
 * @since 19/04/2014
 */
public final class StatusPanelTest {

    private StatusPanelTest() {
        // Do nothing
    }

    public static void main(String... args) {

        final StatusPanel statusPanel = new StatusPanel();
        statusPanel.setHostname("<hostname>");
        statusPanel.setLabel("Équipement en test");

        javax.swing.SwingUtilities.invokeLater(() -> {
            @NotNull JFrame frame = new JFrame("Status");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            frame.add(statusPanel);
            frame.pack();
            frame.setSize(400, 200);
            frame.setVisible(true);
        });

        boolean status = true;
        for (int i = 0; i < 30; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Do nothing
            }

            status = !status;

            final int index = i;
            final boolean isReachable = status;
            SwingUtilities.invokeLater(() -> {
                statusPanel.setLabel(index + "");
                statusPanel.interrogationFinished(isReachable);
            });
        }

        System.exit(0);
    }
}
