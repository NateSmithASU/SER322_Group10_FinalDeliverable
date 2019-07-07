/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import db.*;
import java.sql.SQLException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author natha
 */
public class EmployeeBox extends GridPane {
    
    private final int WIDTH = 300;

    private DatabaseController dbm;
    private TableView tv;
    
    public EmployeeBox(DatabaseController dbm) {
        super();
        this.dbm = dbm;
        tv = new TableView();
        
        tv.setPrefWidth(300);
        
        this.setVgap(20);
        this.setPadding(new Insets(25));
        
        this.add(addEmployeeBox(), 0, 0);
        this.add(deleteEmployeeBox(), 0, 1);
        this.add(tv, 0, 2);
        try {
            dbm.viewTable("employee", tv);
        } catch (SQLException exc) {
            Alert a = new Alert(AlertType.ERROR);
            a.setHeaderText(exc.getMessage());
            a.show();
        }
    }
    
    private GridPane addEmployeeBox() {
        GridPane addEmployee = new GridPane();
        addEmployee.setHgap(10);
        addEmployee.setVgap(10);
        
        addEmployee.setAlignment(Pos.TOP_LEFT);
        
        TextField e_id = new TextField();
        e_id.setPromptText("Employee ID");
        
        TextField name = new TextField();
        name.setPromptText("Employee Name");
        TextField role = new TextField();
        role.setPromptText("Employee Role");
        
        addEmployee.add(new Label("Add Employee"), 0, 0);
        addEmployee.add(new Label("e_id"), 0, 1);
        addEmployee.add(e_id, 1, 1);
        addEmployee.add(new Label("name"), 0, 2);
        addEmployee.add(name, 1, 2);
        addEmployee.add(new Label("role"), 0, 3);
        addEmployee.add(role, 1, 3);
        
        Button submit = new Button("Submit");
        submit.setOnAction(e -> {
            try {
                dbm.addEmployee(Integer.parseInt(e_id.getText()), name.getText(), role.getText());
                e_id.clear();
                name.clear();
                role.clear();
                dbm.viewTable("employee", tv);
            } catch (Exception exc) {
                Alert a = new Alert(AlertType.ERROR);
                a.setHeaderText(exc.getMessage());
                a.show();
            }
        });
        
        addEmployee.add(submit, 1, 4);
        
        return addEmployee;
    }
    
    private GridPane deleteEmployeeBox() {
        GridPane deleteEmployee = new GridPane();
        deleteEmployee.setHgap(10);
        deleteEmployee.setVgap(10);
        
        TextField e_id = new TextField();
        e_id.setPromptText("Employee ID");
        
        Button delete = new Button("Delete");
        delete.setOnAction(e -> {
            try {
                dbm.deleteEmployee(Integer.parseInt(e_id.getText()));
                e_id.clear();
                dbm.viewTable("employee", tv);
            } catch (Exception exc) {
                Alert a = new Alert(AlertType.ERROR);
                a.setHeaderText(exc.getMessage());
                a.show();
            }
        });
        
        deleteEmployee.add(new Label("Delete Employee"), 0, 0);
        deleteEmployee.add(new Label("e_id"), 0, 1);
        deleteEmployee.add(e_id, 1, 1);
        deleteEmployee.add(delete, 1, 2);
        
        return deleteEmployee;
    }
    
}
