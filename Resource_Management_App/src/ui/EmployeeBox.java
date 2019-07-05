/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import db.*;

import java.sql.SQLException;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author natha
 */
public class EmployeeBox extends VBox {
    private DatabaseManager dbm;
    private TableView<Employee> tv;
    
    public EmployeeBox(DatabaseManager dbm) {
        super();
        this.dbm = dbm;
        tv = new TableView<>();
        
        this.getChildren().add(addEmployeeBox());
        this.getChildren().add(viewEmployeeBox());        
    }
    
    private VBox viewEmployeeBox() {
        VBox viewEmployees = new VBox();

        Button viewButton = new Button("View All Employees");

        try {
            ObservableList<Employee> employees = dbm.viewEmployees();
            
            tv.setItems(employees);

            TableColumn<Employee, Integer> e_idCol = new TableColumn<>("e_id");
            e_idCol.setCellValueFactory(new PropertyValueFactory("e_id"));

            TableColumn<Employee, String> nameCol = new TableColumn<>("name");
            nameCol.setCellValueFactory(new PropertyValueFactory("name"));

            TableColumn<Employee, String> roleCol = new TableColumn<>("role");
            roleCol.setCellValueFactory(new PropertyValueFactory("role"));
            
            tv.getColumns().addAll(e_idCol, nameCol, roleCol);

        } catch (SQLException exc) {
            Alert a = new Alert(AlertType.ERROR);
            a.setHeaderText(exc.getMessage());
            a.show();
        }
        
        viewEmployees.getChildren().add(tv);
        return viewEmployees;
    }

    
    
    private GridPane addEmployeeBox() {
        GridPane addEmployee = new GridPane();
        addEmployee.setHgap(10);
        addEmployee.setVgap(10);
        addEmployee.setPadding(new Insets(25));
        
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
                    tv.setItems(dbm.viewEmployees());
            } catch (Exception exc) {
                Alert a = new Alert(AlertType.ERROR);
                a.setHeaderText(exc.getMessage());
                a.show();
            }
        });
        
        addEmployee.add(submit, 1, 4);
        
        return addEmployee;
    }
    
    
    
    
}
