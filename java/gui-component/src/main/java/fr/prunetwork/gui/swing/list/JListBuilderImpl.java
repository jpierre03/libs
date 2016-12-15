package fr.prunetwork.gui.swing.list;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * This component allow user to create a list of elements (generics) from an other list.
 * There is 4 buttons (add, remove, add all, remove all) to do the job.
 *
 * @author Jean-Pierre PRUNARET
 */
public class JListBuilderImpl<T>
        extends JPanel implements JListBuilder<T> {

    private final Collection<T> objects = new ArrayList<>();
    private final DefaultListModel<T> completeModel = new DefaultListModel<>();
    private final DefaultListModel<T> partialModel = new DefaultListModel<>();

    private final JList<T> completeJList = new JList<>(completeModel);
    private final JList<T> partialJList = new JList<>(partialModel);
    private final JScrollPane completeListPanel = new JScrollPane(completeJList);
    private final JScrollPane partialListPanel = new JScrollPane(partialJList);
    private final JButton addButton = new JButton("+");
    private final JButton addAllButton = new JButton("++");
    private final JButton removeButton = new JButton("-");
    private final JButton removeAllButton = new JButton("--");

    public JListBuilderImpl() {
        build();
    }

    private void build() {
        setLayout(new BorderLayout());

        @NotNull final GridLayout gridLayout = new GridLayout(4, 1);
        gridLayout.setVgap(10);
        @NotNull JPanel buttonPanel = new JPanel(gridLayout);
        buttonPanel.add(addAllButton);
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(removeAllButton);

        @NotNull JSplitPane split1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        @NotNull JSplitPane split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        int position = 275;
        int largeur = 50;

        @NotNull final JPanel layoutPanel = new JPanel(new FlowLayout());
        layoutPanel.add(buttonPanel);
        split1.add(completeListPanel);
        split1.add(layoutPanel);
        split1.setDividerLocation(position - largeur);

        split2.add(split1);
        split2.add(partialListPanel);
        split2.setDividerLocation(position);

        add(split2);

        addAllButton.addActionListener(e -> addAllAction());
        addButton.addActionListener(e -> addAction());
        removeButton.addActionListener(e -> removeAction());
        removeAllButton.addActionListener(e -> removeAllAction());
    }

    @Override
    public boolean addChoosed(@NotNull T object) {
        boolean result = false;

        if (!partialModel.contains(object)) {
            partialModel.addElement(object);
            result = true;
        }

        return result;
    }

    @Override
    public boolean add(@NotNull T object) {
        boolean result = false;

        if (!objects.contains(object)) {
            result = objects.add(object);
            completeModel.addElement(object);
        }
        return result;
    }

    @Override
    public boolean remove(@NotNull T object) {
        boolean result;

        result = objects.remove(object);
        completeModel.removeElement(object);
        partialModel.removeElement(object);

        return result;
    }

    private void addAllAction() {
        removeAllAction();

        objects.forEach(partialModel::addElement);
        clearSelection();
    }

    private void removeAllAction() {
        partialModel.clear();
        clearSelection();
    }

    private void addAction() {
        if (!completeJList.isSelectionEmpty()) {

            completeJList.getSelectedValuesList()
                    .stream()
                    .filter(t -> !partialModel.contains(t))
                    .forEach(partialModel::addElement);
        } else {
            emptySelectionError();
        }
        clearSelection();
    }

    private void removeAction() {
        if (!partialJList.isSelectionEmpty()) {

            partialJList.getSelectedValuesList()
                    .forEach(partialModel::removeElement);
        } else {
            emptySelectionError();
        }
        clearSelection();
    }

    private void clearSelection() {
        completeJList.clearSelection();
        partialJList.clearSelection();
    }

    @Override
    @NotNull
    public Collection<T> getChoosed() {
        return Collections.list(partialModel.elements());
    }

    @Override
    public int getChoosedSize() {
        return partialModel.size();
    }

    private void emptySelectionError() {
        JOptionPane.showMessageDialog(this, SELECTION_EMPTY, SELECTION_ERROR, JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void setFont(final Font font) {
        super.setFont(font);
        SwingUtilities.invokeLater(() -> {
            completeJList.setFont(font);
            partialJList.setFont(font);
        });
    }
}
