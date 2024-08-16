package com.inventory.cell;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 *
 * @author RAVEN
 */
public class TableActionCellViewEditorTax extends DefaultCellEditor {

    private TableActionEvent event;

    public TableActionCellViewEditorTax(TableActionEvent event) {
        super(new JCheckBox());
        this.event = event;
    }

    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int row, int column) {
        PanelActionViewTax action = new PanelActionViewTax();
        action.initEvent(event, row);
        action.setBackground(jtable.getSelectionBackground());
        return action;
    }
}
