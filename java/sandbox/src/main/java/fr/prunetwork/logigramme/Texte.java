package fr.prunetwork.logigramme;

import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Vector;
import java.util.StringTokenizer;

/**
 * Dessine un texte
 */
public class Texte{

	/**Vecteur contenant les lignes du texte*/
	private Vector<String> lignes = new Vector<String>();

	/**
	 * Construit le texte
	 *
	 * @param texte
	 */
	public Texte(String texte){
		StringTokenizer st = new StringTokenizer(texte, "\n", false);
		/**Tant qu'il y a une ligne, l'ajouter*/
		while(st.hasMoreTokens()){
			lignes.addElement(st.nextToken());
		}
	}

	/**
	 * Largeur du texte <BR>
	 * @param c Le composant dans lequel le texte est dessiné
	 * @return
	 */
	public int getLargeur(Component c){
		/**On récupére le mesureur de chaînes de caractéres*/
		FontMetrics fm = c.getFontMetrics(c.getFont());
		/**On récupère le nombre de lignes*/
		int nbLignes = lignes.size();
		/**La largeur est initialisée à zéro*/
		int largeur = 0;
		/**Pour chaque ligne du texte*/
		for(int i = 0; i < nbLignes; i++){
			/**On récupère la largeur de la ligne actuelle*/
			int larg = fm.stringWidth(lignes.elementAt(i));
			if(larg > largeur){
				largeur = larg;
			}
		}
		return largeur;
	}

	/**
	 * Hauteur du texte <BR>
	 * @param c Le composant dans lequel le texte est dessiné
	 * @return
	 */
	public int getHauteur(Component c){
		FontMetrics fm = c.getFontMetrics(c.getFont());
		return fm.getHeight() * lignes.size();
	}

	/**
	 * Dessine le texte <BR>
	 * @param x abscisse du coin haut-gauche <BR>
	 * @param y ordonnée du coin haut-gauche <BR>
	 * @param g objet graphique qui permet de dessiner <BR>
	 * @param c le composant dans lequel est dessinée l'instruction <BR>
	 */
	public void dessiner(int x, int y, Graphics g, Component c){
		/**On récupére le mesureur de chaînes de caractéres*/
		g.setFont(c.getFont());
		FontMetrics fm = c.getFontMetrics(c.getFont());
		int hauteurLigne = fm.getHeight();
		y += 2 * hauteurLigne / 3;
		/**On dessine chaqu'une des lignes, les unes sous les autres*/
		int nb = lignes.size();
		for(int i = 0; i < nb; i++){
			String ligne = lignes.elementAt(i);
			g.drawString(ligne, x, y);
			y += hauteurLigne;
		}
	}
}
