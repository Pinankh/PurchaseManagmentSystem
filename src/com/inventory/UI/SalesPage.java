/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.UI;

import com.inventory.DAO.ProductDAO;
import com.inventory.DAO.SalesMonthlyDAO;
import com.inventory.DTO.SalesMonthlyDTO;
import com.inventory.UI.datepicker.SelectedAction;
import com.inventory.UI.datepicker.SelectedDate;
import com.inventory.Utill.Supporter;
import java.awt.event.KeyEvent;

import javax.swing.*;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.DocumentFilter;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author asjad
 */
public class SalesPage extends javax.swing.JPanel {

    String username;
    Dashboard dashboard;
    int quantity;
    String prodCode;

    // Custom cell renderer for double values
    static class DoubleValueCellRenderer extends DefaultTableCellRenderer {
        private final DecimalFormat decimalFormat;

        public DoubleValueCellRenderer() {
            decimalFormat = new DecimalFormat("#0.00");
            setHorizontalAlignment(SwingConstants.RIGHT);
        }

        @Override
        protected void setValue(Object value) {
            // Format the double value before displaying
            if (value instanceof Double) {
                setText(decimalFormat.format(value));
            } else {
                super.setValue(value);
            }
        }
    }
    
    private static class DecimalFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            String newText = currentText.substring(0, offset) + string +
                    currentText.substring(offset, currentText.length());

