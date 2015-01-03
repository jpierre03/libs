import javax.swing.*;
import java.awt.*;

/**
 * @author Jean-Pierre PRUNARET
 *         Date: 13/04/11
 *         Time: 22:11
 */
public class ImagePanel extends JPanel {

    private ImageIcon image;

    public ImagePanel(String file) {
        image = new ImageIcon(file);
        this.setSize(image.getIconWidth(), image.getIconHeight());
        this.setMinimumSize(getSize());
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

    protected void paintComponent(Graphics g) {
        // efface le contenu précédent :
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        // dessine le gif :
        // en boucle
        g.drawImage(image.getImage(), 1, 1, image.getIconWidth(), image.getIconHeight(), this);
        //une seule fois
        //g.drawImage(image.getImage(), 1, 1, image.getIconWidth(), image.getIconWidth(), null);
    }
}
