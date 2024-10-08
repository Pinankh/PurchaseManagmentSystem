/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.inventory.UI;

import com.inventory.DAO.PaymentsDAO;
import com.inventory.DAO.ProductDAO;
import com.inventory.Database.DatabaseConnection;
import com.inventory.UI.datepicker.EventDateChooser;
import com.inventory.Utill.Supporter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author pinal
 */
public class TaxOrVat extends javax.swing.JFrame {

    /**
     * Creates new form TaxOrVat
     */
    
    private boolean  isTaxDone = false;
   
    public TaxOrVat() {
        initComponents();
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/com/inventory/UI/Icons/ic_bakery.png")).getImage());
        this.setExtendedState(JFrame. MAXIMIZED_BOTH);
         // Set the selection mode to single selection
         progressCircle.setVisible(false);
         ButtonGenrateReport.setVisible(true);
        purchaseTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        PaymentsDAO paymentsDAO = new PaymentsDAO();
        if(paymentsDAO.checkVatTaxReport())
        {
            isTaxDone = true;
        }
        else
        {
            isTaxDone = false;
            loadDataSet();
        }
        
        
    }

    
     // Method to load data into table
    private void loadDataSet() {
        try {
            ProductDAO productDAO = new ProductDAO();
            purchaseTable.setModel(productDAO.buildTableModelPurchase(productDAO.getPurchaseInfoForVAT()));
            for(int i=0;i<purchaseTable.getColumnCount();i++)
            {
                if(i==0 || i==1)
                {
                     //purchaseTable.getColumnModel().getColumn(i).setPreferredWidth(-10);
                     
                     
                     purchaseTable.getColumnModel().getColumn(i).setMinWidth(0);
                     purchaseTable.getColumnModel().getColumn(i).setMaxWidth(0);
                     purchaseTable.getColumnModel().getColumn(i).setResizable(false);
                }
                else if(i==2 || i==4)
                {
                    purchaseTable.getColumnModel().getColumn(i).setCellRenderer(Supporter.renderer_left);
                }
                else if(i==7 || i==6 || i==purchaseTable.getColumnCount()-2)
                {
                    purchaseTable.getColumnModel().getColumn(i).setCellRenderer(Supporter.renderer_right_bold);
                }
//                else if(i==purchaseTable.getColumnCount()-1)
//                {
//                    purchaseTable.getColumnModel().getColumn(i).setCellRenderer(new TableActionCellRender());
//                    purchaseTable.getColumnModel().getColumn(i).setCellEditor(new TableActionCellEditor(event));    
//                }
                else
                {
                    purchaseTable.getColumnModel().getColumn(i).setCellRenderer(Supporter.renderer_center);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        purchaseTable = new javax.swing.JTable();
        ButtonGenrateReport = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        progressCircle = new com.inventory.spinner.SpinnerProgress();

        setTitle("Vat/Tax Report Selection");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        purchaseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        purchaseTable.setRowHeight(30);
        purchaseTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                purchaseTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(purchaseTable);

        ButtonGenrateReport.setText("Genrate Report");
        ButtonGenrateReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonGenrateReportActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Please select carry forward purchase. You can select multiple.");

        progressCircle.setIndeterminate(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 185, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonGenrateReport, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(progressCircle, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ButtonGenrateReport, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addComponent(progressCircle, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void purchaseTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_purchaseTableMouseClicked
        int row = purchaseTable.getSelectedRow();
        int col = purchaseTable.getColumnCount();

        // commented by pinalkumar
        //        Object[] data = new Object[col];
        //        for (int i=0; i<col; i++)
        //            data[i] = purchaseTable.getValueAt(row, i);
        //        try
        //        {
            //            //Commented by pinalkumar
            //            quantity = Integer.parseInt(data[3].toString());
            //            prodCode = data[1].toString();
            //        }catch(Exception e){
            //            e.printStackTrace();
            //        }

    }//GEN-LAST:event_purchaseTableMouseClicked

    private void ButtonGenrateReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonGenrateReportActionPerformed
        // TODO add your handling code here:
        
        int opt = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure do you want to pay tax, And make a pdf for it?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if(opt==JOptionPane.YES_OPTION) {
                
                    progressCircle.setVisible(true);
                    ButtonGenrateReport.setVisible(false);
                    List<Integer> slectedIDS = new ArrayList<>();
                    List<Integer> unSlectedIDS = new ArrayList<>();
//                     int col = purchaseTable.getColumnCount();
//
//                     int lenght = purchaseTable.getSelectedRows().length;
//                     
//                     for(int i=0;i<lenght;i++)
//                     {
//                         int row = purchaseTable.getSelectedRows()[i];
//
//                         try
//                         {
//                             //Commented by pinalkumar
//                             int id = Integer.parseInt(String.valueOf(purchaseTable.getValueAt(row, 0)));
//
//
//                             //slectedIDS.add(id);
//
//                         }catch(Exception e){
//                             e.printStackTrace();
//                         }
//                     }

                int[] selectedRows = purchaseTable.getSelectedRows();
                int totalRows = purchaseTable.getRowCount();
                int[] unselectedRows = new int[totalRows - selectedRows.length];
                int currentIndex = 0;
                String unselectedIDS = "";

                for (int i = 0; i < totalRows; i++) {
                    if (!contains(selectedRows, i)) {
                        unselectedRows[currentIndex++] = i;
                        
                         try
                         {
                             //Commented by pinalkumar
                             int id = Integer.parseInt(String.valueOf(purchaseTable.getValueAt(i, 0)));


                             unSlectedIDS.add(id);
                             unselectedIDS += String.valueOf(id)+",";
                         }catch(Exception e){
                             e.printStackTrace();
                         }
                    }
                    else
                    {
                        
                         try
                         {
                             //Commented by pinalkumar
                             int id = Integer.parseInt(String.valueOf(purchaseTable.getValueAt(i, 0)));


                             slectedIDS.add(id);

                         }catch(Exception e){
                             e.printStackTrace();
                         }
                    }
                }

                // Now, unselectedRows array contains the indices of unselected rows
                System.out.println("Unselected Rows: " + Arrays.toString(unSlectedIDS.toArray()));
                System.out.println("selected Rows: " + Arrays.toString(slectedIDS.toArray()));
                
                     Calendar aCalendar = Calendar.getInstance();
                     aCalendar.add(Calendar.MONTH, -1);
                     aCalendar.set(Calendar.DATE, 1);
                     Date firstDateOfPreviousMonth = aCalendar.getTime();
                     aCalendar.set(Calendar.DATE,     aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                     Date lastDateOfPreviousMonth = aCalendar.getTime();
                     String pattern = "dd MMMM yyyy";
                     SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                     String fromdate = simpleDateFormat.format(firstDateOfPreviousMonth);
                     String toDate = simpleDateFormat.format(lastDateOfPreviousMonth);
                     System.out.println(fromdate +" to "+toDate);
                      try
                     {

                         Connection conn = DriverManager.getConnection(DatabaseConnection.DB_URL);
                         String reportPath = "D:\\StarBakery\\reports\\TaxOrVatReport.jrxml";
                         JasperReport jr = JasperCompileManager.compileReport(reportPath);
                         Map<String,Object> para = new HashMap<>();
                         para.put("not_into", unSlectedIDS);
                         para.put("fromDate", fromdate);
                         para.put("toDate", toDate);
                         para.put("isTaxDone", 0);
                         //$X{NOTIN, purchaseinfo.purchaseID, not_into}
                         JasperPrint jp = JasperFillManager.fillReport(jr, para,conn);

                         JasperViewer.viewReport(jp, false);
                         conn.close();
                         String stringWithoutLastCharacter = "";
                         if(unselectedIDS.length()>0)
                         {
                             stringWithoutLastCharacter = unselectedIDS.substring(0, unselectedIDS.length() - 1);
                         }
                         PaymentsDAO paymentsDAO = new PaymentsDAO();
                         paymentsDAO.setVatCompleteData(stringWithoutLastCharacter);
                         PaymentsDAO paymentsDAO1 = new PaymentsDAO();
                         paymentsDAO1.addTaxVatData(stringWithoutLastCharacter);
                         System.out.println("TAX/VAT Successfully done!");
                         progressCircle.setVisible(false);
                         ButtonGenrateReport.setVisible(true);
                         this.dispose();
                     }catch(SQLException | JRException e)
                     {
                         progressCircle.setVisible(false);
                         ButtonGenrateReport.setVisible(true);
                         JOptionPane.showMessageDialog(null, e);

                     }
            }
    }//GEN-LAST:event_ButtonGenrateReportActionPerformed

    private static boolean contains(int[] array, int value) {
        for (int num : array) {
            if (num == value) {
                return true;
            }
        }
        return false;
    }
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        if(isTaxDone)
            JOptionPane.showMessageDialog(null, "Previous month Tax/Vat Report allready genrated. Please visit the genrated report page.");
        else
            JOptionPane.showMessageDialog(null, "If you want to carry forward some of your purchase for next month Tax/Vat, please selects from table. Selected Purchase entry not included in this current Tax/VAT report.");
    }//GEN-LAST:event_formWindowOpened

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(TaxOrVat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(TaxOrVat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(TaxOrVat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(TaxOrVat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new TaxOrVat().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonGenrateReport;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.inventory.spinner.SpinnerProgress progressCircle;
    private javax.swing.JTable purchaseTable;
    // End of variables declaration//GEN-END:variables
}
