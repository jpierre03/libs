package fr.prunetwork.demo.image.gif;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * @author Jean-Pierre PRUNARET
 * @since 2011-04-13
 */
public class ImagePanel extends JPanel {

    @NotNull
    private final ImageIcon image;

    public ImagePanel(String file) {
        image = new ImageIcon(file);
        this.setSize(image.getIconWidth(), image.getIconHeight());
        this.setMinimumSize(getSize());
    }

    protected void paintComponent(@NotNull Graphics g) {
        /** clear previous content */
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        boolean drawOnce = false;
        boolean drawInLoop = !drawOnce;

        @Nullable final ImageObserver observer;

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
