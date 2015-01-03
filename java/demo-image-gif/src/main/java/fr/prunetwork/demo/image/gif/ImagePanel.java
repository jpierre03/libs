package fr.prunetwork.demo.image.gif;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * @author Jean-Pierre PRUNARET
 *         Date: 13/04/11
 *         Time: 22:11
 */
public class ImagePanel extends JPanel {

    private final ImageIcon image;

    public ImagePanel(String file) {
        image = new ImageIcon(file);
        this.setSize(image.getIconWidth(), image.getIconHeight());
        this.setMinimumSize(getSize());
    }

    protected void paintComponent(Graphics g) {
        /** clear previous content */
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        boolean drawOnce = false;
        boolean drawInLoop = !drawOnce;

        final ImageObserver observer;

        if (drawInLoop) {
            observer = this;
        } else if (drawOnce) {
            observer = null;
        } else {
            observer = null;
        }

        g.drawImage(image.getImage(), 1, 1, image.getIconWidth(), image.getIconWidth(), observer);
    }
}
