package fr.prunetwork.gui.table;

import fr.prunetwork.gui.swing.table.HighlightRenderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Jean-Pierre PRUNARET
 * @since 10/07/2014
 */
public class HistoryTable extends JPanel {

    @NotNull
    private final JTable table;
    private boolean isMultipleSelection = true;

    public HistoryTable(@Nullable final MessageTableModel dm, @Nullable TableColumnModel cm, @Nullable ListSelectionModel sm) {
        table = new JTable(dm, cm, sm);
        setLayout(new BorderLayout());

        @NotNull final JButton clear = new JButton("Effacer historique");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dm != null) {
                    dm.purge();
                }
            }
        });

        @NotNull JPanel searchPanel = new JPanel(new BorderLayout());
        @NotNull final JLabel searchCounter = new JLabel();
        @NotNull final JTextField searchField = new JTextField();
        searchField.addActionListener(new SearchActionListener(searchField, searchCounter, table, isMultipleSelection, false));

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchCounter, BorderLayout.WEST);

        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(clear, BorderLayout.SOUTH);

        table.setAutoCreateRowSorter(true);
    }

    public HistoryTable() {
        this(null, null, null);
    }

    public HistoryTable(@NotNull MessageTableModel dm) {
        this(dm, null, null);
    }

    public void setDefaultRenderer(Class<?> columnClass, TableCellRenderer renderer) {
        table.setDefaultRenderer(columnClass, renderer);
    }

    class SearchActionListener implements ActionListener {

        private static final String ERROR_EMPTY_SEARCH = "La recherche demandée est vide.\n Voulez vous chercher quand même ?";
        private static final String ERROR_TITLE = "Erreur sur la recherche";
        private final JTextField field;
        private final JLabel counter;
        private final JTable table;
        private final boolean isMultipleSelection;
        private final boolean applyHighlighter;

        SearchActionListener(JTextField field, JLabel counter, JTable table, boolean isMultipleSelection, boolean applyHighlighter) {
            this.field = field;
            this.counter = counter;
            this.table = table;
            this.isMultipleSelection = isMultipleSelection;
            this.applyHighlighter = applyHighlighter;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            @NotNull final String searchedValue = field.getText().toLowerCase();
            table.clearSelection();
            int count = 0;

            boolean okToContinue = true;

            if (searchedValue.isEmpty()) {
                final int choice = JOptionPane.showConfirmDialog(table, ERROR_EMPTY_SEARCH, ERROR_TITLE, JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                okToContinue = (choice == JOptionPane.OK_OPTION);
            }

            if (okToContinue) {
                for (int row = 0; row <= table.getRowCount() - 1; row++) {
                    for (int col = 0; col <= table.getColumnCount() - 1; col++) {

                        @NotNull final String cellContent = table.getValueAt(row, col).toString().toLowerCase();

                        if (cellContent.contains(searchedValue)) {
                            count++;

                            // this will automatically set the view of the scroll in the location of the value
                            table.scrollRectToVisible(table.getCellRect(row, 0, true));

                            // this will automatically set the focus of the searched/selected row/value
                            if (isMultipleSelection) {
                                table.addRowSelectionInterval(row, row);
                            } else {
                                table.setRowSelectionInterval(row, row);
                            }

                            if (applyHighlighter) {
                                for (int i = 0; i <= table.getColumnCount() - 1; i++) {
                                    table.getColumnModel().getColumn(i).setCellRenderer(new HighlightRenderer());
                                }
                            }
                        }
                    }
                }
            }
            counter.setText(String.valueOf(count));
        }
    }
}


