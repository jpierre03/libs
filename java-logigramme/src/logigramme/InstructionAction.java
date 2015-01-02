package logigramme;

import java.awt.Component;
import java.awt.Graphics;

/**
 * AbstractInstruction simbolisant une action simple
 */
public class InstructionAction extends AbstractInstruction{

	/**Texte de l'action*/
	private Texte texte;
	/**AbstractInstruction suivante*/
	private AbstractInstruction suivant;

	/**
	 * Construit l'instruction ayant le texte donné
	 *
	 * @param action
	 */
	public InstructionAction(String action){
		texte = new Texte(action);
	}

	/**
	 * Change l'instruction suivante
	 *
	 * @param suivant
	 */
	public void setSuivant(AbstractInstruction suivant){
		this.suivant = suivant;
	}

	/**
	 * Dessine l'instruction <BR>
	 * @param x abscisse du coin haut-gauche <BR>
	 * @param y ordonnée du coin haut-gauche <BR>
	 * @param g objet graphique qui permet de dessiner <BR>
	 * @param c le composant dans lequel est dessinée l'instruction <BR>
	 */
	public void dessiner(int x, int y, Graphics g, Component c){
		int larg = texte.getLargeur(c) + 10;
		int haut = texte.getHauteur(c) + 10;
		/**Affichage du texte*/
		texte.dessiner(x + 5, y + 5, g, c);
		/**Rectangle autour du texte*/
		g.drawRect(x, y, larg, haut);
		/**Ligne de liason*/
		g.drawLine(x + larg / 2, y + haut, x + larg / 2, y + haut + 20);
		/**Si il y a une instruction suivante*/
		if(suivant != null){
			/**On calcul son emplacement*/
			int px = 0;
			int l = suivant.getLargeur(c);
			if(l < larg){
				px = (larg - l) / 2;
			}
			/**On la dessine*/
			suivant.dessiner(x + px, y + haut + 20, g, c);
		}
	}

	/**
	 * Largeur de l'instruction une fois dessinée <BR>
	 * @param c composant dans lequel elle est dessinée
	 * @return
	 */
	public int getLargeur(Component c){
		return texte.getLargeur(c) + 10;
	}

	/**
	 * Hauteur de l'instruction une fois dessinée <BR>
	 * @param c composant dans lequel elle est dessinée
	 * @return
	 */
	public int getHauteur(Component c){
		return texte.getHauteur(c) + 30;
	}

	/**
	 * Largeur minimale que doit posséder le composant pour porter cette instruction et ses suivantes <BR>
	 * @param c composant dans lequel elle est dessinée
	 * @return
	 */
	@Override
	public int getLargeurComposant(Component c){
		int larg = getLargeur(c);
		if(suivant == null){
			return larg;
		}
		int l = suivant.getLargeurComposant(c);
		if(l > larg){
			return l;
		}
		return larg;
	}

	/**
	 * Hauteur minimale que doit posséder le composant pour porter cette instruction et ses suivantes <BR>
	 * @param c composant dans lequel elle est dessinée
	 * @return 
	 */
	@Override
	public int getHauteurComposant(Component c){
		if(suivant == null){
			return getHauteur(c);
		}
		return getHauteur(c) + suivant.getHauteurComposant(c);
	}
}
