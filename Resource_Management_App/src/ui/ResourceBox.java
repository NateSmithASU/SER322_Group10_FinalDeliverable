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
public class ResourceBox extends GridPane {
    
    private DatabaseController dbm;
    private TableView tv;
    
    public ResourceBox(DatabaseController dbm) {
        super();
        this.dbm = dbm;
        tv = new TableView();
        tv.setPrefWidth(600);
        
        this.setVgap(20);
        this.setPadding(new Insets(25));
        
        this.add(addResourceBox(), 0, 0);
        this.add(deleteResourceBox(), 0, 1);
        this.add(tv, 0, 2);
        try {
            dbm.viewTable("resource", tv);
        } catch (SQLException exc) {
            Alert a = new Alert(AlertType.ERROR);
            a.setHeaderText(exc.getMessage());
            a.show();
        }
    }
    
    private GridPane addResourceBox() {
        GridPane addResource = new GridPane();
        addResource.setHgap(10);
        addResource.setVgap(10);
        
        addResource.setAlignment(Pos.TOP_LEFT);
        
        TextField resource_id = new TextField();
        resource_id.setPromptText("Resource ID");
        
        TextField type = new TextField();
        type.setPromptText("Resource Type");
        TextField quantity = new TextField();
        quantity.setPromptText("Quantity");
        TextField description = new TextField();
        description.setPromptText("Description");
        
        addResource.add(new Label("Add Resource"), 0, 0);
        addResource.add(new Label("resource_id"), 0, 1);
        addResource.add(resource_id, 1, 1);
        addResource.add(new Label("type"), 0, 2);
        addResource.add(type, 1, 2);
        addResource.add(new Label("quantity"), 0, 3);
        addResource.add(quantity, 1, 3);
        addResource.add(new Label("description"), 0, 4);
        addResource.add(description, 1, 4);
        
        Button submit = new Button("Submit");
        submit.setOnAction(e -> {
            try {
                dbm.addResource(Integer.parseInt(resource_id.getText()), type.getText(), Integer.parseInt(quantity.getText()), description.getText());
                resource_id.clear();
                type.clear();
                description.clear();
                dbm.viewTable("resource", tv);
            } catch (Exception exc) {
                Alert a = new Alert(AlertType.ERROR);
                a.setHeaderText(exc.getMessage());
                a.show();
            }
        });
        
        addResource.add(submit, 1, 5);
        
        return addResource;
    }
    
    private GridPane deleteResourceBox() {
        GridPane deleteResource = new GridPane();
        deleteResource.setHgap(10);
        deleteResource.setVgap(10);
        
        TextField resource_id = new TextField();
        resource_id.setPromptText("Resource ID");
        
        Button delete = new Button("Delete");
        delete.setOnAction(e -> {
            try {
                dbm.deleteResource(Integer.parseInt(resource_id.getText()));
                resource_id.clear();
                dbm.viewTable("resource", tv);
            } catch (Exception exc) {
                Alert a = new Alert(AlertType.ERROR);
                a.setHeaderText(exc.getMessage());
                a.show();
            }
        });
        
        deleteResource.add(new Label("Delete Resource"), 0, 0);
        deleteResource.add(new Label("resource_id"), 0, 1);
        deleteResource.add(resource_id, 1, 1);
        deleteResource.add(delete, 1, 2);
        
        return deleteResource;
    }
    
}
