package ui;

import db.DatabaseManager;

import java.sql.SQLException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author natha
 */
public class LoginWindow extends Application {
    
    private DatabaseManager dbm = null;
    
    @Override
    public void start(Stage primaryStage) {
        Dialog<String> loginMenu = createLoginPopup();
        loginMenu.showAndWait();
        String url = loginMenu.getResult();
        dbm = initializeDBM(url);
        

        
        Scene scene = new Scene(mainMenu());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private DatabaseManager initializeDBM(String url) {
        DatabaseManager newDBM = null;
            try {
            newDBM = new DatabaseManager(url);
            Alert a = new Alert(AlertType.CONFIRMATION);
            a.setHeaderText("Connection successful.");
            a.showAndWait();
        } catch (SQLException e) {
            Alert a = new Alert(AlertType.ERROR);
            a.setHeaderText("Could not connect to database.");
            a.setContentText("Please check your login information and try again.");
            a.showAndWait();
            Platform.exit();
        }
        return newDBM;
    }
    
    @Override
    public void stop() {
        if (dbm != null) {
            try {
                dbm.close();
                System.out.println("Database closed successfully.");
            } catch (SQLException e) {
                System.out.println("Error closing database.");
            }
        }
    }
    
    private VBox mainMenu() {
        VBox menu = new VBox();
        menu.setSpacing(10);
        
        Button manageEmployees = new Button("Manage Employees");
        manageEmployees.setOnAction( e -> {
            Stage stage = new Stage();
            Scene scene = new Scene(new EmployeeBox(dbm));
            stage.setScene(scene);
            stage.show();
        });
        
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> {
            Platform.exit();
    });
        
        menu.getChildren().add(manageEmployees);
        menu.getChildren().add(exitButton);
        
        
        return menu;
    }
    
    
    private Dialog<String> createLoginPopup() {

        Dialog<String> login = new Dialog<>();
        login.setHeaderText("Welcome to the Resource Management Database");
        login.setTitle("Login");
        
        GridPane inputFields = new GridPane();
        
        TextField name = new TextField();
        name.setPromptText("username");
        PasswordField password = new PasswordField();
        password.setPromptText("password");
        TextField port = new TextField("3306");
        TextField uri = new TextField("localhost");
        TextField dbname = new TextField("consulting_company");
        
        inputFields.add(new Label("username: "), 0, 0);
        inputFields.add(name, 1, 0);
        inputFields.add(new Label("password: "), 0, 1);
        inputFields.add(password, 1, 1);
        inputFields.add(new Label("database: "), 0, 2);
        inputFields.add(dbname, 1, 2);
        inputFields.add(new Label("server: "), 0, 3);
        inputFields.add(uri, 1, 3);
        inputFields.add(new Label("port: "), 2, 3);
        inputFields.add(port, 3, 3);
        
        inputFields.setVgap(10);
        inputFields.setHgap(10);
        
        login.getDialogPane().setContent(inputFields);
        ButtonType loginButton = new ButtonType("Login", ButtonData.OK_DONE);
        login.getDialogPane().getButtonTypes().addAll(loginButton, ButtonType.CANCEL);
        
        login.setResultConverter( b -> {
            if (b.equals(loginButton))
                return "jdbc:mysql://" + uri.getText() + "/" + dbname.getText() + "?user=" + name.getText() + "&password=" + password.getText();
            return null;
        });
        
        
        return login;
        
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
}
