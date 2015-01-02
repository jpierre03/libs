package logigramme;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;

/**
 * Classe de test
 */
public class Principal extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construit la frame
	 */
	public Principal(){
		/**Initialisation*/
		super("");
		try{
			logigrammeDemo();
		} catch(Exception e){
			e.printStackTrace();
		}
		/**Dimensionne et met en place*/
		pack();
		/**Centre la frame sur l'écran*/
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension d = getSize();
		if(d.width > dim.width){
			d.width = dim.width;
		}
		if(d.height > dim.height){
			d.height = dim.height;
		}
		setSize(d);
//		setLocation((dim.width - d.width) / 2, (dim.height - d.height) / 2);
		/**Montre la frame*/
		setVisible(true);
	}

	/**Initialisation
	 * @throws Exception 
	 */
	private void logigrammeDemo() throws Exception{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		/**---Construction d'un organigramme---*/
		/** -Les instructions-*/
		InstructionDebut debut = new InstructionDebut();
		InstructionAction action1 = new InstructionAction("Action 1\nFaire qqch");
		InstructionAction action2 = new InstructionAction("Action 2\nFaire autre ch");
		InstructionConditionelle cond = new InstructionConditionelle("Condition");
		InstructionConditionelle cond2 = new InstructionConditionelle("Condition N°2");
		InstructionAction oui = new InstructionAction("Si oui\nOn va là");
		InstructionAction non = new InstructionAction("Si non\nOn va ici");
		InstructionFin fin = new InstructionFin();
		/** -Organisation-*/
		debut.setSuivant(action1);
		action1.setSuivant(action2);
		action2.setSuivant(cond);
		cond.setOui(cond2);
		cond.setNon(cond2);
		cond2.setOui(oui);
		cond2.setNon(non);
		oui.setSuivant(fin);
		non.setSuivant(fin);
		/** -Logigramme prêt-*/
		Logigramme logigramme = new Logigramme(debut);
		/**On met l'organigramme dans un composant porteur*/
		ComposantLogigramme logigrammeComponent = new ComposantLogigramme(logigramme);
		/**Met un tour avec un titre*/
		logigrammeComponent.setBorder(BorderFactory.createTitledBorder("titre"));
		/**On place le composant dans un JScrollPanel dans la frame*/
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new JScrollPane(logigrammeComponent), BorderLayout.CENTER);
	}

	/**
	 * Lance le test
	 * @param args
	 */
	public static void main(String[] args){
		new Principal();
	}
}