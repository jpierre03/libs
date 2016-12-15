package fr.prunetwork.gui.swing.list;

import org.jetbrains.annotations.NotNull;

import javax.accessibility.Accessible;
import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.Serializable;
import java.util.Collection;

/**
 * @author Jean-Pierre PRUNARET
 * @since 2016-11-13
 */
public interface JListBuilder<T> extends ImageObserver, MenuContainer, Serializable, Accessible {

    String SELECTION_ERROR = "Erreur de sélection";
    String SELECTION_EMPTY = "Vous n'avez pas sélectionné d'élément !";

    boolean addChoosed(@NotNull T object);

    boolean add(@NotNull T object);

    boolean remove(@NotNull T object);

    @NotNull
    Collection<T> getChoosed();

    int getChoosedSize();
}
