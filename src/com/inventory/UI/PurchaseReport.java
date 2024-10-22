/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.UI;


import com.inventory.DAO.SupplierDAO;

import com.inventory.DTO.SupplierDTO;
import com.inventory.DTO.UserDTO;
import com.inventory.Database.DatabaseConnection;
import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author asjad
 */
public class PurchaseReport extends javax.swing.JFrame {

    UserDTO userDTO;
    LocalDateTime inTime;
   
    private int SELECTED_SUPPLIER_ID;
    
    /**
     * Creates new form LoginPage
     */

    // Constructor method
    public PurchaseReport() {
        initComponents();
        progressCircle.setVisible(false);
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/com/inventory/UI/Icons/purchase_report.png")).getImage());
        loadComboBox();
        
//        SELECTED_SUPPLIER_ID = suppCombo.getSelectedItem()==null?0:((SupplierDTO) suppCombo.getSelectedItem()).getSuppID();
//       
//       
//        supplierMultiSelection.addItemListener((ItemEvent e) -> {
//            // Get the selected item
//            SELECTED_SUPPLIER_ID = ((SupplierDTO) e.getItem()).getSuppID();
//            System.out.println("Selected IDS = "+SELECTED_SUPPLIER_ID);
//            
//        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fromDate = new com.inventory.UI.datepicker.DateChooser();
        toDate = new com.inventory.UI.datepicker.DateChooser();
        jLabel1 = new javax.swing.JLabel();
        genrateButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        progressCircle = new com.inventory.spinner.SpinnerProgress();
        fromDateText = new javax.swing.JTextField();
        toDateText = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        supplierMultiSelection = new com.inventory.cell.ComboBoxMultiSelection();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        fromDate.setForeground(new java.awt.Color(51, 51, 51));
        fromDate.setDateFormat("yyyy-MM-dd");
        fromDate.setTextRefernce(fromDateText);

        toDate.setForeground(new java.awt.Color(51, 51, 51));
        toDate.setDateFormat("yyyy-MM-dd");
        toDate.setTextRefernce(toDateText);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Purchase Report");
        setBackground(new java.awt.Color(102, 102, 102));
        setBounds(new java.awt.Rectangle(500, 100, 0, 0));
        setName("purchaseReportFrame"); // NOI18N
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("From-Date");

        genrateButton.setBackground(new java.awt.Color(0, 102, 204));
        genrateButton.setForeground(new java.awt.Color(255, 255, 255));
        genrateButton.setText("Genrate");
        genrateButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        genrateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genrateButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Poor Richard", 1, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("PURCHASE   REPORT");

        progressCircle.setIndeterminate(true);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("To-Date");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Select Suppliers");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 2, 10)); // NOI18N
        jLabel6.setText("Date Must be YYYY-MM-DD Formate");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 2, 10)); // NOI18N
        jLabel7.setText("If you select none than all suuplier is selected");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(178, 178, 178)
                .addComponent(progressCircle, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(genrateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(toDateText, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                            .addComponent(fromDateText)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(supplierMultiSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(147, 147, 147)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(16, 16, 16)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fromDateText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(toDateText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(supplierMultiSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(progressCircle, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(genrateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(59, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(207, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(193, 193, 193)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

     // Method to load and update combo box containing supplier names
    private void loadComboBox() {
        try {
            SupplierDAO supplierDAO = new SupplierDAO();
            SupplierDAO supplierDAO2 = new SupplierDAO();
            supplierMultiSelection.setModel(supplierDAO.setComboItemsCustome(supplierDAO.getQueryResult(),supplierDAO2.getQueryCountResult()));
            //suppCombo.setSelectedIndex(0);
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    private void genrateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genrateButtonActionPerformed
        
        try
        {
            List<Integer> slectedIDS = new ArrayList<>();
            int lenght = supplierMultiSelection.getSelectedItems().size();
            for(int i=0;i<lenght;i++)
            {
                slectedIDS.add(((SupplierDTO) supplierMultiSelection.getSelectedItems().get(i)).getSuppID());
            }
            SELECTED_SUPPLIER_ID = supplierMultiSelection.getSelectedItem()==null?0:((SupplierDTO) supplierMultiSelection.getSelectedItem()).getSuppID();
            
            Connection conn = DriverManager.getConnection(DatabaseConnection.DB_URL);
            String reportPath = "D:\\StarBakery\\reports\\PurchaseReport.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(reportPath);
            Map<String,Object> para = new HashMap<>();
            para.put("not_into", slectedIDS);
            para.put("fromDate", fromDateText.getText());
            para.put("toDate", toDateText.getText());
            para.put("fromDate2", fromDate.getSelectedDate().getDay()+"-"+fromDate.getSelectedDate().getMonth()+"-"+fromDate.getSelectedDate().getYear());
            para.put("toDate2", toDate.getSelectedDate().getDay()+"-"+toDate.getSelectedDate().getMonth()+"-"+toDate.getSelectedDate().getYear());
            //$X{NOTIN, purchaseinfo.purchaseID, not_into}
            JasperPrint jp = JasperFillManager.fillReport(jr, para,conn);
            
            JasperViewer.viewReport(jp, false);
            conn.close();
            progressCircle.setVisible(false);
            genrateButton.setVisible(true);
        }catch(SQLException | JRException e)
        {
            progressCircle.setVisible(false);
            genrateButton.setVisible(true);
            JOptionPane.showMessageDialog(null, e);

        }
        
    }//GEN-LAST:event_genrateButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    
//    // Driver Method
//    // **APPLICATION STARTS EXECUTION HERE**
//    public static void main(String[] args) {
//        
//        // setting UI theme and LookAndFeel of the application
//        try {
//            //javax.swing.UIManager.setLookAndFeel(new FlatMaterialDarkerIJTheme());
//            javax.swing.UIManager.setLookAndFeel(new FlatXcodeDarkIJTheme());
//            } catch (UnsupportedLookAndFeelException ex) {
//            Logger.getLogger(PurchaseReport.class.getName()).log(Level.SEVERE, null, ex);
//        } 
//       
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new PurchaseReport().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private com.inventory.UI.datepicker.DateChooser fromDate;
    private javax.swing.JTextField fromDateText;
    private javax.swing.JButton genrateButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private com.inventory.spinner.SpinnerProgress progressCircle;
    private com.inventory.cell.ComboBoxMultiSelection supplierMultiSelection;
    private com.inventory.UI.datepicker.DateChooser toDate;
    private javax.swing.JTextField toDateText;
    // End of variables declaration//GEN-END:variables
}
