package fr.prunetwork.logigramme;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * @author Jean-Pierre PRUNARET
 * @since 08/01/15
 */
public interface Instruction {
    /**
     * Dessine l'instruction <BR>
     *
     * @param x abscisse du coin haut-gauche <BR>
     * @param y ordonnée du coin haut-gauche <BR>
     * @param g objet graphique qui permet de dessiner <BR>
     * @param c le composant dans lequel est dessinée l'instruction <BR>
     */
    void dessiner(int x, int y, @NotNull final Graphics g, @NotNull final Component c);

    /**
     * Largeur de l'instruction une fois dessinée <BR>
     *
     * @param c composant dans lequel elle est dessinée
     * @return
     */
    int getLargeur(@NotNull final Component c);

    /**
     * Hauteur de l'instruction une fois dessinée <BR>
     *
     * @param c composant dans lequel elle est dessinée
     * @return
     */
    int getHauteur(@NotNull final Component c);

    /**
     * Largeur minimale que doit posséder le composant pour porter cette instruction et ses suivantes <BR>
     *
     * @param c composant dans lequel elle est dessinée
     * @return
     */
    int getLargeurComposant(@NotNull final Component c);

    /**
     * Hauteur minimale que doit posséder le composant pour porter cette instruction et ses suivantes <BR>
     *
     * @param c composant dans lequel elle est dessinée
     * @return
     */
    int getHauteurComposant(@NotNull final Component c);
}
