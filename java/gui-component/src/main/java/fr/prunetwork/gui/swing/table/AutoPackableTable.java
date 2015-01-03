package fr.prunetwork.gui.swing.table;

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

    private TablePacker packer = new TablePacker(TablePacker.ALL_ROWS, true);

    public AutoPackableTable() {
    }

    public AutoPackableTable(TableModel dm) {
        super(dm);
    }

    public AutoPackableTable(TableModel dm, TableColumnModel cm) {
        super(dm, cm);
    }

    public AutoPackableTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
        super(dm, cm, sm);
    }

    public AutoPackableTable(int numRows, int numColumns) {
        super(numRows, numColumns);
    }

    public AutoPackableTable(Vector<?> rowData, Vector<?> columnNames) {
        super(rowData, columnNames);
    }

    public AutoPackableTable(Object[][] rowData, Object[] columnNames) {
        super(rowData, columnNames);
    }

    public void pack(int rowsIncluded, boolean distributeExtraArea) {
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
