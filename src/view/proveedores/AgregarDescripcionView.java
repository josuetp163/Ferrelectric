/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.proveedores;

import controller.Alertas;
import controller.DBController;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Detalle;
import res.PATHS;
import view.View;
import view.inventario.ItemView;

/**
 *
 * @author xavic
 */
public class AgregarDescripcionView implements View {
    private VBox root;
    private HBox contButton;
    private GridPane body;
    private Label labelNombre, labelCantidad;
    private TextField inputNombre, inputCantidad;
    private Button newButton, closeButton;
    private List<Detalle> detalles;
    private Stage stage;
    
    public AgregarDescripcionView(Stage stage){
        detalles = new ArrayList<>();
        this.stage = stage;
    }

    @Override
    public Parent build() throws FileNotFoundException {
        root = new VBox();
        newButton = new Button("Agregar");
        closeButton = new Button("Cerrar");
        closeButton.setOnAction(e -> {
            stage.close();
        });
        
        body();
        
        nuevoDetalleButton();
        
        contButton = new HBox(newButton, closeButton);
        contButton.setSpacing(10);
        contButton.setAlignment(Pos.CENTER);
        
        // STYLES
        root.getStyleClass().add("cont_view");
        body.getStyleClass().add("grid_view");
        newButton.getStyleClass().add("save_btn");
        closeButton.getStyleClass().add("save_btn");
        
        root.getChildren().addAll(body, contButton);
        
        return root;
    }
    
    private void body(){
        labelNombre = new Label("Nombre");
        labelCantidad = new Label("Cantidad");
        inputNombre = new TextField();
        inputCantidad = new TextField();
        
        body = new GridPane();
        body.add(labelNombre, 0, 0);
        body.add(labelCantidad, 1, 0);
        body.add(inputNombre, 0, 1);
        body.add(inputCantidad, 1, 1);  
        
        //STYLES
        labelNombre.getStyleClass().add("grid_lbl_save");
        labelCantidad.getStyleClass().add("grid_lbl_save");
        inputNombre.getStyleClass().add("grid_input");
        inputCantidad.getStyleClass().add("grid_input");
    }

    private void guardarDetalle() throws FileNotFoundException{
        Alert alert = Alertas.confirmationAlert("Nuevo Detalle", "Confimar el nuevo detalle", "Nombre: "+inputNombre.getText()+"\nCantidad: "+inputCantidad.getText());
        int idItem = DBController.obtenerIdItem(inputNombre.getText().trim());
        if(idItem==0){
            // HAY QUE IMPLEMENTAR AGREGAR UN NUEVO ITEM CUANDO LA COMPRA
            // TIENE UN ITEM QUE NO SE ENCUENTRA EN EL INVENTARIO
            Stage stage = new Stage();
            Scene scene = new Scene(new ItemView().build(), 1100, 700);
            scene.getStylesheets().add(PATHS.STYLESHEET_PATH);
            stage.setScene(scene);
            stage.setTitle("Crear Item");
            stage.showAndWait();
        }
            
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            detalles.add(new Detalle(inputNombre.getText(), Integer.parseInt(inputCantidad.getText()), 0));
            inputNombre.setText("");
            inputCantidad.setText("");
        }        
    }
    
    private void nuevoDetalleButton(){
        newButton.setOnAction(e -> {
            try {
                guardarDetalle();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(AgregarDescripcionView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    public List<Detalle> devolverListaDetalles(){
        return detalles;
    }
}
