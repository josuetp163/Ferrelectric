/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.DBController;
import static controller.DBController.existeUsuario;
import ferrelectric.sbd.FerrelectricSBD;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author xavic
 */
public class LogInView implements View{
    private GridPane root;
    private TextField nombreInput, passwordInput;
    private VBox mainRoot;
    private DBController dbController;
    
    public LogInView(){
        dbController = new DBController();
    }

    @Override
    public Parent build() {
        root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10,10,10,10));
        root.setVgap(10);
        root.setHgap(10);
        
        Label nombreLabel = new Label("Usuario");
        nombreLabel.getStyleClass().add("logIn_lbl");
        GridPane.setConstraints(nombreLabel, 0, 0);
        
        nombreInput = new TextField();
        nombreInput.getStyleClass().add("logIn_input");
        GridPane.setConstraints(nombreInput, 1, 0);
        
        Label passwordLabel = new Label("Contraseña");
        passwordLabel.getStyleClass().add("logIn_lbl");
        GridPane.setConstraints(passwordLabel, 0, 1);
        
        passwordInput = new TextField();
        passwordInput.getStyleClass().add("logIn_input");
        GridPane.setConstraints(passwordInput, 1, 1);
        
        Button logIn = new Button("Ingresar");
        logIn.getStyleClass().add("logIn_btn");
        logInAction(logIn);
        
        root.getChildren().addAll(nombreLabel, nombreInput, passwordLabel, passwordInput);
        
        mainRoot = new VBox();
        mainRoot.getChildren().addAll(root, logIn);
        mainRoot.setSpacing(20);
        mainRoot.setAlignment(Pos.CENTER);
        
        return mainRoot;
    }
    
    private void logInAction(Button btn){
        btn.setOnAction(e ->{
            try {
                if(existeUsuario(nombreInput.getText(), passwordInput.getText()))
                    FerrelectricSBD.setScene(new MainMenuView().build());
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ingreso no concedido");
                    alert.setHeaderText("Error al ingresar");
                    alert.setContentText("Usuario o Contraseña incorrecta");
                    alert.showAndWait();
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(LogInView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(LogInView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
}
