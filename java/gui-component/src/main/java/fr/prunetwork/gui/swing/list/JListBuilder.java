package fr.prunetwork.gui.swing.list;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * This component allow user to create a list of elements (generics) from an other list.
 * There is 4 buttons (add, remove, add all, remove all) to do the job.
 *
 * @author Jean-Pierre PRUNARET
 */
public class JListBuilder<T>
        extends JPanel {

    public static final String SELECTION_ERROR = "Erreur de sélection";
    public static final String SELECTION_EMPTY = "Vous n'avez pas sélectionné d'élément !";

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

    public JListBuilder() {
        build();
    }

    private void build() {
        setLayout(new BorderLayout());

        final GridLayout gridLayout = new GridLayout(4, 1);
        gridLayout.setVgap(10);
        JPanel buttonPanel = new JPanel(gridLayout);
        buttonPanel.add(addAllButton);
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(removeAllButton);

        JSplitPane split1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JSplitPane split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        int position = 275;
        int largeur = 50;

        final JPanel layoutPanel = new JPanel(new FlowLayout());
        layoutPanel.add(buttonPanel);
        split1.add(completeListPanel);
        split1.add(layoutPanel);
        split1.setDividerLocation(position - largeur);

        split2.add(split1);
        split2.add(partialListPanel);
        split2.setDividerLocation(position);

        add(split2);

        addAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAllAction();
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAction();
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAction();
            }
        });
        removeAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAllAction();
            }
        });
    }

    public boolean addChoosed(T object) {
        boolean result = false;

        if (!partialModel.contains(object)) {
            partialModel.addElement(object);
        }

        return result;
    }

    public boolean add(T object) {
        boolean result = false;

        if (!objects.contains(object)) {
            result = objects.add(object);
            completeModel.addElement(object);
        }
        return result;
    }

    public boolean remove(T object) {
        boolean result;

        result = objects.remove(object);
        completeModel.removeElement(object);
        partialModel.removeElement(object);

        return result;
    }

    private void addAllAction() {
        removeAllAction();

        for (T object : objects) {
            partialModel.addElement(object);
        }
        clearSelection();
    }

    private void removeAllAction() {
        partialModel.clear();
        clearSelection();
    }

    private void addAction() {
        if (!completeJList.isSelectionEmpty()) {

            for (T t : completeJList.getSelectedValuesList()) {
                if (!partialModel.contains(t)) {
                    partialModel.addElement(t);
                }
            }
        } else {
            emptySelectionError();
        }
        clearSelection();
    }

    private void removeAction() {
        if (!partialJList.isSelectionEmpty()) {

            for (T t : partialJList.getSelectedValuesList()) {
                partialModel.removeElement(t);
            }
        } else {
            emptySelectionError();
        }
        clearSelection();
    }

    private void clearSelection() {
        completeJList.clearSelection();
        partialJList.clearSelection();
    }

    public Collection<T> getChoosed() {
        return Collections.list(partialModel.elements());
    }

    public int getChoosedSize() {
        return partialModel.size();
    }

    private void emptySelectionError() {
        JOptionPane.showMessageDialog(this, SELECTION_EMPTY, SELECTION_ERROR, JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void setFont(final Font font) {
        super.setFont(font);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                completeJList.setFont(font);
                partialJList.setFont(font);
            }
        });
    }
}
