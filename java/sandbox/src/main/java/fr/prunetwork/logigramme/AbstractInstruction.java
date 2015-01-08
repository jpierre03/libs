package fr.prunetwork.logigramme;

import java.awt.Component;
import java.awt.Graphics;

/**
 * AbstractInstruction générale de l'organigramme
 */
public abstract class AbstractInstruction{

	/**
	 * Dessine l'instruction <BR>
	 * @param x abscisse du coin haut-gauche <BR>
	 * @param y ordonnée du coin haut-gauche <BR>
	 * @param g objet graphique qui permet de dessiner <BR>
	 * @param c le composant dans lequel est dessinée l'instruction <BR>
	 */
	public abstract void dessiner(int x, int y, Graphics g, Component c);

	/**
	 * Largeur de l'instruction une fois dessinée <BR>
	 * @param c composant dans lequel elle est dessinée
	 * @return
	 */
	public abstract int getLargeur(Component c);

	/**
	 * Hauteur de l'instruction une fois dessinée <BR>
	 * @param c composant dans lequel elle est dessinée
	 * @return
	 */
	public abstract int getHauteur(Component c);

	/**
	 * Largeur minimale que doit posséder le composant pour porter cette instruction et ses suivantes <BR>
	 * @param c composant dans lequel elle est dessinée
	 * @return
	 */
	public int getLargeurComposant(Component c){
		return getLargeur(c);
	}

	/**
	 * Hauteur minimale que doit posséder le composant pour porter cette instruction et ses suivantes <BR>
	 * @param c composant dans lequel elle est dessinée
	 * @return
	 */
	public int getHauteurComposant(Component c){
		return getHauteur(c);
	}
}
