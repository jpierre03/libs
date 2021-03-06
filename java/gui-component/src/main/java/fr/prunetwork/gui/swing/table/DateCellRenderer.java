package fr.prunetwork.gui.swing.table;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Jean-Pierre PRUNARET
 * @since 02/07/2014
 */
public class DateCellRenderer extends DefaultTableCellRenderer {

    public DateCellRenderer() {
    }

    @NotNull
    @Override
    public Component getTableCellRendererComponent(@NotNull JTable table,
                                                   @NotNull Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row,
                                                   int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        @NotNull final Date date = (Date) value;
        @NotNull final Date now = new Date();

        if (now.getTime() - date.getTime() > 60 * 1000) {
            setForeground(Color.white);
            setBackground(Color.red);
        } else if (now.getTime() - date.getTime() > 45 * 1000) {
            setForeground(Color.white);
            setBackground(Color.black);
        } else if (now.getTime() - date.getTime() > 30 * 1000) {
            setForeground(Color.white);
            setBackground(Color.gray);
        } else if (now.getTime() - date.getTime() > 15 * 1000) {
            setForeground(Color.black);
            setBackground(Color.lightGray);
        } else {
            setForeground(Color.black);
            setBackground(Color.white);
        }

        @NotNull SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        setValue(simpleFormat.format(date));
        return this;
    }
}
