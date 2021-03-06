package fr.prunetwork.logigramme;

import fr.prunetwork.logigramme.instruction.Debut;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * Icone dessinant l'organigramme
 */
public class Logigramme implements Icon {

    /**
     * Instruction débutant l'organigramme
     */
    private Debut debut;
    /**
     * Composant contenant l'organigramme
     */
    private ComposantLogigramme composant;

    /**
     * Construit l'organigramme
     *
     * @param debut
     */
    public Logigramme(@NotNull Debut debut) {
        this.debut = debut;
    }

    /**
     * Donne le composant contenant l'organigramme
     *
     * @param composant
     */
    public void setComposantOrganigramme(@NotNull final ComposantLogigramme composant) {
        this.composant = composant;
    }

    /**
     * Dessine l'organigramme <BR>
     *
     * @param x abscisse du coin haut-gauche <BR>
     * @param y ordonnée du coin haut-gauche <BR>
     * @param g objet graphique qui permet de dessiner <BR>
     * @param c le composant dans lequel est dessinée l'instruction <BR>
     */
    public void paintIcon(@NotNull final Component c, @NotNull final Graphics g, int x, int y) {
        debut.dessiner(x, y, g, c);
    }

    /**
     * Largeur de l'organigramme
     */
    public int getIconWidth() {
        if (composant == null) {
            return 100;
        }
        return debut.getLargeurComposant(composant) + 1;
    }

    /**
     * Hauteur de l'organigramme
     */
    public int getIconHeight() {
        if (composant == null) {
            return 100;
        }
        return debut.getHauteurComposant(composant) + 1;
    }
}
