package fr.prunetwork.gui.table;

import fr.prunetwork.amqp.AmqpReceivedMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.table.AbstractTableModel;
import java.util.*;

/**
 * @author Jean-Pierre PRUNARET
 * @since 01/07/2014
 */
public class MessageTableModel extends AbstractTableModel {

    private final String[] entetes = {"Date", "Clef", "Corps"};
    private final List<AmqpReceivedMessage> messages = new ArrayList<>();

    @Override
    public int getColumnCount() {
        return entetes.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return entetes[columnIndex];
    }

    @Override
    public int getRowCount() {
        return messages.size();
    }

    @NotNull
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {

            case 0:
                return messages.get(rowIndex).getReceivedDate();

            case 1:
                return messages.get(rowIndex).getRoutingKey();

            case 2:
                return messages.get(rowIndex).getBody();

            default:
                throw new IllegalArgumentException();
        }
    }

    @NotNull
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {

            case 0:
                return Date.class;

            case 1:
                return String.class;

            default:
                return Object.class;
        }
    }

    @NotNull
    public Collection<AmqpReceivedMessage> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    public void add(@Nullable AmqpReceivedMessage message) {
        if (message == null) {
            throw new IllegalArgumentException("null message");
        }
        messages.add(message);
    }

    public void purge() {
        messages.clear();
        fireTableDataChanged();
    }
}
