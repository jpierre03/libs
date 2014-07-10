package fr.onet.ae.ui.component.table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {

                new JTableSearchAndHighlight();
            }
        });
    }

    private void initializeInventory() {

        panel = new JPanel();
        searchField = new JTextField();

        panel.setLayout(new BorderLayout());

        final String[] columnNames = {"Name", "Surname", "Age"};

        final Object[][] data = {
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
        searchField.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                final String value = searchField.getText().toLowerCase();

                for (int row = 0; row <= table.getRowCount() - 1; row++) {
                    for (int col = 0; col <= table.getColumnCount() - 1; col++) {

                        final String cellContent = table.getValueAt(row, col).toString().toLowerCase();

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
}
