package fr.prunetwork.logigramme;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Composant portant l'organigramme
 */
public class ComposantLogigramme extends JComponent {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * Bord du composant
     */
    private Border tour;
    /**
     * Label qui porte le dessin de l'organigramme
     */
    private JLabel label;
    /**
     * L'organigramme
     */
    private Logigramme organigramme;

    /**
     * Construit le composant avec l'organigramme donné
     *
     * @param organigramme
     */
    public ComposantLogigramme(Logigramme organigramme) {
        super();
        /**Initialiasation et valeurs par défaut*/
        setFont(new Font("Times New Roman", Font.PLAIN, 14));
        setForeground(Color.black);
        this.organigramme = organigramme;
        /**L'organigramme est dessiner dans ce composant*/
        organigramme.setComposantOrganigramme(this);
        /**Calcul de la taille du composant*/
        setSize(organigramme.getIconWidth(), organigramme.getIconHeight());
    }

    /**
     * Change le tour
     */
    @Override
    public void setBorder(Border border) {
        tour = border;
    }

    /**
     * Dessine le composant
     */
    @Override
    protected void paintComponent(Graphics g) {
        /** On initialise à pas de décalage. Comme si il il n'y avait pas de tour */
        Insets in = new Insets(0, 0, 0, 0);

        /** S'il y a un tour, on récupére le décalage qu'il implique*/
        if (tour != null) {
            in = tour.getBorderInsets(this);
        }

        final int l;
        final int h;
        {
            /**---Fond du composant---*/
        /* Couleur du fond */
            g.setColor(getBackground());

            /** Largeur du composant */
            l = organigramme.getIconWidth() + in.left + in.right;

            /** Hauteur du composant */
            h = organigramme.getIconHeight() + in.bottom + in.top;
            /** Peint le fond d'une couleur unie */
            g.fillRect(0, 0, l, h);

            /**---Fin du fond---*/
        }
        /**Dessine l'organigramme*/
        g.setColor(getForeground());
        organigramme.paintIcon(this, g, in.left, in.top);

        /**Dessine le tour*/
        tour.paintBorder(this, g, 0, 0, l, h);
    }

    /**
     * Change l'organigramme
     *
     * @param organigramme
     */
    public void setOrganigramme(Logigramme organigramme) {
        this.organigramme = organigramme;
        organigramme.setComposantOrganigramme(this);
    }

    /**
     * Largeur du composant
     */
    @Override
    public int getWidth() {
        Insets in = new Insets(0, 0, 0, 0);
        if (tour != null) {
            in = tour.getBorderInsets(this);
        }
        return organigramme.getIconWidth() + in.left + in.right;
    }

    /**
     * Hauteur du composant
     */
    @Override
    public int getHeight() {
        Insets in = new Insets(0, 0, 0, 0);
        if (tour != null) {
            in = tour.getBorderInsets(this);
        }
        return organigramme.getIconHeight() + in.bottom + in.top;
    }

    /**
     * Dimension du composant. Sert, si le composant est intégré à une Frame ou un Dialog
     */
    @Override
    public Dimension getSize() {
        Insets in = new Insets(0, 0, 0, 0);
        if (tour != null) {
            in = tour.getBorderInsets(this);
        }
        return new Dimension(
                organigramme.getIconWidth() + in.left + in.right,
                organigramme.getIconHeight() + in.bottom + in.top);
    }

    /**
     * Dimension préférée du composant.
     * Sert, si le composant est intégré à une Frame ou un Dialog
     */
    @Override
    public Dimension getPreferredSize() {
        return getSize();
    }

    /**
     * Dimension maximum du composant.
     * Sert, si le composant est intégré à une Frame ou un Dialog
     */
    @Override
    public Dimension getMaximumSize() {
        return getSize();
    }

    /**
     * Dimension minimum du composant.
     * Sert, si le composant est intégré à une Frame ou un Dialog
     */
    @Override
    public Dimension getMinimumSize() {
        return getSize();
    }
}
