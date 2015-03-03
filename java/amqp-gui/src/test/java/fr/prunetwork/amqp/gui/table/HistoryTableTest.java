package fr.prunetwork.amqp.gui.table;

import fr.prunetwork.amqp.message.SimpleMessage;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

/**
 * @author Jean-Pierre PRUNARET
 * @date 03/03/2015.
 */
public class HistoryTableTest extends JFrame {

    @NotNull
    private final JTextField searchField = new JTextField();
    @NotNull
    private final JPanel panel = new JPanel();
    @NotNull
    private final HistoryTable table;

    @NotNull
    private final JScrollPane scroll;

    private HistoryTableTest() {
        panel.setLayout(new BorderLayout());

        @NotNull final MessageTableModel model = new MessageTableModel();
        @NotNull final TableColumnModel columnModel = new DefaultTableColumnModel();
        @NotNull final ListSelectionModel selectionModel = new DefaultListSelectionModel();

        @NotNull final String[][] data = {
                {"Jhon", "Java", "23"},
                {"Stupid", "Stupido", "500"},
                {"Michael", "Winnie", "20"},
                {"Winnie", "Thepoor", "23"},
                {"Michael", "Winnie", "20"},
                {"Winnie", "Thepoor", "23"},
                {"Michael", "Winnie", "20"},
                {"Winnie", "Thepoor", "23"},
                {"Michael", "Winnie", "20"},
                {"Winnie", "Thepoor", "23"},
                {"Max", "Dumbass", "10"},
                {"Melanie", "Martin", "500"},
                {"Jollibe", "Mcdonalds", "15"}
        };

        for (String[] aData : data) {
            String key = aData[0];
            String value = aData[1] + "_" + aData[2];

            model.add(new SimpleMessage(key, value));
        }
        model.fireTableDataChanged();

        table = new HistoryTable(model);
        scroll = new JScrollPane(table);
        scroll.setBounds(0, 200, 900, 150);

        panel.add(table, BorderLayout.CENTER);

        getContentPane().add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Inventory Window");
        setSize(900, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String... args) {
        SwingUtilities.invokeLater(() -> new HistoryTableTest());

    }
}
