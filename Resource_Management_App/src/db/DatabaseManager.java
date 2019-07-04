package db;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.io.IOException;
import java.sql.SQLException;


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
    
    public boolean addEmployee(String name, String role) throws SQLException {
        try (PreparedStatement psmt = conn.prepareStatement("INSERT INTO employee VALUES (?, ?);")) {
            psmt.setString(1, name);
            psmt.setString(2, role);
            return psmt.execute();
        }
    }
}
