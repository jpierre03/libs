package fr.prunetwork.logigramme.instruction;

import fr.prunetwork.logigramme.Instruction;
import fr.prunetwork.logigramme.Texte;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

/**
 * Instruction indiquant le début de l'organigramme
 */
public final class Debut extends AbstractInstruction {

    /**
     * Texte "Début"
     */
    @NotNull
    private Texte texte;
    /**
     * AbstractInstruction suivante
     */
    @Nullable
    private Instruction suivant;

    /**
     * Construction
     */
    public Debut() {
        texte = new Texte("Début");
    }

    /**
     * Change le suivant
     *
     * @param suivant
     */
    public void setSuivant(@NotNull final Instruction suivant) {
        this.suivant = suivant;
    }

    /**
     * Dessine l'instruction <BR>
     *
     * @param x abscisse du coin haut-gauche <BR>
     * @param y ordonnée du coin haut-gauche <BR>
     * @param g objet graphique qui permet de dessiner <BR>
     * @param c le composant dans lequel est dessinée l'instruction <BR>
     */
    public void dessiner(int x, int y, @NotNull final Graphics g, @NotNull final Component c) {
        final int largeur = texte.getLargeur(c) + 10;
        final int hauteur = texte.getHauteur(c) + 10;

        texte.dessiner(x + 5, y + 5, g, c);
        g.drawRoundRect(x, y, largeur, hauteur, 10, 10);
        g.drawLine(x + largeur / 2, y + hauteur, x + largeur / 2, y + hauteur + 20);
        if (suivant != null) {
            int px = 0;
            final int l = suivant.getLargeur(c);
            if (l < largeur) {
                px = (largeur - l) / 2;
            }
            suivant.dessiner(x + px, y + hauteur + 20, g, c);
        }
    }

    /**
     * Largeur de l'instruction une fois dessinée <BR>
     *
     * @param c composant dans lequel elle est dessinée
     * @return
     */
    public int getLargeur(@NotNull final Component c) {
        return texte.getLargeur(c) + 10;
    }

    /**
     * Hauteur de l'instruction une fois dessinée <BR>
     *
     * @param c composant dans lequel elle est dessinée
     * @return
     */
    public int getHauteur(@NotNull final Component c) {
        return texte.getHauteur(c) + 30;
    }

    /**
     * Largeur minimale que doit posséder le composant pour porter cette instruction et ses suivantes <BR>
     *
     * @param c composant dans lequel elle est dessinée
     * @return
     */
    @Override
    public int getLargeurComposant(@NotNull final Component c) {
        final int largeur = getLargeur(c);

        if (suivant == null) {
            return largeur;
        }

        final int l = suivant.getLargeurComposant(c);
        if (l > largeur) {
            return l;
        }
        return largeur;
    }

    /**
     * Hauteur minimale que doit posséder le composant pour porter cette instruction et ses suivantes <BR>
     *
     * @param c composant dans lequel elle est dessinée
     * @return
     */
    @Override
    public int getHauteurComposant(@NotNull final Component c) {
        if (suivant == null) {
            return getHauteur(c);
        }

        return getHauteur(c) + suivant.getHauteurComposant(c);
    }
}
