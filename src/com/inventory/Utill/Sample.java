/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventory.Utill;
import com.inventory.DTO.ProductDTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
/**
 *
 * @author pinal
 */
    

    public class Sample
    {
        
          // Method to add a new purchase transaction
    
      public static void main(String[] args)
      {
        Connection connection = null;
        try
        {
          // create a database connection
          connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
          Statement statement = connection.createStatement();
          statement.setQueryTimeout(30);  // set timeout to 30 sec.

          statement.executeUpdate("drop table if exists person");
          statement.executeUpdate("create table person (id integer, name string)");
          statement.executeUpdate("insert into person values(1, 'leo')");
          statement.executeUpdate("insert into person values(2, 'yui')");
          
          try {
            String query = "INSERT INTO purchaseinfo ( \"suppliercode\", \"productcode\", \"date\", \"quantity\", \"totalcost\", \"supplierID\", \"invoiceNO\", \"vatExclusiveValue\", \"vatValue\", \"commodities\", \"isPayed\") VALUES(?,?,?,?,?,?,?,?,?,?,?)";
              PreparedStatement prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, "DRG");
            prepStatement.setString(2, "P01");
            prepStatement.setString(3, "25-02-2012");
            prepStatement.setInt(4, 5);
            prepStatement.setDouble(5, 2535.25);
            prepStatement.setInt(6, 401);
            prepStatement.setString(7, "654554");
            prepStatement.setDouble(8, 52415);
            prepStatement.setDouble(9, 524.25);
            prepStatement.setString(10, "Test");
            prepStatement.setInt(11,0);
            prepStatement.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Purchase log added.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
          
          ResultSet rs = statement.executeQuery("select * from purchaseinfo");
          while(rs.next())
          {
            // read the result set
              System.out.println("id = " + rs.getInt("purchaseID"));
            System.out.println("name = " + rs.getString("suppliercode"));
            System.out.println("Invoice = " + rs.getString("invoiceNo"));
            System.out.println("name = " + rs.getDouble("vatValue"));
            
          }
        }
        catch(SQLException e)
        {
          // if the error message is "out of memory",
          // it probably means no database file is found
          System.err.println(e.getMessage());
        }
        finally
        {
          try
          {
            if(connection != null)
              connection.close();
          }
          catch(SQLException e)
          {
            // connection close failed.
            System.err.println(e.getMessage());
          }
        }
      }
    }