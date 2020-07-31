/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.proveedores;

import controller.Alertas;
import controller.DBController;
import java.io.FileNotFoundException;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.View;

/**
 *
 * @author xavic
 */
public class NuevoProveedorView implements View {
    private VBox root, cont;
    private Label labelNombre, labelRuc, labelTelefono;
    private TextField inputNombre, inputRuc, inputTelefono;
    private Button saveBtn;
    private GridPane body;
    private Stage stage;
    
    public NuevoProveedorView(Stage stage){
        this.stage = stage;
    }

    @Override
    public Parent build() throws FileNotFoundException {
        root = new VBox();
        cont = new VBox();
        Label titulo = new Label("Nuevo Proveedor");
        saveBtn = new Button("Guardar");
        
        // STYLES
        cont.getStyleClass().add("cont_view");
        titulo.getStyleClass().add("logIn_lbl");
        saveBtn.getStyleClass().add("save_btn");
  
        body();
        body.getStyleClass().add("grid_view");
        
        saveButtonAction();
        
        cont.getChildren().addAll(titulo, body, saveBtn);
        
        root.getChildren().add(cont);
        
        return root;
    }
    
    private void body(){
        body = new GridPane();
        labelNombre = new Label("Nombre");
        labelRuc = new Label("Ruc");
        labelTelefono = new Label("Telefono");
        inputNombre = new TextField();
        inputRuc = new TextField();
        inputTelefono = new TextField();
        
        body.add(labelNombre, 0, 0);
        body.add(inputNombre, 1, 0);
        body.add(labelRuc, 0, 1);
        body.add(inputRuc, 1, 1);
        body.add(labelTelefono, 0, 2);
        body.add(inputTelefono, 1, 2);
        
        // styles
        labelNombre.getStyleClass().add("grid_lbl_save");
        labelRuc.getStyleClass().add("grid_lbl_save");
        labelTelefono.getStyleClass().add("grid_lbl_save");
        inputNombre.getStyleClass().add("grid_input");
        inputRuc.getStyleClass().add("grid_input");
        inputTelefono.getStyleClass().add("grid_input");
    }
    
    private void saveButtonAction(){
        saveBtn.setOnAction(e ->{
            DBController.crearProveedor(inputNombre.getText(), inputRuc.getText(), inputTelefono.getText());
            Alert alert = Alertas.informationAlert("Guardado", "Nuevo Proveedor!", "Un nuevo proveedor ha sido guardado en la base de datos.");
            alert.showAndWait();
            stage.close();
        });
    }
    
}
