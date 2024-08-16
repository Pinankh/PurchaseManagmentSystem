/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.DAO;

import com.inventory.DTO.SupplierComboboxModel;
import com.inventory.DTO.SupplierDTO;
import com.inventory.Database.ConnectionFactory;
import com.inventory.Database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

/**
 *
 * @author asjad
 */

// Data Access Object for Suppliers
public class SupplierDAO {

    Connection conn = null;
    Statement statement = null;
    PreparedStatement prepStatement = null;
    ResultSet resultSet = null;

    public SupplierDAO() {
        try {
//            conn = new ConnectionFactory().getConn();
//            statement = conn.createStatement();
            conn = DriverManager.getConnection(DatabaseConnection.DB_URL);
//            conn = DriverManager.getConnection("jdbc:sqlite:sample.db");
            statement = conn.createStatement();
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Methods to add new supplier
    public void addSupplierDAO(SupplierDTO supplierDTO) {
        try {
            String query = "SELECT * FROM suppliers WHERE fullname='"
                    +supplierDTO.getFullName()
                    + "' AND location='"
                    +supplierDTO.getLocation()
                    + "' AND mobile='"
                    +supplierDTO.getPhone()
                    + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                JOptionPane.showMessageDialog(null, "This supplier already exists.");
            else
                addFunction(supplierDTO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            closeResources();
        }
    }
    public void addFunction(SupplierDTO supplierDTO) {
        try {
            String query = "INSERT INTO suppliers (\"suppliercode\", \"fullname\", \"location\", \"mobile\", \"tpin_no\", \"vat_no\", \"vat_percent\", \"tax_category_no\", \"tax_category_name\", \"bank_name\", \"account_name\", \"account_number\", \"branch_name\", \"short_code\", \"nrc\") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, supplierDTO.getSuppCode());
            prepStatement.setString(2, supplierDTO.getFullName());
            prepStatement.setString(3, supplierDTO.getLocation());
            prepStatement.setString(4, supplierDTO.getPhone());
            prepStatement.setString(5, supplierDTO.getTpin_no());
            prepStatement.setString(6, supplierDTO.getVat_no());
            prepStatement.setDouble(7, supplierDTO.getVat_percent());
            prepStatement.setInt(8, Integer.parseInt(supplierDTO.getTax_category_no()));
            prepStatement.setString(9, supplierDTO.getTax_category_name());
            
            prepStatement.setString(10, supplierDTO.getBank_name());
            prepStatement.setString(11, supplierDTO.getAccount_name());
            prepStatement.setString(12, supplierDTO.getAccount_number());
            prepStatement.setString(13, supplierDTO.getBranch_name());
            prepStatement.setString(14, supplierDTO.getSuppCode());
            prepStatement.setString(15, supplierDTO.getNrc());
            
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "New supplier has been added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            closeResources();
        }
    }

    // Method to edit existing suppleir details
    public void editSupplierDAO(SupplierDTO supplierDTO) {
        try {
            String query = "UPDATE suppliers SET fullname=?,location=?,mobile=?,tpin_no=?,vat_no=?, vat_percent=?, tax_category_no=?, tax_category_name=?,suppliercode=?,bank_name=?,account_name=?,account_number=?,branch_name=?,short_code=?,nrc=? WHERE sid=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, supplierDTO.getFullName());
            prepStatement.setString(2, supplierDTO.getLocation());
            prepStatement.setString(3, supplierDTO.getPhone());
            prepStatement.setString(4, supplierDTO.getTpin_no());
            prepStatement.setString(5, supplierDTO.getVat_no());
            prepStatement.setDouble(6, supplierDTO.getVat_percent());
            prepStatement.setInt(7, Integer.parseInt(supplierDTO.getTax_category_no()));
            prepStatement.setString(8, supplierDTO.getTax_category_name());
            prepStatement.setString(9, supplierDTO.getSuppCode());
            
            prepStatement.setString(10, supplierDTO.getBank_name());
            prepStatement.setString(11, supplierDTO.getAccount_name());
            prepStatement.setString(12, supplierDTO.getAccount_number());
            prepStatement.setString(13, supplierDTO.getBranch_name());
            prepStatement.setString(14, supplierDTO.getShort_code());
            prepStatement.setString(15, supplierDTO.getNrc());
            
            prepStatement.setInt(16, supplierDTO.getSuppID());
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Supplier details have been updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            closeResources();
        }
    }

    // Method to delete existing supplier
    public void deleteSupplierDAO(String suppID) {
        try {
            
            if(getpurchaseCount(Integer.parseInt(suppID)) > 0)
            {
                JOptionPane.showMessageDialog(null, "This Supplier can't be removed. Becuase you entered purchase entry with this supplier!");
            }
            else
            {
                String query = "DELETE FROM suppliers WHERE sid=" +suppID;
                statement.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Supplier has been removed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            closeResources();
        }
    }

    // Supplier data set retrieval method
    public int getpurchaseCount(int id) {
        int isExist = 0;
        try {
            
            String query = "SELECT count(*) AS recordCount from purchaseinfo WHERE supplierID = "+id;
            resultSet = statement.executeQuery(query);
            resultSet.next();
            isExist = resultSet.getInt("recordCount");
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isExist;
    }
    
    // Supplier data set retrieval method
    public ResultSet getQueryResult() {
        try {
            
            String query = "SELECT sid, suppliercode, fullname, location, mobile, tpin_no, vat_percent, tax_category_no, tax_category_name,bank_name,account_name,account_number,branch_name,short_code,nrc FROM suppliers";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    
    // Supplier data set retrieval method
    public SupplierDTO getSupplierBankDetails(int id) {
        SupplierDTO dTO = new SupplierDTO();
        try {
            
            String query = "SELECT sid, suppliercode, fullname, location, mobile, tpin_no, vat_percent, tax_category_no, tax_category_name,bank_name,account_name,account_number,branch_name,short_code,nrc FROM suppliers WHERE sid = "+id;
            resultSet = statement.executeQuery(query);
            
            while (resultSet.next()){
            
                
            dTO.setSuppID(resultSet.getInt("sid"));
            dTO.setSuppCode(resultSet.getString("suppliercode"));
            dTO.setFullName(resultSet.getString("fullname"));
            dTO.setTpin_no(resultSet.getString("tpin_no"));
            dTO.setPhone(resultSet.getString("mobile"));
            dTO.setVat_percent(resultSet.getDouble("vat_percent"));
            
            dTO.setBank_name(resultSet.getString("bank_name"));
            dTO.setAccount_name(resultSet.getString("account_name"));
            dTO.setAccount_number(resultSet.getString("account_number"));
            dTO.setBranch_name(resultSet.getString("branch_name"));
            dTO.setShort_code(resultSet.getString("short_code"));
            dTO.setNrc(resultSet.getString("nrc"));
            
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dTO;
    }
    
    // Supplier data set retrieval method
    public int getQueryCountResult() {
        int countValue=0;
        try {
            String countQuery = "Select count(*) from suppliers";
            ResultSet resultSetCount = statement.executeQuery(countQuery);
            while (resultSetCount.next()){
            countValue = resultSetCount.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countValue;
    }

    // Search method
    public ResultSet getSearchResult(String searchText) {
        try {
            String query = "SELECT sid, suppliercode, fullname, location, mobile, tpin_no, vat_percent, tax_category_no, tax_category_name,bank_name,account_name,account_number,branch_name,short_code,nrc FROM suppliers " +
                    "WHERE suppliercode LIKE '%"+searchText+"%' OR location LIKE '%"+searchText+"%' " +
                    "OR fullname LIKE '%"+searchText+"%' OR mobile LIKE '%"+searchText+"%'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // Method to set/update supplier combo box
    public DefaultComboBoxModel<String> setComboItems(ResultSet resultSet) throws SQLException {
        Vector<String> suppNames = new Vector<>();
        while (resultSet.next()){
            suppNames.add(resultSet.getString("fullname"));
        }
        return new DefaultComboBoxModel<>(suppNames);
    }
    
    
     // Method to set/update supplier combo box
    public SupplierComboboxModel setComboItemsCustome(ResultSet resultSet,int count) throws SQLException {
        SupplierDTO[] supplierDTOs = new SupplierDTO[count];
        int i=0;
        while (resultSet.next()){
             SupplierDTO dTO = new SupplierDTO();
            dTO.setSuppID(resultSet.getInt("sid"));
            dTO.setSuppCode(resultSet.getString("suppliercode"));
            dTO.setFullName(resultSet.getString("fullname"));
            dTO.setTpin_no(resultSet.getString("tpin_no"));
            dTO.setPhone(resultSet.getString("mobile"));
            dTO.setVat_percent(resultSet.getDouble("vat_percent"));
            dTO.setVat_no(resultSet.getString("tax_category_no"));
            dTO.setBank_name(resultSet.getString("bank_name"));
            dTO.setAccount_name(resultSet.getString("account_name"));
            dTO.setAccount_number(resultSet.getString("account_number"));
            dTO.setBranch_name(resultSet.getString("branch_name"));
            dTO.setShort_code(resultSet.getString("short_code"));
            dTO.setNrc(resultSet.getString("nrc"));
            supplierDTOs[i] = dTO;
            i++;
        }
        SupplierComboboxModel model = new SupplierComboboxModel();
        model.supplierDTOs = supplierDTOs;
        return model;
    }
    
    public SupplierDTO[] setComboItemsCustome2(ResultSet resultSet,int count) throws SQLException {
        SupplierDTO[] supplierDTOs = new SupplierDTO[count];
        int i=0;
        while (resultSet.next()){
             SupplierDTO dTO = new SupplierDTO();
            dTO.setSuppID(resultSet.getInt("sid"));
            dTO.setSuppCode(resultSet.getString("suppliercode"));
            dTO.setFullName(resultSet.getString("fullname"));
            dTO.setTpin_no(resultSet.getString("tpin_no"));
            dTO.setPhone(resultSet.getString("mobile"));
            dTO.setVat_percent(resultSet.getDouble("vat_percent"));
            
            dTO.setBank_name(resultSet.getString("bank_name"));
            dTO.setAccount_name(resultSet.getString("account_name"));
            dTO.setAccount_number(resultSet.getString("account_number"));
            dTO.setBranch_name(resultSet.getString("branch_name"));
            dTO.setShort_code(resultSet.getString("short_code"));
            dTO.setNrc(resultSet.getString("nrc"));
            supplierDTOs[i] = dTO;
            i++;
        }
        
        return supplierDTOs;
    }
    
    // Method to set/update supplier combo box
    public SupplierComboboxModel setComboItemsNew(ResultSet resultSet,int count) throws SQLException {
       
                SupplierDTO[] supplierDTOs = new SupplierDTO[count];

                // sid, suppliercode, fullname, location, mobile, tpin_no, vat_percent, tax_category_no, tax_category_name,bank_name,account_name,account_number,branch_name,short_code,nrc
                int i=0;
                while (resultSet.next()){
                    SupplierDTO dTO = new SupplierDTO();
                    dTO.setSuppID(resultSet.getInt("sid"));
                    dTO.setSuppCode(resultSet.getString("suppliercode"));
                    dTO.setFullName(resultSet.getString("fullname"));
                    dTO.setTpin_no(resultSet.getString("tpin_no"));
                    dTO.setPhone(resultSet.getString("mobile"));
                    dTO.setVat_percent(resultSet.getDouble("vat_percent"));

                    dTO.setBank_name(resultSet.getString("bank_name"));
                    dTO.setAccount_name(resultSet.getString("account_name"));
                    dTO.setAccount_number(resultSet.getString("account_number"));
                    dTO.setBranch_name(resultSet.getString("branch_name"));
                    dTO.setShort_code(resultSet.getString("short_code"));
                    dTO.setNrc(resultSet.getString("nrc"));
                    supplierDTOs[i] = dTO;
                    i++;
                }
                SupplierComboboxModel model = new SupplierComboboxModel();
                model.supplierDTOs = supplierDTOs;
                return model;
        
    }
    

    // Method to display retrieved data set in tabular form
    public DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        Vector<String> columnNames = new Vector<String>();
        int colCount = metaData.getColumnCount();

        for (int col=1; col <= colCount; col++){
            //columnNames.add(metaData.getColumnName(col).toUpperCase(Locale.ROOT));
            //sid, 
            //suppliercode, 
            //fullname, 
            //location, 
            //mobile, 
            //tpin_no, 
            //vat_percent, 
            //tax_category_no, 
            //tax_category_name,
            //bank_name,
            //account_number,
            //branch_name,
            //short_code,
            //nrc
            switch (col) {
                case 1:
                        columnNames.add("id");
                    break;
                    case 2:
                        columnNames.add("SpCode");
                    break;
                    case 3:
                        columnNames.add("Name");
                    break;
                    case 4:
                        columnNames.add("City");
                    break;
                    case 5:
                        columnNames.add("Contact");
                    break;
                    case 6:
                        columnNames.add("Tpin_no");
                    break;
                    case 7:
                        columnNames.add("Vat%");
                    break;
                    case 8:
                        columnNames.add("TaxNo");
                    break;
                    case 9:
                        columnNames.add("Tax Name");
                    break;
                    case 10:
                        columnNames.add("Bank Name");
                    break;
                    case 11:
                        columnNames.add("Account Name");
                    break;
                    case 12:
                        columnNames.add("Account No");
                    break;
                    case 13:
                        columnNames.add("Branch");
                    break;
                    case 14:
                        columnNames.add("ShortCode");
                    break;
                    case 15:
                        columnNames.add("NRC");
                    break;
                    
                default:
                    throw new AssertionError();
            }
        }

        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
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
}
