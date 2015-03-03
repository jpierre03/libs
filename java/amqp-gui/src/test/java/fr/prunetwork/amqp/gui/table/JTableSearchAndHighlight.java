package fr.prunetwork.amqp.gui.table;

import fr.prunetwork.gui.swing.table.HighlightRenderer;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Jean-Pierre PRUNARET
 * @since 10/07/2014
 */
public class JTableSearchAndHighlight extends JFrame {
    private JTextField searchField;
    private JTable table;
    private JPanel panel;
    private JScrollPane scroll;

    public JTableSearchAndHighlight() {

        initializeInventory();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new JTableSearchAndHighlight());
    }

    private void initializeInventory() {

        panel = new JPanel();
        searchField = new JTextField();

        panel.setLayout(new BorderLayout());

        @NotNull final String[] columnNames = {"Name", "Surname", "Age"};

        @NotNull final Object[][] data = {
                {"Jhon", "Java", "23"}, {"Stupid", "Stupido", "500"},
                {"Michael", "Winnie", "20"}, {"Winnie", "Thepoor", "23"},
                {"Michael", "Winnie", "20"}, {"Winnie", "Thepoor", "23"},
                {"Michael", "Winnie", "20"}, {"Winnie", "Thepoor", "23"},
                {"Michael", "Winnie", "20"}, {"Winnie", "Thepoor", "23"},
                {"Max", "Dumbass", "10"}, {"Melanie", "Martin", "500"},
                {"Jollibe", "Mcdonalds", "15"}
        };

        table = new JTable(data, columnNames);
        table.setColumnSelectionAllowed(true);
        table.setRowSelectionAllowed(true);
        table.setAutoCreateRowSorter(true);

        scroll = new JScrollPane(table);
        scroll.setBounds(0, 200, 900, 150);

        searchField.setBounds(10, 100, 150, 20);
        searchField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                matchFirstRow();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        panel.add(searchField, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        getContentPane().add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Inventory Window");
        setSize(900, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void matchFirstRow() {
        table.clearSelection();

        @NotNull final String value = searchField.getText().toLowerCase();

        for (int row = 0; row <= table.getRowCount() - 1; row++) {
            for (int col = 0; col <= table.getColumnCount() - 1; col++) {

                @NotNull final String cellContent = table.getValueAt(row, col).toString().toLowerCase();

                if (cellContent.contains(value)) {

                    // this will automatically set the view of the scroll in the location of the value
                    table.scrollRectToVisible(table.getCellRect(row, 0, true));

                    // this will automatically set the focus of the searched/selected row/value
                    table.setRowSelectionInterval(row, row);

                    for (int i = 0; i <= table.getColumnCount() - 1; i++) {

                        table.getColumnModel().getColumn(i).setCellRenderer(new HighlightRenderer());
                    }
                }
            }
        }
    }
}
