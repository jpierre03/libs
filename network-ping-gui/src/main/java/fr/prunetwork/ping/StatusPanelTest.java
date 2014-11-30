package fr.prunetwork.ping;

import javax.swing.*;

/**
 * @author Jean-Pierre PRUNARET
 * @since 19/04/2014
 */
public final class StatusPanelTest {

    private StatusPanelTest() {
    }

    public static void main(String... args) {

        final StatusPanel statusPanel = new StatusPanel();
        statusPanel.setHostname("<hostname>");
        statusPanel.setLabel("Équipement en test");

        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame frame = new JFrame("Status");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                frame.add(statusPanel);
                frame.pack();
                frame.setSize(400, 200);
                frame.setVisible(true);
            }
        });

        boolean status = true;
        for (int i = 0; i < 30; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Do nothing
            }

            status = !status;
            statusPanel.setStatus(status);
            statusPanel.setLabel(i + "");
        }
    }
}
