package fr.prunetwork.graphviz;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

final class ShowImage extends Panel {

    private final BufferedImage image;

    private ShowImage(@NotNull String path) throws IOException {
        try {
            @NotNull URL input = new URL(path);
            image = ImageIO.read(input);
        } catch (IOException ie) {
            System.out.println("Error:" + ie.getMessage());
            throw new IOException(ie);
        }
    }

    @Override
    public void paint(@NotNull Graphics g) {
        g.drawImage(image, 0, 0, null);
    }

    public static void main(@NotNull String args[]) throws Exception {
        @NotNull JFrame frame = new JFrame("Display image");
        Panel panel;
        if (args.length == 0) {
            panel = new ShowImage("http://www.roseindia.net/java/example/java/swing/rajeshxml2.gif");
        } else {
            panel = new ShowImage(args[0]);
        }

        frame.getContentPane().add(panel);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }
}
