package com.inventory.DAO;

import com.inventory.DTO.UserDTO;
import com.inventory.Database.DatabaseConnection;
import com.inventory.UI.UsersPage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Vector;

public class UserDAO {
    private Connection conn = null;
    private PreparedStatement prepStatement = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    // Constructor method
    public UserDAO() {
        try {
            conn = DriverManager.getConnection(DatabaseConnection.DB_URL);
            //conn = DriverManager.getConnection("jdbc:sqlite:sample.db");
            statement = conn.createStatement();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
     //Login verification method
    public boolean checkLogin(String username, String password, String userType){
        String query = "SELECT * FROM users WHERE username='"
                + username
                + "' AND password='"
                + password
                + "' AND usertype='"
                + userType
                + "' LIMIT 1";

        try {
            resultSet = statement.executeQuery(query);
            if(resultSet.next()) return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally{
            closeResources();
        }
        return false;
    }
    

    // Methods to add new user
    public void addUserDAO(UserDTO userDTO, String userType) {
        try {
            String query = "SELECT * FROM users WHERE name=? AND location=? AND phone=? AND usertype=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, userDTO.getFullName());
            prepStatement.setString(2, userDTO.getLocation());
            prepStatement.setString(3, userDTO.getPhone());
            prepStatement.setString(4, userDTO.getUserType());
            resultSet = prepStatement.executeQuery();

            if (resultSet.next())
                JOptionPane.showMessageDialog(null, "User already exists");
            else
                addFunction(userDTO, userType);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeResources();
        }
    }

    private void addFunction(UserDTO userDTO, String userType) {
        try {
            String query = "INSERT INTO users (name, location, phone, username, password, usertype) VALUES(?,?,?,?,?,?)";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, userDTO.getFullName());
            prepStatement.setString(2, userDTO.getLocation());
            prepStatement.setString(3, userDTO.getPhone());
            prepStatement.setString(4, userDTO.getUsername());
            prepStatement.setString(5, userDTO.getPassword());
            prepStatement.setString(6, userDTO.getUserType());
            prepStatement.executeUpdate();

            if ("ADMINISTRATOR".equals(userType))
                JOptionPane.showMessageDialog(null, "New administrator added.");
            else
                JOptionPane.showMessageDialog(null, "New employee added.");

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeResources();
        }
    }

    // Method to edit existing user
    public void editUserDAO(UserDTO userDTO) {
        try {
            String query = "UPDATE users SET name=?, location=?, phone=?, usertype=? WHERE username=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, userDTO.getFullName());
            prepStatement.setString(2, userDTO.getLocation());
            prepStatement.setString(3, userDTO.getPhone());
            prepStatement.setString(4, userDTO.getUserType());
            prepStatement.setString(5, userDTO.getUsername());
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Updated Successfully.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeResources();
        }
    }

    // Method to delete existing user
    public void deleteUserDAO(String username) {
        try {
            String query = "DELETE FROM users WHERE username=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, username);
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "User Deleted.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeResources();
            new UsersPage().loadDataSet();
        }
    }

    // Method to retrieve data set to display in the table
    public ResultSet getQueryResult() {
        try {
            String query = "SELECT * FROM users";
            resultSet = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
             //closeResources(); // Commented out since it's not needed here
        }
        return resultSet;
    }

    public ResultSet getUserDAO(String username) {
        try {
            String query = "SELECT * FROM users WHERE username=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, username);
            resultSet = prepStatement.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // closeResources(); // Commented out since it's not needed here
        }
        return resultSet;
    }

    public void getFullName(UserDTO userDTO, String username) {
        try {
            String query = "SELECT * FROM users WHERE username=? LIMIT 1";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, username);
            resultSet = prepStatement.executeQuery();

            String fullName = null;
            if (resultSet.next()) fullName = resultSet.getString(2);
            userDTO.setFullName(fullName);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
             closeResources(); // Commented out since it's not needed here
        }
    }

    public ResultSet getUserLogsDAO() {
        try {
            String query = "SELECT users.name,userlogs.username,in_time,out_time,location FROM userlogs" +
                    " INNER JOIN users on userlogs.username=users.username ORDER BY in_time DESC";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
             //closeResources(); // Commented out since it's not needed here
        }
        return resultSet;
    }

    public void addUserLogin(UserDTO userDTO) {
        try {
            String query = "INSERT INTO userlogs (username, in_time, out_time) VALUES(?,?,?)";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, userDTO.getUsername());
            prepStatement.setString(2, userDTO.getInTime());
            prepStatement.setString(3, userDTO.getOutTime());
            prepStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }

    public ResultSet getPassDAO(String username, String password) {
        try {
            String query = "SELECT password FROM users WHERE username=? AND password=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, username);
            prepStatement.setString(2, password);
            resultSet = prepStatement.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
             closeResources(); // Commented out since it's not needed here
        }
        return resultSet;
    }

    public void changePass(String username, String password) {
        try {
            String query = "UPDATE users SET password=? WHERE username=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, password);
            prepStatement.setString(2, username);
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Password has been changed.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeResources();
        }
    }

    // Method to display data set in tabular form
    public DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        Vector<String> columnNames = new Vector<String>();
        int colCount = metaData.getColumnCount();

        for (int col = 1; col <= colCount; col++) {
            columnNames.add(metaData.getColumnName(col).toUpperCase(Locale.ROOT));
        }

        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        while (resultSet.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int col = 1; col <= colCount; col++) {
//                if(col==2 || col==3)
//                {
//                    String dateuser = String.valueOf(resultSet.getObject(col));
//                    LocalDateTime dateTime = LocalDateTime.parse(dateuser);
//                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm");
//                    String formattedDate = dateTime.format(formatter);
//                    vector.add(formattedDate);
//                }
//                else
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
    
     // Method to display data set in tabular form
    public DefaultTableModel buildUserLogTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        Vector<String> columnNames = new Vector<String>();
        int colCount = metaData.getColumnCount();

        for (int col = 1; col <= colCount; col++) {
            columnNames.add(metaData.getColumnName(col).toUpperCase(Locale.ROOT));
        }

        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        while (resultSet.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int col = 1; col <= colCount; col++) {
                if(col==4 || col==3)
                {
                    String dateuser = String.valueOf(resultSet.getObject(col));
                    LocalDateTime dateTime = LocalDateTime.parse(dateuser);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm");
                    String formattedDate = dateTime.format(formatter);
                    vector.add(formattedDate);
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
    // Close resources method
    private void closeResources() {
        try {
            if (resultSet != null)
                resultSet.close();
            if (prepStatement != null)
                prepStatement.close();
            if(statement != null)
                statement.close();
            if(conn != null)
                conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
