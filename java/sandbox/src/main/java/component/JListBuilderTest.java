package component;

import javax.swing.*;
import java.awt.*;

/**
 * A test class to launch a JListBuilder
 *
 * @author Jean-Pierre PRUNARET
 */
final class JListBuilderTest {

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
                    /** Turn off metal's use of bold fonts */
                    UIManager.put("swing.boldMetal", Boolean.FALSE);
                } catch (Exception ignored) {
                }

                JListBuilder<String> lb = new JListBuilder<>();
                lb.add("Tralala");
                lb.add("Trololo");
                lb.add("Hobbit jouflu");

                for (int i = 0; i < 5; i++) {
                    lb.add("----" + i);
                }

                JFrame frame = new JFrame();
                frame.setLayout(new BorderLayout());
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setContentPane(lb);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
