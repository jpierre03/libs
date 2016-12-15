package fr.prunetwork.logigramme;

import fr.prunetwork.logigramme.instruction.*;
import fr.prunetwork.logigramme.instruction.Action;
import org.jetbrains.annotations.NotNull;

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
        @NotNull Debut debut = new Debut();
        @NotNull Action action1 = new Action("Action 1\nFaire qqch");
        @NotNull Action action2 = new Action("Action 2\nFaire autre ch");
        @NotNull Action action3 = new Action("Action 3\nFaire autre ch");
        @NotNull Condition cond = new Condition("Condition");
        @NotNull Condition cond2 = new Condition("Condition N°2");
        @NotNull Condition cond0 = new Condition("Condition N°0");
        @NotNull Action oui = new Action("Si oui\nOn va là");
        @NotNull Action non = new Action("Si non\nOn va ici");
        @NotNull Fin fin = new Fin();
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
        @NotNull Logigramme logigramme = new Logigramme(debut);
        /**On met l'organigramme dans un composant porteur*/
        @NotNull ComposantLogigramme logigrammeComponent = new ComposantLogigramme(logigramme);
        /**Met un tour avec un titre*/
        logigrammeComponent.setBorder(BorderFactory.createTitledBorder("Un beau logigramme"));
        /**On place le composant dans un JScrollPanel dans la frame*/
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(new JScrollPane(logigrammeComponent), BorderLayout.CENTER);
    }
}
