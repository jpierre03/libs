package fr.prunetwork.gui.swing.table;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.util.Vector;

/**
 * This table automatically pack columns.
 *
 * @author Jean-Pierre PRUNARET
 */
public class AutoPackableTable extends JTable {

    @Nullable
    private TablePacker packer = new TablePacker(TablePacker.ALL_ROWS, true);

    public AutoPackableTable() {
    }

    public AutoPackableTable(@NotNull final TableModel dm) {
        super(dm);
    }

    public AutoPackableTable(@NotNull final TableModel dm,
                             @NotNull final TableColumnModel cm) {
        super(dm, cm);
    }

    public AutoPackableTable(@NotNull final TableModel dm,
                             @NotNull final TableColumnModel cm,
                             @NotNull final ListSelectionModel sm) {
        super(dm, cm, sm);
    }

    public AutoPackableTable(int numRows, int numColumns) {
        super(numRows, numColumns);
    }

    public AutoPackableTable(@NotNull final Vector<?> rowData,
                             @NotNull final Vector<?> columnNames) {
        super(rowData, columnNames);
    }

    public AutoPackableTable(@NotNull final Object[][] rowData,
                             @NotNull final Object[] columnNames) {
        super(rowData, columnNames);
    }

    public void pack(int rowsIncluded) {
        packer = new TablePacker(rowsIncluded, true);
        if (isShowing()) {
            packer.pack(this);
            packer = null;
        }
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if (packer != null) {
            packer.pack(this);
            packer = null;
        }
    }
}
