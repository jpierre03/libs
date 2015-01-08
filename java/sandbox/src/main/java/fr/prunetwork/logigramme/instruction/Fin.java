package fr.prunetwork.logigramme.instruction;

import fr.prunetwork.logigramme.Texte;

import java.awt.*;

/**
 * Instruction indiquant une fin à l'organigramme
 */
public final class Fin extends AbstractInstruction {

    /**
     * Texte "Fin"
     */
    private Texte texte;

    /**
     * Construction
     */
    public Fin() {
        texte = new Texte("Fin");
    }

    /**
     * Dessine l'instruction <BR>
     *
     * @param x abscisse du coin haut-gauche <BR>
     * @param y ordonnée du coin haut-gauche <BR>
     * @param g objet graphique qui permet de dessiner <BR>
     * @param c le composant dans lequel est dessinée l'instruction <BR>
     */
    public void dessiner(int x, int y, Graphics g, Component c) {
        final int largeur = texte.getLargeur(c) + 10;
        final int hauteur = texte.getHauteur(c) + 10;

        texte.dessiner(x + 5, y + 5, g, c);
        g.drawRoundRect(x, y, largeur, hauteur, 10, 10);
    }

    /**
     * Largeur de l'instruction une fois dessinée <BR>
     *
     * @param c composant dans lequel elle est dessinée
     * @return
     */
    public int getLargeur(Component c) {
        return texte.getLargeur(c) + 10;
    }

    /**
     * Hauteur de l'instruction une fois dessinée <BR>
     *
     * @param c composant dans lequel elle est dessinée
     * @return
     */
    public int getHauteur(Component c) {
        return texte.getHauteur(c) + 10;
    }
}
