package fr.prunetwork;

import fr.prunetwork.amqp.message.SimpleMessage;
import fr.prunetwork.amqp.receiver.SimpleAmqpReceiver;
import fr.prunetwork.gui.table.DateCellRenderer;
import fr.prunetwork.gui.table.MessageTableModel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.Date;

/**
 * @author Jean-Pierre PRUNARET
 * @since 01/07/2014
 */
public class Main {

    public static void main(String... args) throws Exception {
        JFrame frame = new JFrame();
        frame.setTitle("Application");
        frame.setPreferredSize(new Dimension(600, 400));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final MessageTableModel messageTableModel = new MessageTableModel();

        TableModel model = messageTableModel;
        JTable table = new JTable(model);

        table.setDefaultRenderer(Date.class, new DateCellRenderer());

        table.setAutoCreateRowSorter(true);
        frame.getContentPane().add(new JScrollPane(table));
        frame.pack();

        SimpleAmqpReceiver receiver = new SimpleAmqpReceiver(
                "amqp://jpierre03:toto@bc.antalios.com",
                "ae.laveuse.v1",
                Arrays.asList("#")
        );
        receiver.configure();

        System.out.println("*******");

        frame.setVisible(true);

        System.out.println("*******");
        while (true) {
            final MessageTableModel m = messageTableModel;
            final SimpleMessage message = receiver.consume();

            if (message != null) {
                m.add(message);
                m.fireTableRowsInserted(m.getRowCount() - 1, m.getRowCount() - 1);
            }
        }
    }
}
