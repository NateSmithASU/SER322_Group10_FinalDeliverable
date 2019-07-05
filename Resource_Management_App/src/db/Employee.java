/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.collections.FXCollections;

/**
 *
 * @author natha
 */
public class Employee {
    private IntegerProperty e_id;
    private StringProperty name, role;
    
    public Employee(int e_id, String name, String role) {
        this.e_id = new SimpleIntegerProperty(this, "e_id");
        this.e_id.set(e_id);
        
        this.name = new SimpleStringProperty(this, "name");
        this.name.set(name);
        
        this.role = new SimpleStringProperty(this, "role");
        this.role.set(role);
    }
    
    public StringProperty nameProperty() {
        return name;
    }
    
    public StringProperty roleProperty() {
        return role;
    }
    
    public IntegerProperty e_idProperty() {
        return e_id;
    }
}
