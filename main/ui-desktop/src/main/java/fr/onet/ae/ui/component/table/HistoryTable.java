package fr.onet.ae.ui.component.table;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Jean-Pierre PRUNARET
 * @since 10/07/2014
 */
public class HistoryTable extends JPanel {

    JTable table;

    public HistoryTable(final MessageTableModel dm, TableColumnModel cm, ListSelectionModel sm) {
        table = new JTable(dm, cm, sm);
        setLayout(new BorderLayout());

        final JButton clear = new JButton("Effacer model");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dm.purge();
            }
        });

        add(clear, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public HistoryTable() {
        this(null, null, null);
    }

    public HistoryTable(MessageTableModel dm) {
        this(dm, null, null);
    }

    public void setDefaultRenderer(Class<?> columnClass, TableCellRenderer renderer) {
        table.setDefaultRenderer(columnClass, renderer);
    }

    public void setAutoCreateRowSorter(boolean autoCreateRowSorter) {
        table.setAutoCreateRowSorter(autoCreateRowSorter);
    }
}


