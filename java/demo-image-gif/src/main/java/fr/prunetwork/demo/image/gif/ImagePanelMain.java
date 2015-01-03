package fr.prunetwork.demo.image.gif;

import javax.swing.*;

/**
 * @author Jean-Pierre PRUNARET
 * @since 03/01/15
 */
public final class ImagePanelMain {

    private ImagePanelMain() {
    }

    public static void main(String args[]) {
        JFrame frame = new JFrame("Un gif animé");

        ImagePanel panel = new ImagePanel("test.gif");
        frame.add(panel);
        frame.setSize(panel.getSize());

        //---
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.pack();
        frame.setVisible(true);
    }
}