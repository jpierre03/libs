package fr.prunetwork.demo.image.gif;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;

import static javax.swing.WindowConstants.*;

/**
 * @author Jean-Pierre PRUNARET
 * @since 03/01/15
 */
public final class ImagePanelMain {

    private ImagePanelMain() {
    }

    public static void main(String... args) {
        @NotNull final JFrame frame = new JFrame("Un gif animé");

        @NotNull final ImagePanel panel = new ImagePanel("test.gif");
        frame.add(panel);
        frame.setSize(panel.getSize());

        //---
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
//		frame.pack();
        frame.setVisible(true);
    }
}
