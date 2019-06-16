package fr.prunetwork.logigramme.instruction;

import fr.prunetwork.logigramme.Instruction;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * AbstractInstruction générale de l'organigramme
 */
abstract class AbstractInstruction implements Instruction {

    @Override
    public int getLargeurComposant(@NotNull final Component c) {
        return getLargeur(c);
    }

    @Override
    public int getHauteurComposant(@NotNull final Component c) {
        return getHauteur(c);
    }
}
