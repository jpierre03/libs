package fr.prunetwork.logigramme;

import fr.prunetwork.logigramme.instruction.*;

import javax.swing.*;
import java.awt.*;

/**
 * Classe de test
 */
public class Main extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Construit la frame
     */
    public Main() {
        /**Initialisation*/
        super("");
        try {
            logigrammeDemo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**Dimensionne et met en place*/
        pack();
        /**Centre la frame sur l'écran*/
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension d = getSize();
        if (d.width > dim.width) {
            d.width = dim.width;
        }
        if (d.height > dim.height) {
            d.height = dim.height;
        }
        setSize(d);
//		setLocation((dim.width - d.width) / 2, (dim.height - d.height) / 2);
        /**Montre la frame*/
        setVisible(true);
    }

    /**
     * Lance le test
     *
     * @param args
     */
    public static void main(String[] args) {
        new Main();
    }

    /**
     * Initialisation
     *
     * @throws Exception
     */
    private void logigrammeDemo() throws Exception {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        /**---Construction d'un organigramme---*/
        /** -Les instructions-*/
        Debut debut = new Debut();
        fr.prunetwork.logigramme.instruction.Action action1 = new fr.prunetwork.logigramme.instruction.Action("Action 1\nFaire qqch");
        fr.prunetwork.logigramme.instruction.Action action2 = new fr.prunetwork.logigramme.instruction.Action("Action 2\nFaire autre ch");
        fr.prunetwork.logigramme.instruction.Action action3 = new fr.prunetwork.logigramme.instruction.Action("Action 3\nFaire autre ch");
        Condition cond = new Condition("Condition");
        Condition cond2 = new Condition("Condition N°2");
        Condition cond0 = new Condition("Condition N°0");
        fr.prunetwork.logigramme.instruction.Action oui = new fr.prunetwork.logigramme.instruction.Action("Si oui\nOn va là");
        fr.prunetwork.logigramme.instruction.Action non = new fr.prunetwork.logigramme.instruction.Action("Si non\nOn va ici");
        Fin fin = new Fin();
        /** -Organisation-*/
        debut.setSuivant(action1);
        action1.setSuivant(action2);
        action2.setSuivant(cond0);
        action3.setSuivant(cond);
        cond0.setOui(action3);
        cond0.setNon(fin);
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
        logigrammeComponent.setBorder(BorderFactory.createTitledBorder("Un beau logigramme"));
        /**On place le composant dans un JScrollPanel dans la frame*/
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(new JScrollPane(logigrammeComponent), BorderLayout.CENTER);
    }
}
