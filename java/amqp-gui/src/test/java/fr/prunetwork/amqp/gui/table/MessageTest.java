package fr.prunetwork.amqp.gui.table;

import fr.prunetwork.amqp.message.SimpleMessage;
import org.jetbrains.annotations.NotNull;

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
        @NotNull JFrame frame = new JFrame();
        frame.setTitle("Notes des élèves");
        frame.setPreferredSize(new Dimension(500, 400));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        @NotNull final MessageTableModel messageTableModel = new MessageTableModel();

        {
            messageTableModel.add(new SimpleMessage("key1", "b1"));
            messageTableModel.add(new SimpleMessage("key2", "b2"));
            messageTableModel.add(new SimpleMessage("key1", "b3"));
            messageTableModel.add(new SimpleMessage("key2", "b4"));
            messageTableModel.add(new SimpleMessage("key1", "b5"));
        }

        @NotNull TableModel model = messageTableModel;
        @NotNull JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        frame.getContentPane().add(new JScrollPane(table));
        frame.pack();

        frame.setVisible(true);
    }
}
