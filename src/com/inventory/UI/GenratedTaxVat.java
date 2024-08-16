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
import com.inventory.cell.TableActionCellViewEditor;
import com.inventory.cell.TableActionCellViewEditorTax;
import com.inventory.cell.TableActionCellViewRender;
import com.inventory.cell.TableActionCellViewRenderTax;
import com.inventory.cell.TableActionEvent;
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
import javax.swing.table.DefaultTableModel;
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
public class GenratedTaxVat extends javax.swing.JFrame {

    /**
     * Creates new form TaxOrVat
     */
    
    
   TableActionEvent event;
   
    public GenratedTaxVat() {
        initComponents();
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/com/inventory/UI/Icons/genrated_reports.png")).getImage());
        this.setExtendedState(JFrame. MAXIMIZED_BOTH);
         // Set the selection mode to single selection
         progressCircle.setVisible(false);
         ButtonGenrateReport.setVisible(true);
        ButtonGenrateReport.setEnabled(false);
        
        event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                System.out.println("Edit row : " + row);
            }
            
            @Override
            public void onDelete(int row) {
                if (genratedTaxVatTable.isEditing()) {
                    genratedTaxVatTable.getCellEditor().stopCellEditing();
                }
                DefaultTableModel model = (DefaultTableModel) genratedTaxVatTable.getModel();
                //model.removeRow(row);
                String PurchaseIDS = String.valueOf(model.getValueAt(row, 2));
                loadpurchaseDataSet(PurchaseIDS);
                ButtonGenrateReport.setEnabled(true);
            }

            @Override
            public void onView(int row) {
                System.out.println("View row : " + row);
            }
        };
        
        loadDataSet();
        loadpurchaseDataSet("");
        
        genratedTaxVatTable.getSelectionModel().addListSelectionListener((e) -> {
            if(e.getValueIsAdjusting())
                    return;
            System.out.println("Selecstion");
            
        });
        
    }

    
     // Method to load data into table
    private void loadDataSet() {
        try {
            PaymentsDAO paymentsDAO = new PaymentsDAO();
            genratedTaxVatTable.setModel(paymentsDAO.buildGenratedTaxVatTableModel(paymentsDAO.getTaxVatData()));
            for(int i=0;i<genratedTaxVatTable.getColumnCount();i++)
            {
                if(i==0)
                {
                     //purchaseTable.getColumnModel().getColumn(i).setPreferredWidth(-10);
                     
                     
                     genratedTaxVatTable.getColumnModel().getColumn(i).setMinWidth(0);
                     genratedTaxVatTable.getColumnModel().getColumn(i).setMaxWidth(0);
                     genratedTaxVatTable.getColumnModel().getColumn(i).setResizable(false);
                }
                else if(i==genratedTaxVatTable.getColumnCount()-1)
                {
                    genratedTaxVatTable.getColumnModel().getColumn(i).setCellRenderer(new TableActionCellViewRender());
                    genratedTaxVatTable.getColumnModel().getColumn(i).setCellEditor(new TableActionCellViewEditor(event));    
                }
                else
                {
                    genratedTaxVatTable.getColumnModel().getColumn(i).setCellRenderer(Supporter.renderer_center);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    
     // Method to load data into table
    private void loadpurchaseDataSet(String purchaseIDS) {
        try {
            ProductDAO productDAO = new ProductDAO();
            purchaseTable.setModel(productDAO.buildTableModelPurchase(productDAO.getPurchaseInfoForGenratedVAT(purchaseIDS)));
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
        progressCircle = new com.inventory.spinner.SpinnerProgress();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        genratedTaxVatTable = new javax.swing.JTable();

        setTitle("Vat/Tax Report Selection");

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
        purchaseTable.setRowSelectionAllowed(false);
        jScrollPane1.setViewportView(purchaseTable);

        ButtonGenrateReport.setText("Genrate Report");
        ButtonGenrateReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonGenrateReportActionPerformed(evt);
            }
        });

        progressCircle.setIndeterminate(true);

        jLabel1.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jLabel1.setText("Genrated Tax/Vat Reports");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel2.setText(" Purchase Detials, You Genrated in Vat/Tax Report");

        genratedTaxVatTable.setModel(new javax.swing.table.DefaultTableModel(
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
        genratedTaxVatTable.setRowHeight(30);
        genratedTaxVatTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                genratedTaxVatTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(genratedTaxVatTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 309, Short.MAX_VALUE)
                        .addComponent(ButtonGenrateReport, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(progressCircle, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(progressCircle, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(ButtonGenrateReport, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonGenrateReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonGenrateReportActionPerformed
        // TODO add your handling code here:
        
        int opt = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure do you want to regenrate tax report, And make a pdf for it?",
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
                         para.put("isTaxDone", 1);
                         //$X{NOTIN, purchaseinfo.purchaseID, not_into}
                         JasperPrint jp = JasperFillManager.fillReport(jr, para,conn);

                         JasperViewer.viewReport(jp, false);
                         conn.close();
                         String stringWithoutLastCharacter = "";
                         if(unselectedIDS.length()>0)
                         {
                             stringWithoutLastCharacter = unselectedIDS.substring(0, unselectedIDS.length() - 1);
                         }
//                         PaymentsDAO paymentsDAO = new PaymentsDAO();
//                         paymentsDAO.setVatCompleteData(stringWithoutLastCharacter);
//                         PaymentsDAO paymentsDAO1 = new PaymentsDAO();
//                         paymentsDAO1.addTaxVatData(stringWithoutLastCharacter);
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

    private void genratedTaxVatTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_genratedTaxVatTableMouseClicked
        int row = genratedTaxVatTable.getSelectedRow();
        int col = genratedTaxVatTable.getColumnCount();

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

    }//GEN-LAST:event_genratedTaxVatTableMouseClicked

    private static boolean contains(int[] array, int value) {
        for (int num : array) {
            if (num == value) {
                return true;
            }
        }
        return false;
    }
    
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
    private javax.swing.JTable genratedTaxVatTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private com.inventory.spinner.SpinnerProgress progressCircle;
    private javax.swing.JTable purchaseTable;
    // End of variables declaration//GEN-END:variables
}
