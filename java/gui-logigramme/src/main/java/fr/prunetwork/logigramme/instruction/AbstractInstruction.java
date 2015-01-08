package fr.prunetwork.logigramme.instruction;

import fr.prunetwork.logigramme.Instruction;

import java.awt.*;

/**
 * AbstractInstruction générale de l'organigramme
 */
abstract class AbstractInstruction implements Instruction {

    @Override
    public int getLargeurComposant(Component c) {
        return getLargeur(c);
    }

    @Override
    public int getHauteurComposant(Component c) {
        return getHauteur(c);
    }
}
