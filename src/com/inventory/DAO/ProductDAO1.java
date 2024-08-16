/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.DAO;

import com.inventory.DTO.ProductDTO;
import com.inventory.Database.ConnectionFactory;
import com.inventory.Database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author asjad
 */

// Data Access Object for Products, Purchase, Stock and Sales
public class ProductDAO1 {

    Connection conn = null;
    PreparedStatement prepStatement = null;
    PreparedStatement prepStatement2 = null;
    Statement statement = null;
    Statement statement2 = null;
    ResultSet resultSet = null;

    public ProductDAO1() {
        try {
//            conn = new ConnectionFactory().getConn();
//            statement = conn.createStatement();
//            statement2 = conn.createStatement();
            
            conn = DriverManager.getConnection(DatabaseConnection.DB_URL);
            statement = conn.createStatement();
            statement2 = conn.createStatement();
            statement.setQueryTimeout(50);
            statement2.setQueryTimeout(50);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
       
    }

    public ResultSet getSuppInfo() {
        try {
            String query = "SELECT * FROM suppliers";
            resultSet = statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getCustInfo() {
        try {
            String query = "SELECT * FROM customers";
            resultSet = statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getProdStock() {
        try {
            String query = "SELECT * FROM currentstock";
            resultSet = statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getProdInfo() {
        try {
            String query = "SELECT * FROM products";
            resultSet = statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public Double getProdCost(String prodCode) {
        Double costPrice = null;
        try {
            String query = "SELECT costprice FROM products WHERE productcode='" +prodCode+ "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                costPrice = resultSet.getDouble("costprice");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return costPrice;
    }

    public Double getProdSell(String prodCode) {
        Double sellPrice = null;
        try {
            String query = "SELECT sellprice FROM products WHERE productcode='" +prodCode+ "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                sellPrice = resultSet.getDouble("sellprice");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sellPrice;
    }

    String suppCode;
    public String getSuppCode(String suppName) {
        try {
            String query = "SELECT suppliercode FROM suppliers WHERE fullname='" +suppName+ "'";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                suppCode = resultSet.getString("suppliercode");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppCode;
    }
    
    int suppID;
    public int getSuppID(String suppName) {
        try {
            String query = "SELECT sid FROM suppliers WHERE fullname='" +suppName+ "'";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                suppID = resultSet.getInt("sid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppID;
    }
    
    String tpinNO;
    public String getTPIN_No(String suppName) {
        try {
            String query = "SELECT tpin_no FROM suppliers WHERE fullname='" +suppName+ "'";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                tpinNO = resultSet.getString("tpin_no");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tpinNO;
    }
    
    double vatPercentage;
    public double getVatPercentage(String suppName) {
        try {
            String query = "SELECT vat_percent FROM suppliers WHERE fullname='" +suppName+ "'";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                vatPercentage = resultSet.getDouble("vat_percent");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vatPercentage;
    }
    

    String prodCode;
    public String getProdCode(String prodName) {
        try {
            String query = "SELECT productcode FROM products WHERE productname='" +prodName+ "'";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                suppCode = resultSet.getString("productcode");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prodCode;
    }

    String custCode;
    public String getCustCode(String custName) {
        try {
            String query = "SELECT customercode FROM suppliers WHERE fullname='" +custName+ "'";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                suppCode = resultSet.getString("customercode");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return custCode;
    }

    // Method to check for availability of stock in Inventory
    boolean flag = false;
    public boolean checkStock(String prodCode) {
        try {
            String query = "SELECT * FROM currentstock WHERE productcode='" +prodCode+ "'";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    // Methods to add a new product
    public void addProductDAO(ProductDTO productDTO) {
        try {
            String query = "SELECT * FROM products WHERE productname='"
                    + productDTO.getProdName()
                    + "' AND costprice='"
                    + productDTO.getCostPrice()
                    + "' AND sellprice='"
                    + productDTO.getSellPrice()
                    + "' AND brand='"
                    + productDTO.getBrand()
                    + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                JOptionPane.showMessageDialog(null, "Product has already been added.");
            else
                addFunction(productDTO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addFunction(ProductDTO productDTO) {
        try {
            String query = "INSERT INTO products VALUES(null,?,?,?,?,?)";
            prepStatement = (PreparedStatement) conn.prepareStatement(query);
            prepStatement.setString(1, productDTO.getProdCode());
            prepStatement.setString(2, productDTO.getProdName());
            prepStatement.setDouble(3, productDTO.getCostPrice());
            prepStatement.setDouble(4, productDTO.getSellPrice());
            prepStatement.setString(5, productDTO.getBrand());

            String query2 = "INSERT INTO currentstock VALUES(?,?)";
            prepStatement2 = conn.prepareStatement(query2);
            prepStatement2.setString(1, productDTO.getProdCode());
            prepStatement2.setInt(2, productDTO.getQuantity());

            prepStatement.executeUpdate();
            prepStatement2.executeUpdate();
            JOptionPane.showMessageDialog(null, "Product added and ready for sale.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Method to add a new purchase transaction
    public void addPurchaseDAO(ProductDTO productDTO) {
        try {
            String query = "INSERT INTO purchaseinfo VALUES(null,?,?,?,?,?)";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, productDTO.getSuppCode());
            prepStatement.setString(2, productDTO.getProdCode());
            prepStatement.setString(3, productDTO.getDate());
            prepStatement.setInt(4, productDTO.getQuantity());
            prepStatement.setDouble(5, productDTO.getTotalCost());

            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Purchase log added.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String prodCode = productDTO.getProdCode();
        if(checkStock(prodCode)) {
            try {
                String query = "UPDATE currentstock SET quantity=quantity+? WHERE productcode=?";
                prepStatement = conn.prepareStatement(query);
                prepStatement.setInt(1, productDTO.getQuantity());
                prepStatement.setString(2, prodCode);

                prepStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else if (!checkStock(prodCode)) {
            try {
                String query = "INSERT INTO currentstock VALUES(?,?)";
                prepStatement = (PreparedStatement) conn.prepareStatement(query);
                prepStatement.setString(1, productDTO.getProdCode());
                prepStatement.setInt(2, productDTO.getQuantity());

                prepStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        deleteStock();
    }

     // Method to add a new purchase transaction
    public void addPurchaseDAO2(ProductDTO productDTO) {
        try {
            String query = "INSERT INTO purchaseinfo ( \"suppliercode\", \"productcode\", \"date\", \"quantity\", \"totalcost\", \"supplierID\", \"invoiceNO\", \"vatExclusiveValue\", \"vatValue\", \"commodities\", \"isPayed\") VALUES(?,?,?,?,?,?,?,?,?,?,?)";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, productDTO.getSuppCode());
            prepStatement.setString(2, productDTO.getProdCode());
            prepStatement.setString(3, productDTO.getDate());
            prepStatement.setInt(4, productDTO.getQuantity());
            prepStatement.setDouble(5, productDTO.getTotalCost());
            prepStatement.setInt(6, productDTO.getSupplierID());
            prepStatement.setString(7, productDTO.getInvoiceNO());
            prepStatement.setDouble(8, productDTO.getVatExclusiveValue());
            prepStatement.setDouble(9, productDTO.getVatValue());
            prepStatement.setString(10, productDTO.getCommodities());
            prepStatement.setInt(11,0);
            prepStatement.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Purchase log added.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            if (conn != null) {
              try {
                conn.close(); // <-- This is important
              } catch (SQLException e) {
                /* handle exception */
              }
            }
}

    }
    
    // Method to update existing product details
    public void editProdDAO(ProductDTO productDTO) {
        try {
            String query = "UPDATE products SET productname=?,costprice=?,sellprice=?,brand=? WHERE productcode=?";
            prepStatement = (PreparedStatement) conn.prepareStatement(query);
            prepStatement.setString(1, productDTO.getProdName());
            prepStatement.setDouble(2, productDTO.getCostPrice());
            prepStatement.setDouble(3, productDTO.getSellPrice());
            prepStatement.setString(4, productDTO.getBrand());
            prepStatement.setString(5, productDTO.getProdCode());

            String query2 = "UPDATE currentstock SET quantity=? WHERE productcode=?";
            prepStatement2 = conn.prepareStatement(query2);
            prepStatement2.setInt(1, productDTO.getQuantity());
            prepStatement2.setString(2, productDTO.getProdCode());

            prepStatement.executeUpdate();
            prepStatement2.executeUpdate();
            JOptionPane.showMessageDialog(null, "Product details updated.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Methods to handle updating of stocks in Inventory upon any transaction made
    public void editPurchaseStock(String code, int quantity) {
        try {
            String query = "SELECT * FROM currentstock WHERE productcode='" +code+ "'";
            resultSet = statement.executeQuery(query);
            if(resultSet.next()) {
                String query2 = "UPDATE currentstock SET quantity=quantity-? WHERE productcode=?";
                prepStatement = conn.prepareStatement(query2);
                prepStatement.setInt(1, quantity);
                prepStatement.setString(2, code);
                prepStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void editSoldStock(String code, int quantity) {
        try {
            String query = "SELECT * FROM currentstock WHERE productcode='" +code+ "'";
            resultSet = statement.executeQuery(query);
            if(resultSet.next()) {
                String query2 = "UPDATE currentstock SET quantity=quantity+? WHERE productcode=?";
                prepStatement = conn.prepareStatement(query2);
                prepStatement.setInt(1, quantity);
                prepStatement.setString(2, code);
                prepStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void deleteStock() {
        try {
            String query = "DELETE FROM currentstock WHERE productcode NOT IN(SELECT productcode FROM purchaseinfo)";
            String query2 = "DELETE FROM salesinfo WHERE productcode NOT IN(SELECT productcode FROM products)";
            statement.executeUpdate(query);
            statement.executeUpdate(query2);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Method to permanently delete a product from inventory
    public void deleteProductDAO(String code) {
        try {
            String query = "DELETE FROM products WHERE productcode=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, code);

            String query2 = "DELETE FROM currentstock WHERE productcode=?";
            prepStatement2 = conn.prepareStatement(query2);
            prepStatement2.setString(1, code);

            prepStatement.executeUpdate();
            prepStatement2.executeUpdate();

            JOptionPane.showMessageDialog(null, "Product has been removed.");
        } catch (SQLException e){
            e.printStackTrace();
        }
        deleteStock();
    }

    public void deletePurchaseDAO(int ID){
        try {
            String query = "DELETE FROM purchaseinfo WHERE purchaseID=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setInt(1, ID);
            prepStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Transaction has been removed.");
        } catch (SQLException e){
            e.printStackTrace();
        }
        deleteStock();
    }

    public void deleteSaleDAO(int ID) {
        try {
            String query = "DELETE FROM salesinfo WHERE salesID=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setInt(1, ID);
            prepStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Transaction has been removed.");
        } catch (SQLException e){
            e.printStackTrace();
        }
        deleteStock();
    }

    // Sales transaction handling
    public void sellProductDAO(ProductDTO productDTO, String username) {
        int quantity = 0;
        String prodCode = null;
        try {
            String query = "SELECT * FROM currentstock WHERE productcode='" +productDTO.getProdCode()+ "'";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                prodCode = resultSet.getString("productcode");
                quantity = resultSet.getInt("quantity");
            }
            if (productDTO.getQuantity()>quantity)
                JOptionPane.showMessageDialog(null, "Insufficient stock for this product.");
            else if (productDTO.getQuantity()<=0)
                JOptionPane.showMessageDialog(null, "Please enter a valid quantity");
            else {
                String stockQuery = "UPDATE currentstock SET quantity=quantity-'"
                        +productDTO.getQuantity()
                        +"' WHERE productcode='"
                        +productDTO.getProdCode()
                        +"'";
                String salesQuery = "INSERT INTO salesinfo(date,productcode,customercode,quantity,revenue,soldby)" +
                        "VALUES('"+productDTO.getDate()+"','"+productDTO.getProdCode()+"','"+productDTO.getCustCode()+
                        "','"+productDTO.getQuantity()+"','"+productDTO.getTotalRevenue()+"','"+username+"')";
                statement.executeUpdate(stockQuery);
                statement.executeUpdate(salesQuery);
                JOptionPane.showMessageDialog(null, "Product sold.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Products data set retrieval for display
    public ResultSet getQueryResult() {
        try {
            String query = "SELECT productcode,productname,costprice,sellprice,brand FROM products ORDER BY pid";
            resultSet = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    // Purchase table data set retrieval
    public ResultSet getPurchaseInfo() {
        try {
            String query = "SELECT PurchaseID,purchaseinfo.ProductCode,ProductName,Quantity,Totalcost " +
                    "FROM purchaseinfo INNER JOIN products " +
                    "ON products.productcode=purchaseinfo.productcode ORDER BY purchaseid;";
            resultSet = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }
    
    // Purchase table data set retrieval
    public ResultSet getPurchaseInfo2() {
        try {
            String query = "SELECT purchaseinfo.supplierID,purchaseinfo.date,suppliers.tpin_no,suppliers.fullname, purchaseinfo.invoiceNO,purchaseinfo.vatExclusiveValue,purchaseinfo.vatValue,purchaseinfo.commodities,purchaseinfo.isPayed FROM purchaseinfo INNER JOIN suppliers ON suppliers.sid = purchaseinfo.supplierID ORDER BY purchaseid;";
            resultSet = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    // Stock table data set retrieval
    public ResultSet getCurrentStockInfo() {
        try {
            var query = "SELECT currentstock.ProductCode,products.ProductName,currentstock.Quantity,products.CostPrice,products.SellPrice FROM currentstock INNER JOIN products ON currentstock.productcode=products.productcode;";
                    
            resultSet = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    // Sales table data set retrieval
    public ResultSet getSalesInfo() {
        try {
            String query = "SELECT salesid, salesinfo.productcode, productname, salesinfo.quantity, revenue, users.name AS Sold_by FROM salesinfo INNER JOIN products ON salesinfo.productcode=products.productcode INNER JOIN users ON salesinfo.soldby=users.username;";
                    
            resultSet = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    // Search method for products
    public ResultSet getProductSearch(String text) {
        try {
            String query = "SELECT productcode,productname,costprice,sellprice,brand FROM products " +
                    "WHERE productcode LIKE '%"+text+"%' OR productname LIKE '%"+text+"%' OR brand LIKE '%"+text+"%'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getProdFromCode(String text) {
        try {
            String query = "SELECT productcode,productname,costprice,sellprice,brand FROM products " +
                    "WHERE productcode='" +text+ "' LIMIT 1";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // Search method for sales
    public ResultSet getSalesSearch(String text) {
        try {
            String query = "SELECT salesid,salesinfo.productcode,productname,\n" +
                    "                    salesinfo.quantity,revenue,users.name AS Sold_by\n" +
                    "                    FROM salesinfo INNER JOIN products\n" +
                    "                    ON salesinfo.productcode=products.productcode\n" +
                    "                    INNER JOIN users\n" +
                    "                    ON salesinfo.soldby=users.username\n" +
                    "                    INNER JOIN customers\n" +
                    "                    ON customers.customercode=salesinfo.customercode\n" +
                    "WHERE salesinfo.productcode LIKE '%"+text+"%' OR productname LIKE '%"+text+"%' " +
                    "OR users.name LIKE '%"+text+"%' OR customers.fullname LIKE '%"+text+"%' ORDER BY salesid;";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // Search method for purchase logs
    public ResultSet getPurchaseSearch(String text) {
        try {
            String query = "SELECT PurchaseID,purchaseinfo.productcode,products.productname,quantity,totalcost " +
                    "FROM purchaseinfo INNER JOIN products ON purchaseinfo.productcode=products.productcode " +
                    "INNER JOIN suppliers ON purchaseinfo.suppliercode=suppliers.suppliercode" +
                    "WHERE PurchaseID LIKE '%"+text+"%' OR productcode LIKE '%"+text+"%' OR productname LIKE '%"+text+"%' " +
                    "OR suppliers.fullname LIKE '%"+text+"%' OR purchaseinfo.suppliercode LIKE '%"+text+"%' " +
                    "OR date LIKE '%"+text+"%' ORDER BY purchaseid";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    
    // Search method for purchase logs
    public ResultSet getPurchaseSearch2(String text) {
        try {
            String query = "SELECT purchaseinfo.date,suppliers.tpin_no,suppliers.fullname, purchaseinfo.invoiceNO,purchaseinfo.vatExclusiveValue,purchaseinfo.vatValue,purchaseinfo.commodities,purchaseinfo.isPayed " +
                    "FROM purchaseinfo INNER JOIN suppliers ON purchaseinfo.supplierID=suppliers.sid " +
                    "WHERE purchaseID LIKE '%"+text+"%' OR suppliers.tpin_no LIKE '%"+text+"%' OR suppliers.fullname LIKE '%"+text+"%' " +
                    "OR purchaseinfo.invoiceNO LIKE '%"+text+"%' OR purchaseinfo.vatExclusiveValue LIKE '%"+text+"%' " +
                    "OR date LIKE '%"+text+"%' OR purchaseinfo.commodities LIKE '%"+text+"%' ORDER BY purchaseid";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getProdName(String code) {
        try {
            String query = "SELECT productname FROM products WHERE productcode='" +code+ "'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    public String getSuppName(int ID) {
        String name = null;
        try {
            String query = "SELECT fullname FROM suppliers " +
                    "INNER JOIN purchaseinfo ON suppliers.suppliercode=purchaseinfo.suppliercode " +
                    "WHERE purchaseid='" +ID+ "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                name = resultSet.getString("fullname");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return name;
    }

    public String getCustName(int ID) {
        String name = null;
        try {
            String query = "SELECT fullname FROM customers " +
                    "INNER JOIN salesinfo ON customers.customercode=salesinfo.customercode " +
                    "WHERE salesid='" +ID+ "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                name = resultSet.getString("fullname");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return name;
    }

    public String getPurchaseDate(int ID) {
        String date = null;
        try {
            String query = "SELECT date FROM purchaseinfo WHERE purchaseid='" +ID+ "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                date = resultSet.getString("date");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return date;
    }
    public String getSaleDate(int ID) {
        String date = null;
        try {
            String query = "SELECT date FROM salesinfo WHERE salesid='" +ID+ "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                date = resultSet.getString("date");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return date;
    }


    // Method to display product-related data set in tabular form
    public DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        Vector<String> columnNames = new Vector<String>();
        int colCount = metaData.getColumnCount();

        for (int col=1; col <= colCount; col++){
            columnNames.add(metaData.getColumnName(col).toUpperCase(Locale.ROOT));
          
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
        return new DefaultTableModel(data, columnNames);
    }

    // Method to display product-related data set in tabular form
    public DefaultTableModel buildTableModelPurchase(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        Vector<String> columnNames = new Vector<String>();
        int colCount = metaData.getColumnCount();

        for (int col=1; col <= colCount+1; col++){
            //columnNames.add(metaData.getColumnName(col).toUpperCase(Locale.ROOT));
            switch (col) {
                case 1:
                    columnNames.add("Scode");
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
                    columnNames.add("Commodities");
                    break;
                case 9:
                    columnNames.add("isPayed");
                    break;
                case 10:
                    columnNames.add("Print");
                    break;
                default:
                    break;
            }
        }

        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        
        while (resultSet.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int col=1; col<=colCount; col++) {
                if(col==2)
                {
                    try {
                        String dat = sdf.parse(resultSet.getObject(col).toString()).toString();
                        SimpleDateFormat sdf2 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                        java.util.Date date = sdf2.parse(dat);
                        sdf2.applyPattern("dd-MMM-yyyy");
                        String formattedDate = sdf2.format(date);
        
                        vector.add(formattedDate);
                    } catch (ParseException ex) {
                        Logger.getLogger(ProductDAO1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                else if(col==9)
                {
                    int val = this.resultSet.getInt(col);
                    if(val==0)
                    {
                        vector.add("NO");
                    }
                    else
                    {
                        vector.add("YES");
                    }
                }
                else
                {
                    vector.add(resultSet.getObject(col));
                }
                
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }





}
