package db;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Timestamp;

import java.time.Instant;
import java.time.LocalDateTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableView;

/**
 *
 * @author natha
 */
public class DatabaseController {

    private final Connection conn;

    public DatabaseController(String url) throws SQLException {
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

    public void viewTable(String tableName, TableView tv) throws SQLException {
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName + ";")) {
            insertResultSetToTableView(rs, tv);
        }
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

    public boolean deleteEmployee(int e_id) throws SQLException {
        boolean success = false;
        try (PreparedStatement psmt = conn.prepareStatement("DELETE FROM employee WHERE e_id = ?");) {
            psmt.setInt(1, e_id);
            success = psmt.execute();
        }
        return success;
    }
    

    public boolean addCompany(int c_id, String name, String address) throws SQLException {
        boolean success = false;

        try (PreparedStatement psmt = conn.prepareStatement("INSERT INTO company VALUES (?, ?, ?);")) {
            psmt.setInt(1, c_id);
            psmt.setString(2, name);
            psmt.setString(3, address);
            success = psmt.execute();
        }

        return success;
    }

    public boolean deleteCompany(int c_id) throws SQLException {
        boolean success = false;
        try (PreparedStatement psmt = conn.prepareStatement("DELETE FROM company WHERE c_id = ?");) {
            psmt.setInt(1, c_id);
            success = psmt.execute();
        }
        return success;
    }
    
    public boolean addResource(int resource_id, String type, int quantity, String description) throws SQLException {
        boolean success = false;

        try (PreparedStatement psmt = conn.prepareStatement("INSERT INTO resource VALUES (?, ?, ?, ?);")) {
            psmt.setInt(1, resource_id);
            psmt.setString(2, type);
            psmt.setInt(3, quantity);
            psmt.setString(4, description);
            success = psmt.execute();
        }

        return success;
    }

    public boolean deleteResource(int resource_id) throws SQLException {
        boolean success = false;
        try (PreparedStatement psmt = conn.prepareStatement("DELETE FROM resource WHERE resource_id = ?");) {
            psmt.setInt(1, resource_id);
            success = psmt.execute();
        }
        return success;
    }
    
        public boolean addRentalTransaction(int rental_id, LocalDateTime date_in, LocalDateTime date_out, int e_id, int c_id, int resource_id) throws SQLException {
        boolean success = false;

        try (PreparedStatement psmt = conn.prepareStatement("INSERT INTO rental_transaction VALUES (?, ?, ?, ?, ?, ?);")) {
            psmt.setInt(1, rental_id);
            psmt.setTimestamp(2, Timestamp.valueOf(date_in));
            psmt.setTimestamp(3, Timestamp.valueOf(date_out));
            psmt.setInt(4, e_id);
            psmt.setInt(5, c_id);
            psmt.setInt(6, resource_id);
            
            success = psmt.execute();
        }

        return success;
    }

    public boolean deleteRentalTransaction(int rental_id) throws SQLException {
        boolean success = false;
        try (PreparedStatement psmt = conn.prepareStatement("DELETE FROM rental_transaction WHERE rental_id = ?");) {
            psmt.setInt(1, rental_id);
            success = psmt.execute();
        }
        return success;
    }

    private void insertResultSetToTableView(ResultSet rs, TableView tv) throws SQLException {

        ObservableList<ObservableList> tuples = FXCollections.observableArrayList();
        tv.getColumns().clear();

        final int colCount = rs.getMetaData().getColumnCount();

        for (int i = 0; i < colCount; i++) {
            final int j = i;
            TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
            col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {

                @Override
                public ObservableValue<String> call(CellDataFeatures<ObservableList, String> p) {
                    return new SimpleStringProperty(p.getValue().get(j).toString());
                }
            });
            tv.getColumns().add(col);
        }
        while (rs.next()) {
            ObservableList<String> tuple = FXCollections.observableArrayList();
            for (int i = 0; i < colCount; i++) {
                tuple.add(rs.getString(i + 1));
            }
            tuples.add(tuple);
        }
        tv.setItems(tuples);
    }
}
