package fr.prunetwork.logigramme;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Dessine un texte
 */
public class Texte {

    /**
     * Structure contenant les lignes du texte
     */
    @NotNull
    private java.util.List<String> lignes = new ArrayList<>();

    /**
     * Construit le texte
     *
     * @param texte
     */
    public Texte(@NotNull String texte) {
        @NotNull StringTokenizer tokenizer = new StringTokenizer(texte, "\n", false);
        /**Tant qu'il y a une ligne, l'ajouter*/
        while (tokenizer.hasMoreTokens()) {
            lignes.add(tokenizer.nextToken());
        }
    }

    /**
     * Largeur du texte <BR>
     *
     * @param c Le composant dans lequel le texte est dessiné
     * @return
     */
    public int getLargeur(@NotNull Component c) {
        /**On récupére le mesureur de chaînes de caractéres*/
        final FontMetrics fm = c.getFontMetrics(c.getFont());

        /**La largeur est initialisée à zéro*/
        int largeur = 0;

        /**Pour chaque ligne du texte*/
        for (@NotNull String ligne : lignes) {
            /**On récupère la largeur de la ligne actuelle*/
            int largeurLigne = fm.stringWidth(ligne);
            if (largeurLigne > largeur) {
                largeur = largeurLigne;
            }
        }
        return largeur;
    }

    /**
     * Hauteur du texte <BR>
     *
     * @param c Le composant dans lequel le texte est dessiné
     * @return
     */
    public int getHauteur(@NotNull Component c) {
        final FontMetrics fm = c.getFontMetrics(c.getFont());
        return fm.getHeight() * lignes.size();
    }

    /**
     * Dessine le texte <BR>
     *
     * @param x abscisse du coin haut-gauche <BR>
     * @param y ordonnée du coin haut-gauche <BR>
     * @param g objet graphique qui permet de dessiner <BR>
     * @param c le composant dans lequel est dessinée l'instruction <BR>
     */
    public void dessiner(int x, int y, @NotNull Graphics g, @NotNull Component c) {
        /**On récupére le mesureur de chaînes de caractéres*/
        g.setFont(c.getFont());

        final FontMetrics fm = c.getFontMetrics(c.getFont());
        final int hauteurLigne = fm.getHeight();
        y += 2 * hauteurLigne / 3;

        /**On dessine chaqu'une des lignes, les unes sous les autres*/
        for (String ligne : lignes) {
            g.drawString(ligne, x, y);
            y += hauteurLigne;
        }
    }
}
