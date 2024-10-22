/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.UI;

import com.inventory.DAO.SupplierDAO;
import com.inventory.DTO.SupplierDTO;
import com.inventory.Utill.Supporter;
import java.awt.Component;
import java.awt.Event;
import java.awt.event.KeyEvent;

import javax.swing.*;
import java.sql.SQLException;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author asjad
 */
public class SupplierPage_new extends javax.swing.JPanel {

    /**
     * Creates new form SupplierPage
     */
    public SupplierPage_new() {
        initComponents();
        jScrollPane1.setViewportView(suppTable);
//        suppTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//        suppTable.setPreferredSize(null);
// Set the selection mode to single selection
        suppTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        loadDataSet();
        supplier_id_text.setVisible(false);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        suppTable = new javax.swing.JTable();
        searchText = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        supplier_id_text = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        vatPercentageText = new javax.swing.JTextField();
        codeText = new javax.swing.JTextField();
        nameText = new javax.swing.JTextField();
        phoneText = new javax.swing.JTextField();
        locationText = new javax.swing.JTextField();
        tpinNoText = new javax.swing.JTextField();
        addButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        TaxCateogryText = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        TaxDescriptionText = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        TfBankName = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        TfAccountName = new javax.swing.JTextField();
        jLabel_AccountNO = new javax.swing.JLabel();
        TfAccountNO = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        TfBranchName = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        TfShortCode = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        TfNrcCode = new javax.swing.JTextField();

        jLabel1.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jLabel1.setText("SUPPLIERS");

        suppTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        suppTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        suppTable.setGridColor(new java.awt.Color(153, 153, 153));
        suppTable.setRowHeight(30);
        suppTable.setShowGrid(false);
        suppTable.getTableHeader().setReorderingAllowed(false);
        suppTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                suppTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(suppTable);

        searchText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchTextKeyReleased(evt);
            }
        });

        jLabel8.setText("Search:");

        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setToolTipText("");
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Enter Supplier Details"));
        jPanel1.setToolTipText("");

        jLabel2.setText("Supplier Code *");

        jLabel3.setText("Full Name *");

        jLabel4.setText("Location *");

        jLabel5.setText("Contact NO ");

        jLabel6.setText("TPIN No *");

        jLabel7.setText("VAT % *");

        nameText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameTextActionPerformed(evt);
            }
        });

        addButton.setText("Add");
        addButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        editButton.setText("Edit");
        editButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        clearButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        clearButton.setText("CLEAR");
        clearButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        jLabel9.setText("TAX No*");

        TaxCateogryText.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        TaxCateogryText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TaxCateogryTextKeyTyped(evt);
            }
        });

        jLabel10.setText("TAX Description:");

        jLabel11.setText("Bank Name*");

        jLabel12.setText("Account Name*");

        jLabel_AccountNO.setText("Account NO*");

        TfAccountNO.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                TfAccountNOFocusLost(evt);
            }
        });
        TfAccountNO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TfAccountNOKeyTyped(evt);
            }
        });

        jLabel14.setText("Branch Name*");

        jLabel15.setText("Short Code*");

        TfShortCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TfShortCodeActionPerformed(evt);
            }
        });

        jLabel16.setText("N.R.C");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(clearButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(deleteButton))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(vatPercentageText, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(codeText, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(TfNrcCode, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(TfShortCode, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(TfBranchName, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel_AccountNO, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(TfAccountNO, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(TfAccountName, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(TfBankName, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(TaxDescriptionText, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(TaxCateogryText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(tpinNoText, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(phoneText, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(locationText, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(nameText, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(codeText, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameText, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(locationText, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(phoneText, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tpinNoText, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vatPercentageText, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TaxCateogryText, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TaxDescriptionText, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TfBankName, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TfAccountName, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_AccountNO, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TfAccountNO, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TfBranchName, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TfShortCode, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TfNrcCode, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteButton)
                    .addComponent(editButton)
                    .addComponent(addButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(clearButton)
                .addGap(8, 8, 8))
        );

        jScrollPane2.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(178, 178, 178)
                        .addComponent(supplier_id_text)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(supplier_id_text))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void suppTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_suppTableMouseClicked
        int row = suppTable.getSelectedRow();
        int col = suppTable.getColumnCount();
        Object[] data = new Object[col];

        for (int i=0; i<col; i++)
            data[i] = suppTable.getValueAt(row, i);
        
        supplier_id_text.setText(String.valueOf(data[0]));
        codeText.setText((String) data[1]);
        nameText.setText((String) data[2]);
        locationText.setText((String) data[3]);
        phoneText.setText((String) data[4]);
        tpinNoText.setText((String) data[5]);
        vatPercentageText.setText(String.valueOf(data[6]));
        TaxCateogryText.setText(String.valueOf(data[7]));
        TaxDescriptionText.setText((String) data[8]);
        TfBankName.setText(data[9].toString());
        TfAccountName.setText(data[10].toString());
        TfAccountNO.setText(data[11].toString());
        TfBranchName.setText(data[12].toString());
        TfShortCode.setText(data[13].toString());
        if(data[14] != null)
            TfNrcCode.setText(data[14].toString());
    }//GEN-LAST:event_suppTableMouseClicked

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        if (codeText.getText().equals("") || nameText.getText().equals("")
                || locationText.getText().equals("")) //|| phoneText.getText().equals(""))
            JOptionPane.showMessageDialog(this, "Please enter all the required details.");
        else {
            SupplierDTO supplierDTO = new SupplierDTO();
            supplierDTO.setSuppCode(codeText.getText());
            supplierDTO.setFullName(nameText.getText());
            supplierDTO.setLocation(locationText.getText());
            supplierDTO.setPhone(phoneText.getText());
            supplierDTO.setTpin_no(tpinNoText.getText());
            supplierDTO.setVat_percent(Double.parseDouble(vatPercentageText.getText()));
            supplierDTO.setTax_category_no(TaxCateogryText.getText());
            supplierDTO.setTax_category_name(TaxDescriptionText.getText());
            supplierDTO.setVat_no("");
            supplierDTO.setBank_name(TfBankName.getText());
            supplierDTO.setAccount_name(TfAccountName.getText());
            supplierDTO.setAccount_number(TfAccountNO.getText());
            supplierDTO.setShort_code(TfShortCode.getText());
            supplierDTO.setBranch_name(TfBranchName.getText());
            supplierDTO.setNrc(TfNrcCode.getText());
            
            new SupplierDAO().addSupplierDAO(supplierDTO);
            loadDataSet();
        }
    }//GEN-LAST:event_addButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        if (suppTable.getSelectedRow()<0)
            JOptionPane.showMessageDialog(this, "Please select an entry to edit from the table.");
        else {
            if (codeText.getText().equals("") || nameText.getText().equals("")
                    || locationText.getText().equals("") || phoneText.getText().equals(""))
                JOptionPane.showMessageDialog(this, "Please enter all the required details.");
            else {
                SupplierDTO supplierDTO = new SupplierDTO();
                supplierDTO.setSuppID(Integer.parseInt(supplier_id_text.getText()));
                supplierDTO.setSuppCode(codeText.getText());
                supplierDTO.setFullName(nameText.getText());
                supplierDTO.setLocation(locationText.getText());
                supplierDTO.setPhone(phoneText.getText());
                supplierDTO.setTpin_no(tpinNoText.getText());
                supplierDTO.setVat_percent(Double.parseDouble(vatPercentageText.getText()));
                supplierDTO.setTax_category_no(TaxCateogryText.getText());
                supplierDTO.setTax_category_name(TaxDescriptionText.getText());
                supplierDTO.setVat_no("");
                
                supplierDTO.setBank_name(TfBankName.getText());
                supplierDTO.setAccount_name(TfAccountName.getText());
                supplierDTO.setAccount_number(TfAccountNO.getText());
                supplierDTO.setBranch_name(TfBranchName.getText());
                supplierDTO.setShort_code(TfShortCode.getText());
                supplierDTO.setNrc(TfNrcCode.getText());
                
                new SupplierDAO().editSupplierDAO(supplierDTO);
                loadDataSet();
            }
        }
    }//GEN-LAST:event_editButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        if (suppTable.getSelectedRow()<0)
            JOptionPane.showMessageDialog(this, "Please select an entry from the table to be deleted.");
        else {
            int opt = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this supplier?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if (opt==JOptionPane.YES_OPTION) {
                new SupplierDAO().deleteSupplierDAO(suppTable.getValueAt(suppTable.getSelectedRow(),0).toString());
                loadDataSet();
            }
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        codeText.setText("");
        nameText.setText("");
        locationText.setText("");
        phoneText.setText("");
        tpinNoText.setText("");
        vatPercentageText.setText("");
        TaxCateogryText.setText("");
        TaxDescriptionText.setText("");
        searchText.setText("");
        TfBankName.setText("");
        TfAccountName.setText("");
        TfAccountNO.setText("");
        TfBranchName.setText("");
        TfShortCode.setText("");
        TfNrcCode.setText("");
    }//GEN-LAST:event_clearButtonActionPerformed

    private void searchTextKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTextKeyReleased
        String text = searchText.getText();
        loadSearchData(text);
    }//GEN-LAST:event_searchTextKeyReleased

    private void nameTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameTextActionPerformed

    private void TfShortCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TfShortCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TfShortCodeActionPerformed

    private void TaxCateogryTextKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TaxCateogryTextKeyTyped
        // TODO add your handling code here:
        char c  = evt.getKeyChar();
        
        if(!Character.isDigit(c) || TaxCateogryText.getText().length() >= 2)
        {
            evt.consume();
        }
    }//GEN-LAST:event_TaxCateogryTextKeyTyped

    private void TfAccountNOKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TfAccountNOKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if(!(Character.isDigit(c)) || TfAccountNO.getText().length() > 12)
        {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_TfAccountNOKeyTyped

    private void TfAccountNOFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TfAccountNOFocusLost
        // TODO add your handling code here:
        if(TfAccountNO.getText().length() < 1)
        {
            getToolkit().beep();
            JOptionPane.showMessageDialog(TaxCateogryText, "Enter Valid Bank Account Number");
            TfAccountNO.requestFocus();
        }
    }//GEN-LAST:event_TfAccountNOFocusLost


    // Method to load data into table
    public void loadDataSet() {
        try {
            SupplierDAO supplierDAO = new SupplierDAO();
            suppTable.setModel(supplierDAO.buildTableModel(supplierDAO.getQueryResult()));
            
            for(int i=0;i<suppTable.getColumnCount();i++)
            {
                if(i==0 )
                {
                     //purchaseTable.getColumnModel().getColumn(i).setPreferredWidth(-10);
                     
                     
                     suppTable.getColumnModel().getColumn(i).setMinWidth(0);
                     suppTable.getColumnModel().getColumn(i).setMaxWidth(0);
                     suppTable.getColumnModel().getColumn(i).setResizable(false);
                }
                else
                {
                    suppTable.getColumnModel().getColumn(i).setCellRenderer(Supporter.renderer_center);
                }
            }
            
//            suppTable.getColumnModel().getColumn(0).setPreferredWidth(5);
//            suppTable.getColumnModel().getColumn(1).setPreferredWidth(7);
//            suppTable.getColumnModel().getColumn(2).setPreferredWidth(12);
//            suppTable.getColumnModel().getColumn(3).setPreferredWidth(8);
//            suppTable.getColumnModel().getColumn(4).setPreferredWidth(8);
//            suppTable.getColumnModel().getColumn(5).setPreferredWidth(8);
//            suppTable.getColumnModel().getColumn(6).setPreferredWidth(8);
//            suppTable.getColumnModel().getColumn(7).setPreferredWidth(8);
//            suppTable.getColumnModel().getColumn(8).setPreferredWidth(8);
//            suppTable.getColumnModel().getColumn(9).setPreferredWidth(8);
//            suppTable.getColumnModel().getColumn(10).setPreferredWidth(8);
//            suppTable.getColumnModel().getColumn(11).setPreferredWidth(8);
//            suppTable.getColumnModel().getColumn(12).setPreferredWidth(8);
//            suppTable.getColumnModel().getColumn(13).setPreferredWidth(8);
//            suppTable.getColumnModel().getColumn(14).setPreferredWidth(8);
            
            
                    
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to display search result in table
    public void loadSearchData(String text) {
        try {
            SupplierDAO supplierDAO = new SupplierDAO();
            suppTable.setModel(supplierDAO.buildTableModel(supplierDAO.getSearchResult(text)));
            for(int i=0;i<suppTable.getColumnCount();i++)
            {
                if(i==0 )
                {
                     //purchaseTable.getColumnModel().getColumn(i).setPreferredWidth(-10);
                     
                     
                     suppTable.getColumnModel().getColumn(i).setMinWidth(0);
                     suppTable.getColumnModel().getColumn(i).setMaxWidth(0);
                     suppTable.getColumnModel().getColumn(i).setResizable(false);
                }
                else
                {
                    suppTable.getColumnModel().getColumn(i).setCellRenderer(Supporter.renderer_center);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField TaxCateogryText;
    private javax.swing.JTextField TaxDescriptionText;
    private javax.swing.JTextField TfAccountNO;
    private javax.swing.JTextField TfAccountName;
    private javax.swing.JTextField TfBankName;
    private javax.swing.JTextField TfBranchName;
    private javax.swing.JTextField TfNrcCode;
    private javax.swing.JTextField TfShortCode;
    private javax.swing.JButton addButton;
    private javax.swing.JButton clearButton;
    private javax.swing.JTextField codeText;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton editButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_AccountNO;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField locationText;
    private javax.swing.JTextField nameText;
    private javax.swing.JTextField phoneText;
    private javax.swing.JTextField searchText;
    private javax.swing.JTable suppTable;
    private javax.swing.JLabel supplier_id_text;
    private javax.swing.JTextField tpinNoText;
    private javax.swing.JTextField vatPercentageText;
    // End of variables declaration//GEN-END:variables
}
