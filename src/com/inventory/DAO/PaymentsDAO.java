/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.DAO;

import com.inventory.DTO.SalesMonthlyDTO;
import com.inventory.DTO.SupplierDTO;
import com.inventory.Database.DatabaseConnection;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author asjad
 */

// Data Access Object for Products, Purchase, Stock and Sales
public class PaymentsDAO {

    Connection conn = null;
    PreparedStatement prepStatement = null;
    
    Statement statement = null;
    
    ResultSet resultSet = null;

    public PaymentsDAO() {
        try {
//            conn = new ConnectionFactory().getConn();
//            statement = conn.createStatement();
//            statement2 = conn.createStatement();
            
            conn = DriverManager.getConnection(DatabaseConnection.DB_URL);
//            conn = DriverManager.getConnection("jdbc:sqlite:sample.db");
            statement = conn.createStatement();
           
            statement.setQueryTimeout(50);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
       
    }

   public void addPaymentsData(int supplierID,double payAmount,String purchaseIDS) {
        
        
        try {
            
            //String query = "update purchaseinfo SET isPayed = 1 where purchaseID IN("+purchaseIDS+")";
                    
            //int rs = statement.executeUpdate(query);
            String query = "UPDATE purchaseinfo SET isPayed = 1 WHERE purchaseID IN ("+purchaseIDS+")";
            prepStatement = conn.prepareStatement(query);
//            prepStatement.setInt(1, 1);
//            prepStatement.setString(2, purchaseIDS);
            prepStatement.executeUpdate();
            prepStatement.close();
            
                String addquery = "INSERT INTO payments ( \"supplierID\", \"purchaseIDS\", \"totalcost\") VALUES(?,?,?)";
                prepStatement = conn.prepareStatement(addquery);
                prepStatement.setInt(1, supplierID);
                prepStatement.setString(2, purchaseIDS);
                prepStatement.setDouble(3, payAmount);
                prepStatement.executeUpdate();
                
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            closeResources();
        }
        
       
    }
   
   public boolean checkVatTaxReport()
   {
       Calendar aCalendar = Calendar.getInstance();
       aCalendar.add(Calendar.MONTH, -1);
       aCalendar.set(Calendar.DATE, 1);
       java.util.Date firstDateOfPreviousMonth = aCalendar.getTime();
       aCalendar.set(Calendar.DATE,     aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
       java.util.Date lastDateOfPreviousMonth = aCalendar.getTime();
       String pattern = "yyyy-MM-DD";
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
       String fromdate = simpleDateFormat.format(firstDateOfPreviousMonth);
       String toDate = simpleDateFormat.format(lastDateOfPreviousMonth);
       System.out.println(fromdate +" to "+toDate);
       
       boolean isDataAvailable = false;
       try {
            String query = "select count(*) from tax_vat_reports where report_date >= "+fromdate+" AND report_date >= "+toDate+"";
            ResultSet resultSetCount = statement.executeQuery(query);
            while (resultSetCount.next()){
                int countValue = resultSetCount.getInt(1);

                if(countValue>0)
                    isDataAvailable = true;
                else
                    isDataAvailable = false;
            }
            
                
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
       return isDataAvailable;
   }
   
   public void setVatCompleteData(String purchaseIDS) {
        
        
        try {
            
            //String query = "update purchaseinfo SET isPayed = 1 where purchaseID IN("+purchaseIDS+")";
                    
            //int rs = statement.executeUpdate(query);
            String query = "UPDATE purchaseinfo SET isTaxDone = 1 WHERE purchaseID IN ("+purchaseIDS+")";
            prepStatement = conn.prepareStatement(query);
//            prepStatement.setInt(1, 1);
//            prepStatement.setString(2, purchaseIDS);
            prepStatement.executeUpdate();
            prepStatement.close();
                 
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            closeResources();
        }
        
       
    }

   public void addTaxVatData(String purchaseIDS) {
        
        
        try {
            
                        
                String addquery = "INSERT INTO tax_vat_reports ( \"purchaseID\") VALUES(?)";
                prepStatement = conn.prepareStatement(addquery);
               
                prepStatement.setString(1, purchaseIDS);
                
                prepStatement.executeUpdate();
                
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            closeResources();
        }
        
       
    }
   
   public ResultSet getTaxVatData() {
        
        
        try {
            
                        
                String getQuery = "SELECT reportID, report_date, purchaseID from tax_vat_reports ORDER by report_date DESC";
                resultSet = statement.executeQuery(getQuery);
                
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        
        return resultSet;
       
    }
   
   // Purchase table data set retrieval
    public ResultSet getPurchaseInfoFor(String purchaseIDS) {
        try {
            String query = "SELECT purchaseinfo.purchaseid,STRFTIME('%d-%m-%Y', purchaseinfo.date) as date,suppliers.tpin_no,suppliers.fullname, purchaseinfo.invoiceNO,purchaseinfo.vatExclusiveValue,purchaseinfo.vatValue,purchaseinfo.totalcost,purchaseinfo.commodities FROM purchaseinfo INNER JOIN suppliers ON suppliers.sid = purchaseinfo.supplierID WHERE purchaseID IN ("+purchaseIDS+") ORDER BY purchaseinfo.date;";
            resultSet = statement.executeQuery(query);
            
            
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }
    
    public void editPurchaseDAO(String purchaseIDS) {
        try {
            String query = "UPDATE purchaseinfo SET isPayed=1 WHERE purchaseID IN(?)";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, purchaseIDS);
            
            prepStatement.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Supplier details have been updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            closeResources();
        }
    }
    
    
    // Payed table data set retrieval
    public ResultSet getPayedInfoFor(int id) {
        try {
            String query = "SELECT paymentID,payment_date,purchaseIDS,totalcost from payments where supplierID = "+id+" ORDER by payment_date;";
            resultSet = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }
    
   // Sales table data set retrieval
    public ResultSet getMonthlySalesInfo() {
        
        try {
            String query = "select salesid, sl_date, sr_local_sales, total_zro, total_otn18 from monthly_sales ORDER BY sl_date DESC";
            resultSet = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        
        return resultSet;
    }

     // Method to display product-related data set in tabular form
    public DefaultTableModel buildGenratedTaxVatTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        Vector<String> columnNames = new Vector<String>();
        int colCount = metaData.getColumnCount();

        for (int col=1; col <= colCount+1; col++){
            switch (col) {
                case 1:
                    columnNames.add("ID");
                    break;
                case 2:
                    columnNames.add("Report Date");
                    break;
                case 3:
                    columnNames.add("Purchase IDS");
                    break;
                case 4:
                    columnNames.add("View");
                    break;
                default:
                    break;
            }
                      
        }

        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
       
        // Define input and output date formats
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        
        while (resultSet.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int col=1; col<=colCount; col++) {
                
                if(col==2)
                {
                    try {
                            // Parse the input date string
                            java.util.Date date = inputFormat.parse(String.valueOf(resultSet.getObject(col)));

                            // Format the date to the desired output format
                            String formattedDate = outputFormat.format(date);

                            // Print the formatted date
                            System.out.println("Formatted Date: " + formattedDate);
                            vector.add(formattedDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                }
                else
                    vector.add(resultSet.getObject(col));
            }
            data.add(vector);
        }
        closeResources();
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return column==3;
                }
            };
            return tableModel;
        }

     // Method to display product-related data set in tabular form
    public DefaultTableModel buildPayedTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        Vector<String> columnNames = new Vector<String>();
        int colCount = metaData.getColumnCount();

        for (int col=1; col <= colCount+1; col++){
            switch (col) {
                case 1:
                    columnNames.add("ID");
                    break;
                case 2:
                    columnNames.add("Payed Date");
                    break;
                case 3:
                    columnNames.add("Purchase IDS");
                    break;
                case 4:
                    columnNames.add("Payed Amount");
                    break;
                case 5:
                    columnNames.add("View");
                    break;
                default:
                    break;
            }
                      
        }

        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
       
        // Define input and output date formats
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        
        while (resultSet.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int col=1; col<=colCount; col++) {
                
                if(col==2)
                {
                    try {
                            // Parse the input date string
                            java.util.Date date = inputFormat.parse(String.valueOf(resultSet.getObject(col)));

                            // Format the date to the desired output format
                            String formattedDate = outputFormat.format(date);

                            // Print the formatted date
                            System.out.println("Formatted Date: " + formattedDate);
                            vector.add(formattedDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                }
                else
                    vector.add(resultSet.getObject(col));
            }
            data.add(vector);
        }
        closeResources();
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return column==4;
                }
            };
            return tableModel;
        }
    
    
    public void closeResources() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (prepStatement != null) {
                prepStatement.close();
            }
            
            if (statement != null) {
                statement.close();
            }
           
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Method to display product-related data set in tabular form
    public DefaultTableModel buildTableModelPayment(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        Vector<String> columnNames = new Vector<String>();
        int colCount = metaData.getColumnCount();

        for (int col=1; col <= colCount+1; col++){
            //columnNames.add(metaData.getColumnName(col).toUpperCase(Locale.ROOT));
            switch (col) {
                case 1:
                    columnNames.add("ID");
                    break;
                case 2:
                    columnNames.add("Invoice date");
                    break;
                case 3:
                    columnNames.add("TPIN No");
                    break;
                case 4:
                    columnNames.add("Supplier Name");
                    break;
                case 5:
                    columnNames.add("Invoice No");
                    break;
                case 6:
                    columnNames.add("Vat Exclusive");
                    break;
                case 7:
                    columnNames.add("Vat Value");
                    break;
                case 8:
                    columnNames.add("Total");
                    break;
                case 9:
                    columnNames.add("Commodites");
                    break;
//                case 11:
//                    columnNames.add("Print");
//                    break;
                default:
                    break;
            }
        }

        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        
        while (resultSet.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int col=1; col<=colCount; col++) {
                
                vector.add(resultSet.getObject(col));
                
            }
            data.add(vector);
        }
        closeResources();
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            return tableModel;
        
    }

}
