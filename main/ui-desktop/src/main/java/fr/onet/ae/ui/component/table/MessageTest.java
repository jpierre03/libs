package fr.onet.ae.ui.component.table;

import fr.onet.ae.amqp.consumer.AmqpReceivedMessageImpl;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * @author Jean-Pierre PRUNARET
 * @since 01/07/2014
 */
public class MessageTest {

    public MessageTest() {
    }

    public static void main(String... args) {
        JFrame frame = new JFrame();
        frame.setTitle("Notes des élèves");
        frame.setPreferredSize(new Dimension(500, 400));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final MessageTableModel messageTableModel = new MessageTableModel();

        {
            messageTableModel.add(new AmqpReceivedMessageImpl("key1", "b1"));
            messageTableModel.add(new AmqpReceivedMessageImpl("key2", "b2"));
            messageTableModel.add(new AmqpReceivedMessageImpl("key1", "b3"));
            messageTableModel.add(new AmqpReceivedMessageImpl("key2", "b4"));
            messageTableModel.add(new AmqpReceivedMessageImpl("key1", "b5"));
        }

        TableModel model = messageTableModel;
        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        frame.getContentPane().add(new JScrollPane(table));
        frame.pack();

        frame.setVisible(true);
    }
}
