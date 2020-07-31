/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.inventario;

import controller.Alertas;
import controller.DBController;
import java.io.FileNotFoundException;
import java.util.Optional;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import models.Item;
import view.View;
import view.utils.Header;

/**
 *
 * @author xavic
 */
public class ItemView implements View {
    private VBox root, content;
    private Label name, labelName, labelMarca, labelPU, labelCantidad;
    private TextField inputName, inputMarca, inputPU, inputCantidad;
    private GridPane body;
    private Button saveBtn, deleteBtn;
    private Header header;
    private Item item;
    
    public ItemView(){
        // CONSTRUCTOR POR DEFECTO
    }
    
    public ItemView(Item item){
        this.item = item;
    }

    @Override
    public Parent build() throws FileNotFoundException {
        root = new VBox();
        content = new VBox();
        header = new Header("Item");
        header.addBackEventListener(new InventarioView());
        name = new Label("ITEM");
        body = new GridPane();
        saveBtn = new Button("Guardar");
        deleteBtn = new Button("Eliminar");
        
        content.getStyleClass().add("cont_view");
        name.getStyleClass().add("logIn_lbl");
        body.getStyleClass().add("grid_view");
        saveBtn.getStyleClass().add("save_btn");
        deleteBtn.getStyleClass().add("save_btn");
        
        createBody();
        saveButtonAction();
        deleteButtonAction();
        
        content.getChildren().addAll(name, body, saveBtn, deleteBtn);
        root.getChildren().addAll(header.render(), content);
        return root;
    }
    
    private void createBody(){
        labelName = new Label("Nombre");
        labelMarca = new Label("Marca");
        labelPU = new Label("Precio Unitario");
        labelCantidad = new Label("Cantidad");
        inputName = new TextField();
        inputMarca = new TextField();
        inputPU = new TextField();
        inputCantidad = new TextField();
        
        setInputText();
        
        //ES POSIBLE QUE HAYA UNA FORMA MÁS EFICIENTE DE ASIGNAR ESTO
        //POR AHORA SE QUEDA ASÍ
        labelName.getStyleClass().add("grid_lbl_save");
        labelMarca.getStyleClass().add("grid_lbl_save");
        labelPU.getStyleClass().add("grid_lbl_save");
        labelCantidad.getStyleClass().add("grid_lbl_save");
        inputName.getStyleClass().add("grid_input");
        inputMarca.getStyleClass().add("grid_input");
        inputPU.getStyleClass().add("grid_input");
        inputCantidad.getStyleClass().add("grid_input");

        body.add(labelName, 0, 0);
        body.add(inputName, 1, 0);
        body.add(labelMarca, 0, 1);
        body.add(inputMarca, 1, 1);
        body.add(labelPU, 0, 3);
        body.add(inputPU, 1, 3);
        body.add(labelCantidad, 0, 4);
        body.add(inputCantidad, 1, 4);
    }
    
    private void setInputText(){
        if(item != null){
            inputName.setText(item.getNombre());
            inputMarca.setText(item.getMarca());
            inputPU.setText(String.valueOf(item.getPrecioUnidad()));
            inputCantidad.setText(String.valueOf(item.getCantidad()));
        }
    }
    
    private void cleanInputText(){
        inputName.setText("");
            inputMarca.setText("");
            inputPU.setText("");
            inputCantidad.setText("");
    }
    
    private void saveButtonAction(){
        saveBtn.setOnAction(e ->{
            if(inputName.getText().equals("") || inputMarca.getText().equals("") || inputPU.getText().equals("")){
                Alertas.errorAlert("ERROR", "Error de datos", "Los datos ingresados no son correctos").showAndWait();
            }else{
                String nombre = inputName.getText();
                String marca = inputMarca.getText();
                double precioUnitario = Double.parseDouble(inputPU.getText());
                int cantidad = Integer.parseInt(inputCantidad.getText());
                Item item = new Item(nombre, marca, precioUnitario, cantidad);
                if(DBController.verifyItemInDatabase(item)){
                    DBController.updateItem(item);
                }else{
                    DBController.insertItem(item);
                }
                Alertas.informationAlert("SAVE", "Datos guardados", "Los datos ingresados se han guardado en la base de datos").showAndWait();
            }
        });
    }
    
    private void deleteButtonAction(){
        deleteBtn.setOnAction(e -> {
            Alert alert = Alertas.confirmationAlert("Confirmación", "Alerta", "¿Seguro desea eliminar el item?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                DBController.deleteItem(item);
                cleanInputText();
            }    
        });

    }

}
