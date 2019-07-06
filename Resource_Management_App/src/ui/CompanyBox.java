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
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author natha
 */
public class CompanyBox extends GridPane {
    
    private DatabaseController dbm;
    private TableView tv;
    
    public CompanyBox(DatabaseController dbm) {
        super();
        this.dbm = dbm;
        tv = new TableView();
        tv.setPrefWidth(600);
        
        this.setVgap(20);
        this.setPadding(new Insets(25));
        
        this.add(addCompanyBox(), 0, 0);
        this.add(deleteCompanyBox(), 0, 1);
        this.add(tv, 0, 2);
        try {
            dbm.viewTable("company", tv);
        } catch (SQLException exc) {
            Alert a = new Alert(AlertType.ERROR);
            a.setHeaderText(exc.getMessage());
            a.show();
        }
    }
    
    private GridPane addCompanyBox() {
        GridPane addCompany = new GridPane();
        addCompany.setHgap(10);
        addCompany.setVgap(10);
        
        addCompany.setAlignment(Pos.TOP_LEFT);
        
        TextField c_id = new TextField();
        c_id.setPromptText("Company ID");
        
        TextField name = new TextField();
        name.setPromptText("Company Name");
        TextField address = new TextField();
        address.setPromptText("Address");
        
        addCompany.add(new Label("Add Company"), 0, 0);
        addCompany.add(new Label("c_id"), 0, 1);
        addCompany.add(c_id, 1, 1);
        addCompany.add(new Label("name"), 0, 2);
        addCompany.add(name, 1, 2);
        addCompany.add(new Label("address"), 0, 3);
        addCompany.add(address, 1, 3);
        
        Button submit = new Button("Submit");
        submit.setOnAction(e -> {
            try {
                dbm.addCompany(Integer.parseInt(c_id.getText()), name.getText(), address.getText());
                c_id.clear();
                name.clear();
                address.clear();
                dbm.viewTable("company", tv);
            } catch (Exception exc) {
                Alert a = new Alert(AlertType.ERROR);
                a.setHeaderText(exc.getMessage());
                a.show();
            }
        });
        
        addCompany.add(submit, 1, 4);
        
        return addCompany;
    }
    
    private GridPane deleteCompanyBox() {
        GridPane deleteCompany = new GridPane();
        deleteCompany.setHgap(10);
        deleteCompany.setVgap(10);
        
        TextField c_id = new TextField();
        c_id.setPromptText("Company ID");
        
        Button delete = new Button("Delete");
        delete.setOnAction(e -> {
            try {
                dbm.deleteCompany(Integer.parseInt(c_id.getText()));
                c_id.clear();
                dbm.viewTable("company", tv);
            } catch (Exception exc) {
                Alert a = new Alert(AlertType.ERROR);
                a.setHeaderText(exc.getMessage());
                a.show();
            }
        });
        
        deleteCompany.add(new Label("Delete Company"), 0, 0);
        deleteCompany.add(new Label("c_id"), 0, 1);
        deleteCompany.add(c_id, 1, 1);
        deleteCompany.add(delete, 1, 2);
        
        return deleteCompany;
    }
    
}
