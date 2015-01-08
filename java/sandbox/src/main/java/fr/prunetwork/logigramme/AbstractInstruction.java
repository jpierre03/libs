package fr.prunetwork.logigramme;

import java.awt.*;

/**
 * AbstractInstruction générale de l'organigramme
 */
public abstract class AbstractInstruction implements Instruction {

    @Override
    public int getLargeurComposant(Component c) {
        return getLargeur(c);
    }

    @Override
    public int getHauteurComposant(Component c) {
        return getHauteur(c);
    }
}