            if (isValidDecimal(newText)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            String newText = currentText.substring(0, offset) + text +
                    currentText.substring(offset + length, currentText.length());

            if (isValidDecimal(newText)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        private boolean isValidDecimal(String text) {
            try {
                Double.parseDouble(text);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    };
    /**
     * Creates new form SalesPage
     */
    
    public SalesPage(String username, Dashboard dashboard) {
        initComponents();
        this.username = username;
        this.dashboard = dashboard;
        
        // Create a NumberFormatter with a DecimalFormat that allows decimal numbers
        NumberFormatter formatter = new NumberFormatter(new DecimalFormat("#0.00"));
        formatter.setValueClass(Double.class); // Set the value class to Double
        formatter.setMinimum(0.0); // Set minimum value (optional, adjust as needed)
        //formatter.setMaximum(100.0); // Set maximum value (optional, adjust as needed)
        formatter.setAllowsInvalid(false);
        // Set the formatter factory for the JFormattedTextField
        srLocalSalesTF.setFormatterFactory(new DefaultFormatterFactory(formatter));
        srLocalSalesTF.setColumns(10);
        
        // Set the formatter factory for the JFormattedTextField
        totalZROiutputTF.setFormatterFactory(new DefaultFormatterFactory(formatter));
        totalZROiutputTF.setColumns(10);
        
        // Set the formatter factory for the JFormattedTextField
        totalTaxNo18tf.setFormatterFactory(new DefaultFormatterFactory(formatter));
        totalTaxNo18tf.setColumns(10);
        
        //((AbstractDocument) jTextField1.getDocument()).setDocumentFilter(new DecimalFilter() );
        
        loadDataSet();
        
        
        
        clearButton.doClick();
        
        dateChooser1.addEventDateChooser((SelectedAction action, SelectedDate date) -> {
            System.out.println(date.getDay() + "-" + date.getMonth() + "-" + date.getYear());
            if (action.getAction() == SelectedAction.DAY_SELECTED) {
                dateChooser1.hidePopup();
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser1 = new com.inventory.UI.datepicker.DateChooser();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        sellPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        sellButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        srLocalSalesTF = new javax.swing.JFormattedTextField();
        totalZROiutputTF = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        totalTaxNo18tf = new javax.swing.JFormattedTextField();
        salesDate = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        salesTable = new javax.swing.JTable();

        dateChooser1.setForeground(new java.awt.Color(51, 51, 51));
        dateChooser1.setDateFormat("MMM-yyyy");
        dateChooser1.setTextRefernce(salesDate);

        jLabel1.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jLabel1.setText("SALES");

        sellPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Monthly Sales"));

        jLabel4.setText("Month-Year");

        jLabel5.setText("SR Local Sales");

        jLabel6.setText("Total ZR Output");

        sellButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        sellButton.setText("Add Monthly Sales");
        sellButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sellButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sellButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        clearButton.setText("Clear");
        clearButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        srLocalSalesTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        srLocalSalesTF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                srLocalSalesTFKeyTyped(evt);
            }
        });

        totalZROiutputTF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        totalZROiutputTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalZROiutputTF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                totalZROiutputTFKeyTyped(evt);
            }
        });

        jLabel7.setText("Total Tax No 18");

        totalTaxNo18tf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        totalTaxNo18tf.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalTaxNo18tf.setToolTipText("0.00");
        totalTaxNo18tf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                totalTaxNo18tfKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout sellPanelLayout = new javax.swing.GroupLayout(sellPanel);
        sellPanel.setLayout(sellPanelLayout);
        sellPanelLayout.setHorizontalGroup(
            sellPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sellPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sellPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sellButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(sellPanelLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalZROiutputTF))
                    .addGroup(sellPanelLayout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(srLocalSalesTF))
                    .addGroup(sellPanelLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(salesDate))
                    .addGroup(sellPanelLayout.createSequentialGroup()
                        .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearButton, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
                    .addGroup(sellPanelLayout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalTaxNo18tf)))
                .addContainerGap())
        );
        sellPanelLayout.setVerticalGroup(
            sellPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sellPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(sellPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(salesDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sellPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(srLocalSalesTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sellPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalZROiutputTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sellPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalTaxNo18tf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(sellButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(sellPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteButton)
                    .addComponent(clearButton))
                .addContainerGap(88, Short.MAX_VALUE))
        );

        salesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        salesTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                salesTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(salesTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sellPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(sellPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(70, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void salesTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salesTableMouseClicked
        int row = salesTable.getSelectedRow();
        int col = salesTable.getColumnCount();
        Object[] data = new Object[col];
        for (int i=0; i<col; i++)
            data[i] = salesTable.getValueAt(row, i);
        
        salesDate.setText(data[1].toString());
        srLocalSalesTF.setValue(data[2]);
        totalZROiutputTF.setValue(data[3]);
        totalTaxNo18tf.setValue(data[4]);
        
    }//GEN-LAST:event_salesTableMouseClicked

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
       
        
        
//        srLocalSalesTF.setText("0.00");
//        totalZROiutputTF.setText("0.00");
//        totalTaxNo18tf.setText("0.00");
       
        srLocalSalesTF.setValue(0.00);
        totalZROiutputTF.setValue(0.00);
        totalTaxNo18tf.setValue(0.00);
        
        loadDataSet();
    }//GEN-LAST:event_clearButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        if (salesTable.getSelectedRow()<0)
        JOptionPane.showMessageDialog(this, "Please select an entry from the table you wish to delete.");
        else {
            int opt = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this sale from the database?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
            if (opt == JOptionPane.YES_OPTION) {
                new ProductDAO().deleteSaleDAO(Integer.parseInt(
                    salesTable.getValueAt(salesTable.getSelectedRow(),0).toString()));
            new ProductDAO().editSoldStock(
                salesTable.getValueAt(salesTable.getSelectedRow(),1).toString(), quantity);
            loadDataSet();
        }
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void sellButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sellButtonActionPerformed
        if (srLocalSalesTF.getText().equals("") || totalTaxNo18tf.getText().equals("")
            || totalZROiutputTF.getText().equals(""))
        JOptionPane.showMessageDialog(this, "Please fill all the required fields.");
        else {
            try {
                SalesMonthlyDTO monthlyDTO = new SalesMonthlyDTO();
                monthlyDTO.setSalesDate(dateChooser1.getSelectedDate().toString());
                monthlyDTO.setSr_local_sales(Double.parseDouble(srLocalSalesTF.getText()));
                monthlyDTO.setTotal_otn18(Double.parseDouble(totalTaxNo18tf.getText()));
                monthlyDTO.setTotal_zro(Double.parseDouble(totalZROiutputTF.getText()));
                
                SalesMonthlyDAO monthlyDAO = new SalesMonthlyDAO();
                monthlyDAO.addSalesMonthlyDAO(monthlyDTO);
                loadDataSet();
                clearButton.doClick();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_sellButtonActionPerformed

    private void srLocalSalesTFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_srLocalSalesTFKeyTyped
        // TODO add your handling code here:
        
//        char c = evt.getKeyChar();
//        if(!(Character.isDigit(c)) && c!=KeyEvent.VK_PERIOD)
//        {
//            getToolkit().beep();
//            evt.consume();
//        }
//        else if(c==KeyEvent.VK_PERIOD && srLocalSalesTF.getText().contains("."))
//        {
//            getToolkit().beep();
//            evt.consume();
//        }
    }//GEN-LAST:event_srLocalSalesTFKeyTyped

    private void totalZROiutputTFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_totalZROiutputTFKeyTyped
        // TODO add your handling code here:
//        char c = evt.getKeyChar();
//        if(!(Character.isDigit(c)) && c!=KeyEvent.VK_PERIOD)
//        {
//            getToolkit().beep();
//            evt.consume();
//        }
//        else if(c==KeyEvent.VK_PERIOD && totalZROiutputTF.getText().contains("."))
//        {
//            getToolkit().beep();
//            evt.consume();
//        }
    }//GEN-LAST:event_totalZROiutputTFKeyTyped

    private void totalTaxNo18tfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_totalTaxNo18tfKeyTyped
        // TODO add your handling code here:
//        char c = evt.getKeyChar();
//        if(!(Character.isDigit(c)) && c!=KeyEvent.VK_PERIOD)
//        {
//            getToolkit().beep();
//            evt.consume();
//        }
//        else if(c==KeyEvent.VK_PERIOD && totalTaxNo18tf.getText().contains("."))
//        {
//            getToolkit().beep();
//            evt.consume();
//        }
    }//GEN-LAST:event_totalTaxNo18tfKeyTyped

    // Method to load data into table
    public void loadDataSet() {
        try {
            SalesMonthlyDAO monthlyDAO = new SalesMonthlyDAO();
            salesTable.setModel(monthlyDAO.buildMonthlySalesTableModel(monthlyDAO.getMonthlySalesInfo()));
            
            for(int i=0;i<salesTable.getColumnCount();i++)
            {
                if(i==0)
                {
                     //purchaseTable.getColumnModel().getColumn(i).setPreferredWidth(-10);
                     
                     
                     salesTable.getColumnModel().getColumn(i).setMinWidth(0);
                     salesTable.getColumnModel().getColumn(i).setMaxWidth(0);
                     salesTable.getColumnModel().getColumn(i).setResizable(false);
                }
                else if(i==1)
                {
                    salesTable.getColumnModel().getColumn(i).setCellRenderer(Supporter.renderer_center);
                }
                else
                {
                    salesTable.getColumnModel().getColumn(i).setCellRenderer(Supporter.renderer_right_bold);
                    salesTable.getColumnModel().getColumn(i).setCellRenderer(new DoubleValueCellRenderer());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to display search result in table
    public void loadSearchData(String text) {
        try {
            ProductDAO productDAO = new ProductDAO();
            salesTable.setModel(productDAO.buildTableModel(productDAO.getSalesSearch(text)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearButton;
    private com.inventory.UI.datepicker.DateChooser dateChooser1;
    private javax.swing.JButton deleteButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField salesDate;
    private javax.swing.JTable salesTable;
    private javax.swing.JButton sellButton;
    private javax.swing.JPanel sellPanel;
    private javax.swing.JFormattedTextField srLocalSalesTF;
    private javax.swing.JFormattedTextField totalTaxNo18tf;
    private javax.swing.JFormattedTextField totalZROiutputTF;
    // End of variables declaration//GEN-END:variables
}
