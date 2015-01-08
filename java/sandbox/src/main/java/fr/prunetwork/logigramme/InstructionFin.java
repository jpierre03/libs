package fr.prunetwork.logigramme;

import java.awt.*;

/**
 * AbstractInstruction indiquant une fin à l'organigramme
 */
public class InstructionFin extends AbstractInstruction {

    /**
     * Texte "Fin"
     */
    private Texte texte;

    /**
     * Construction
     */
    public InstructionFin() {
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
        int larg = texte.getLargeur(c) + 10;
        int haut = texte.getHauteur(c) + 10;
        texte.dessiner(x + 5, y + 5, g, c);
        g.drawRoundRect(x, y, larg, haut, 10, 10);
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
