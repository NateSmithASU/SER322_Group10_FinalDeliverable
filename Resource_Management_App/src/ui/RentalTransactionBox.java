/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import db.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author natha
 */
public class RentalTransactionBox extends GridPane {
    
    private DatabaseController dbm;
    private TableView tv;
    
    public RentalTransactionBox(DatabaseController dbm) {
        super();
        this.dbm = dbm;
        tv = new TableView();
        tv.setPrefWidth(600);
        
        this.setVgap(20);
        this.setPadding(new Insets(25));
        
        this.add(addRentalTransactionBox(), 0, 0);
        this.add(deleteRentalTransactionBox(), 0, 1);
        this.add(tv, 0, 2);
        try {
            dbm.viewTable("rental_transaction", tv);
        } catch (SQLException exc) {
            Alert a = new Alert(AlertType.ERROR);
            a.setHeaderText(exc.getMessage());
            a.show();
        }
    }
    
    private GridPane addRentalTransactionBox() {
        GridPane addRentalTransaction = new GridPane();
        addRentalTransaction.setHgap(10);
        addRentalTransaction.setVgap(10);
        
        addRentalTransaction.setAlignment(Pos.TOP_LEFT);
        
        TextField rental_id = new TextField();
        rental_id.setPromptText("Rental ID");
        DatePicker date_in = new DatePicker();
        DatePicker date_out = new DatePicker();
        TextField e_id = new TextField();
        e_id.setPromptText("Employee ID");
        TextField c_id = new TextField();
        c_id.setPromptText("Company ID");
        TextField resource_id = new TextField();
        resource_id.setPromptText("Resource ID");
        
        addRentalTransaction.add(new Label("Add Rental Transaction"), 0, 0);
        addRentalTransaction.add(new Label("rental_id"), 0, 1);
        addRentalTransaction.add(rental_id, 1, 1);
        addRentalTransaction.add(new Label("date_in"), 0, 2);
        addRentalTransaction.add(date_in, 1, 2);
        addRentalTransaction.add(new Label("date_out"), 0, 3);
        addRentalTransaction.add(date_out, 1, 3);
        addRentalTransaction.add(new Label("e_id"), 0, 4);
        addRentalTransaction.add(e_id, 1, 4);
        addRentalTransaction.add(new Label("c_id"), 0, 5);
        addRentalTransaction.add(c_id, 1, 5);
        addRentalTransaction.add(new Label("resource_id"), 0, 6);
        addRentalTransaction.add(resource_id, 1, 6);
        
        
        
        Button submit = new Button("Submit");
        submit.setOnAction(e -> {
            LocalDateTime d_in = date_in.getValue().atTime(LocalTime.now());
            LocalDateTime d_out = date_out.getValue().atTime(LocalTime.now());
            try {
                dbm.addRentalTransaction(
                        Integer.parseInt(rental_id.getText()), 
                        d_in,
                        d_out,
                        Integer.parseInt(e_id.getText()), 
                        Integer.parseInt(c_id.getText()),
                        Integer.parseInt(resource_id.getText()));
                rental_id.clear();
                e_id.clear();
                c_id.clear();
                resource_id.clear();
                dbm.viewTable("rental_transaction", tv);
            } catch (Exception exc) {
                Alert a = new Alert(AlertType.ERROR);
                a.setHeaderText(exc.getMessage());
                a.show();
            }
        });
        
        addRentalTransaction.add(submit, 1, 7);
        
        return addRentalTransaction;
    }
    
    private GridPane deleteRentalTransactionBox() {
        GridPane deleteRentalTransaction = new GridPane();
        deleteRentalTransaction.setHgap(10);
        deleteRentalTransaction.setVgap(10);
        
        TextField rental_id = new TextField();
        rental_id.setPromptText("Rental Transaction ID");
        
        Button delete = new Button("Delete");
        delete.setOnAction(e -> {
            try {
                dbm.deleteRentalTransaction(Integer.parseInt(rental_id.getText()));
                rental_id.clear();
                dbm.viewTable("rental_transaction", tv);
            } catch (Exception exc) {
                Alert a = new Alert(AlertType.ERROR);
                a.setHeaderText(exc.getMessage());
                a.show();
            }
        });
        
        deleteRentalTransaction.add(new Label("Delete Rental Transaction"), 0, 0);
        deleteRentalTransaction.add(new Label("rental_id"), 0, 1);
        deleteRentalTransaction.add(rental_id, 1, 1);
        deleteRentalTransaction.add(delete, 1, 2);
        
        return deleteRentalTransaction;
    }
    
}
