package com.inventory.cell;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 *
 * @author RAVEN
 */
public class TableActionCellViewEditor extends DefaultCellEditor {

    private TableActionEvent event;

    public TableActionCellViewEditor(TableActionEvent event) {
        super(new JCheckBox());
        this.event = event;
    }

    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int row, int column) {
        PanelActionView action = new PanelActionView();
        action.initEvent(event, row);
        action.setBackground(jtable.getSelectionBackground());
        return action;
    }
}
