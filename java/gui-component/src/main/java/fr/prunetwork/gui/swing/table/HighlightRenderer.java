package fr.prunetwork.gui.swing.table;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * http://stackoverflow.com/questions/3245135/search-in-jtable
 *
 * @author Jean-Pierre PRUNARET
 * @since 10/07/2014
 */
public class HighlightRenderer extends DefaultTableCellRenderer {

    @NotNull
    @Override
    public Component getTableCellRendererComponent(@NotNull JTable table,
                                                   @NotNull Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row,
                                                   int column) {

        // everything as usual
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // added behavior
        if (row == table.getSelectedRow()) {

            // this will customize that kind of border that will be use to highlight a row
            setBorder(BorderFactory.createMatteBorder(2, 1, 2, 1, Color.BLACK));
        }

        return this;
    }
}

