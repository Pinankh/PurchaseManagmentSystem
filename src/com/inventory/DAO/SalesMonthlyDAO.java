/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.DAO;

import com.inventory.DTO.SalesMonthlyDTO;
import com.inventory.Database.DatabaseConnection;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
public class SalesMonthlyDAO {

    Connection conn = null;
    PreparedStatement prepStatement = null;
    
    Statement statement = null;
    
    ResultSet resultSet = null;

    public SalesMonthlyDAO() {
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

   public void addSalesMonthlyDAO(SalesMonthlyDTO monthlyDTO) {
        int isExist=0;
        String date = monthlyDTO.getSalesDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date convertedDate = null;
        try {
            convertedDate = dateFormat.parse(date);
        } catch (ParseException ex) {
            Logger.getLogger(SalesMonthlyDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(convertedDate);
        aCalendar.set(Calendar.DATE,     aCalendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        java.util.Date firstDateOfPreviousMonth = aCalendar.getTime();
        aCalendar.set(Calendar.DATE,     aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        java.util.Date lastDateOfPreviousMonth = aCalendar.getTime();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String fromdate = simpleDateFormat.format(firstDateOfPreviousMonth);
        String toDate = simpleDateFormat.format(lastDateOfPreviousMonth);
        System.out.println(fromdate +" to "+toDate);
        
        try {
            String query = "select count(salesid) AS recordCount from monthly_sales WHERE sl_date >= '"+fromdate+"' and sl_date <= '"+toDate+"'";
                    
            resultSet = statement.executeQuery(query);
            
            resultSet.next();
            isExist = resultSet.getInt("recordCount");
            resultSet.close();
            if (isExist>0)
            {
                JOptionPane.showMessageDialog(null, "Selected Month & Year Salaes Entry is already exist!");
                
                }
            else
            {
                String addquery = "INSERT INTO monthly_sales ( \"sr_local_sales\", \"total_zro\", \"total_otn18\", \"sl_date\") VALUES(?,?,?,?)";
                prepStatement = conn.prepareStatement(addquery);
                prepStatement.setDouble(1, monthlyDTO.getSr_local_sales());
                prepStatement.setDouble(2, monthlyDTO.getTotal_zro());
                prepStatement.setDouble(3, monthlyDTO.getTotal_otn18());
                prepStatement.setString(4, monthlyDTO.getSalesDate());

                prepStatement.executeUpdate();

                JOptionPane.showMessageDialog(null, "Monthly Sales Data added sucessfully.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            closeResources();
        }
        
       
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
    public DefaultTableModel buildMonthlySalesTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        Vector<String> columnNames = new Vector<String>();
        int colCount = metaData.getColumnCount();

        for (int col=1; col <= colCount; col++){
            switch (col) {
                case 1:
                    columnNames.add("ID");
                    break;
                case 2:
                    columnNames.add("Month-Year");
                    break;
                case 3:
                    columnNames.add("SR Local Sales");
                    break;
                case 4:
                    columnNames.add("Total ZR OUTPUT");
                    break;
                case 5:
                    columnNames.add("Total Output Tax No:18");
                    break;
                default:
                    break;
            }
                      
        }

        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat showDate = new SimpleDateFormat("dd-MMMM-yyyy");
        DecimalFormat  decimalFormat = new DecimalFormat("#0.00");
        while (resultSet.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int col=1; col<=colCount; col++) {
                
                if(col == 2)
                {
                    try {
                        java.util.Date date89 = sdf.parse(String.valueOf(resultSet.getObject(col)));
                        // Parse the input string into a LocalDate object
                        LocalDate date = LocalDate.parse(String.valueOf(resultSet.getObject(col)));

                        // Create a formatter for the desired output format
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-yyyy");

                        // Format the date using the formatter
                        String formattedDate = date.format(formatter);
                        
                        vector.add(formattedDate);
                    } catch (ParseException ex) {
                        Logger.getLogger(SalesMonthlyDAO.class.getName()).log(Level.SEVERE, null, ex);
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
