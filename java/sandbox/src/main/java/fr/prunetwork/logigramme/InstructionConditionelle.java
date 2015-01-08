package fr.prunetwork.logigramme;

import java.awt.*;

/**
 * AbstractInstruction conditionnelle
 */
public class InstructionConditionelle extends AbstractInstruction{

	/**Texte oui*/
	private static final Texte OUI = new Texte("oui");
	/**Texte non*/
	private static final Texte NON = new Texte("non");
	/**Texte de l'instruction*/
	private Texte texte;
	/**AbstractInstruction sur la branche oui*/
	private AbstractInstruction oui;
	/**AbstractInstruction sur la branche non*/
	private AbstractInstruction non;

	/**
	 * Construit l'instruction
	 *
	 * @param condition
	 */
	public InstructionConditionelle(String condition){
		texte = new Texte(condition);
	}

	/**
	 * Change l'instruction aprés le oui
	 *
	 * @param instruction
	 */
	public void setOui(AbstractInstruction instruction){
		oui = instruction;
	}

	/**
	 * Change l'instruction aprés le non
	 *
	 * @param instruction
	 */
	public void setNon(AbstractInstruction instruction){
		non = instruction;
	}

	/**
	 * Dessine l'instruction <BR>
	 * @param x abscisse du coin haut-gauche <BR>
	 * @param y ordonnée du coin haut-gauche <BR>
	 * @param g objet graphique qui permet de dessiner <BR>
	 * @param c le composant dans lequel est dessinée l'instruction <BR>
	 */
	public void dessiner(int x, int y, Graphics g, Component c){
		int lgOui = OUI.getLargeur(c);
		int htOui = OUI.getHauteur(c);
		int lgNon = NON.getLargeur(c);
		int htNon = NON.getHauteur(c);
		int larg = texte.getLargeur(c);
		int haut = texte.getHauteur(c);
		/**Dessin du texte*/
		texte.dessiner(x + larg / 2, y + haut / 2, g, c);
		/**---losange---*/
		g.drawLine(x, y + haut, x + larg, y);
		g.drawLine(x + larg, y, x + 2 * larg, y + haut);
		g.drawLine(x + 2 * larg, y + haut, x + larg, y + 2 * haut);
		g.drawLine(x + larg, y + 2 * haut, x, y + haut);
		/**---oui----*/
		g.drawLine(x + larg, y + 2 * haut, x + larg, y + 2 * haut + 10);
		OUI.dessiner(x + larg - lgOui / 2, y + 2 * haut + 10, g, c);
		g.drawLine(x + larg, y + 2 * haut + 10 + htOui, x + larg, y + 2 * haut + 20 + htOui);
		/**---non---*/
		g.drawLine(x + 2 * larg, y + haut, x + 2 * larg + 10, y + haut);
		NON.dessiner(x + 2 * larg + 10, y + haut - htNon / 2, g, c);
		g.drawLine(x + 2 * larg + 10 + lgNon, y + haut, x + 2 * larg + 20 + lgNon, y + haut);
		/**Dessine l'instruction en oui, si elle existe*/
		int xx = x + 2 * larg + 20 + lgNon;
		if(oui != null){
			int px = 0;
			int l = oui.getLargeur(c);
			if(l < 2 * larg){
				px = (2 * larg - l) / 2;
			}
			oui.dessiner(x + px, y + 2 * haut + 20 + htOui, g, c);
			l = oui.getLargeurComposant(c);
			if(l > xx){
				xx = l;
			}
		}
		/**Dessine l'instruction en non, si elle existe*/
		if(non != null){
			g.drawLine(x + 2 * larg + 20 + lgNon, y + haut, xx, y + haut);
			non.dessiner(xx, y, g, c);
		}
	}

	/**
	 * Largeur de l'instruction une fois dessinée <BR>
	 * @param c composant dans lequel elle est dessinée
	 * @return
	 */
	public int getLargeur(Component c){
		int xx = 2 * texte.getLargeur(c) + 20 + NON.getLargeur(c);
		if(oui != null){
			int l = oui.getLargeurComposant(c);
			if(l > xx){
				xx = l;
			}
		}
		return xx;
	}

	/**
	 * Hauteur de l'instruction une fois dessinée <BR>
	 * @param c composant dans lequel elle est dessinée
	 * @return
	 */
	public int getHauteur(Component c){
		return 2 * texte.getHauteur(c) + 20 + OUI.getHauteur(c);
	}

	/**
	 * Largeur minimale que doit posséder le composant pour porter cette instruction et ses suivantes <BR>
	 * @param c composant dans lequel elle est dessinée
	 * @return
	 */
	@Override
	public int getLargeurComposant(Component c){
		if(non == null){
			return getLargeur(c);
		}
		return getLargeur(c) + non.getLargeurComposant(c);
	}

	/**
	 * Hauteur minimale que doit posséder le composant pour porter cette instruction et ses suivantes <BR>
	 * @param c composant dans lequel elle est dessinée
	 * @return
	 */
	@Override
	public int getHauteurComposant(Component c){
		if(oui == null){
			return getHauteur(c);
		}
		return getHauteur(c) + oui.getHauteurComposant(c);
	}
}
