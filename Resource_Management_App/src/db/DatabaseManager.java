package db;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.io.IOException;
import java.sql.SQLException;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author natha
 */
public class DatabaseManager {
    private final Connection conn;
    
    public DatabaseManager(String url) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        conn = DriverManager.getConnection(url);
    }
    
    public void close() throws SQLException {
        conn.close();
    }
        
    public boolean addEmployee(int e_id, String name, String role) throws SQLException {
        boolean success = false;
        
        try (PreparedStatement psmt = conn.prepareStatement("INSERT INTO employee VALUES (?, ?, ?);")) {
            psmt.setInt(1, e_id);
            psmt.setString(2, name);
            psmt.setString(3, role);
            success = psmt.execute();
        }
        
        return success;
    }
    
    public ObservableList<Employee> viewEmployees() throws SQLException {
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        
        try (Statement stmt = conn.createStatement();
                ResultSet results = stmt.executeQuery("SELECT e_id, name, role FROM employee;");) {
            while (results.next()) {
                Employee e = new Employee(results.getInt("e_id"), results.getString("name"), results.getString("role"));
                employees.add(e);
            }
        }
        
        return employees;
    }
}
