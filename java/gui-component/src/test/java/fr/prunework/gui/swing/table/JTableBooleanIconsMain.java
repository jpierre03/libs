package fr.prunework.gui.swing.table;

import fr.prunetwork.gui.swing.table.JTableBooleanIcons;

import java.awt.*;

/**
 * @author Jean-Pierre PRUNARET
 * @since 03/01/15
 */
public class JTableBooleanIconsMain {

    private JTableBooleanIconsMain() {
    }


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    JTableBooleanIcons window = new JTableBooleanIcons();
                    window.getFrame().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
