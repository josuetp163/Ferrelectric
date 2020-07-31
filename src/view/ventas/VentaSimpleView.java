/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.ventas;

import controller.Alertas;
import controller.DBController;
import ferrelectric.sbd.FerrelectricSBD;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Venta;
import res.PATHS;
import view.View;
import view.inventario.ItemView;
import view.utils.Header;

/**
 *
 * @author xavic
 */
public class VentaSimpleView implements View {
    private VBox root, content;
    private Label name, labelNumFactura, lableCliente, labelCedula, labelFecha;
    private TextField inputNumFactura, inputCliente, inputCedula, inputFecha;
    private GridPane body;
    private Button saveBtn, agregarDetalles;
    private Header header;
    private Venta venta;
    private AgregarDetalleView adv;

    @Override
    public Parent build() throws FileNotFoundException {
        root = new VBox();
        content = new VBox();
        header = new Header("Venta");
        header.addBackEventListener(new VentasView());
        name = new Label("Venta");
        body = new GridPane();
        agregarDetalles = new Button("Añadir detalles");
        saveBtn = new Button("Guardar");
        
        content.getStyleClass().add("cont_view");
        name.getStyleClass().add("logIn_lbl");
        body.getStyleClass().add("grid_view");
        saveBtn.getStyleClass().add("save_btn");
        agregarDetalles.getStyleClass().add("save_btn");
        
        createBody();
        saveButtonAction();
        addDetalleButtonAction();
        
        content.getChildren().addAll(name, body, agregarDetalles, saveBtn);
        root.getChildren().addAll(header.render(), content);
        return root;
    }
    
    private void createBody(){
        labelNumFactura = new Label("Num Factura");
        lableCliente = new Label("Cliente");
        labelCedula = new Label("Cedula");
        labelFecha = new Label("Fecha");
        inputNumFactura = new TextField();
        inputCliente = new TextField();
        inputCedula = new TextField();
        inputFecha = new TextField();
        
        //ES POSIBLE QUE HAYA UNA FORMA MÁS EFICIENTE DE ASIGNAR ESTO
        //POR AHORA SE QUEDA ASÍ
        labelNumFactura.getStyleClass().add("grid_lbl_save");
        lableCliente.getStyleClass().add("grid_lbl_save");
        labelCedula.getStyleClass().add("grid_lbl_save");
        labelFecha.getStyleClass().add("grid_lbl_save");
        inputNumFactura.getStyleClass().add("grid_input");
        inputCliente.getStyleClass().add("grid_input");
        inputCedula.getStyleClass().add("grid_input");
        inputFecha.getStyleClass().add("grid_input");

        body.add(labelNumFactura, 0, 0);
        body.add(inputNumFactura, 1, 0);
        body.add(lableCliente, 0, 1);
        body.add(inputCliente, 1, 1);
        body.add(labelCedula, 0, 3);
        body.add(inputCedula, 1, 3);
        body.add(labelFecha, 0, 4);
        body.add(inputFecha, 1, 4);
    }
    
    private void saveButtonAction(){
        saveBtn.setOnAction(e ->{
            boolean existeCliente = DBController.verificarCliente(inputCliente.getText().trim(), inputCedula.getText().trim());
            if(!existeCliente){
                try {
                    Alertas.errorAlert("ERROR", "Error de datos", "Los datos ingresados no son correctos").showAndWait();
                    abrirNuevoClienteView();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(VentaSimpleView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                venta = createVenta();
                DBController.guardarFactura(adv.devolverListaDetalles(), venta);
                Alertas.informationAlert("SAVE", "Datos guardados", "Los datos ingresados se han guardado en la base de datos").showAndWait();
            }
        });
    }
    
    private void addDetalleButtonAction(){
        agregarDetalles.setOnAction(e -> {
            try {
                boolean existeCliente = DBController.verificarCliente(inputCliente.getText().trim(), inputCedula.getText().trim());
                if(!existeCliente){
                    Alertas.errorAlert("ERROR", "Error de datos", "Los datos ingresados no son correctos").showAndWait();
                    abrirNuevoClienteView();
                }else{ 
                    Stage stage = new Stage();
                    adv = new AgregarDetalleView(stage); 
                    Scene scene = new Scene(adv.build(), 1000, 400);
                    scene.getStylesheets().add(PATHS.STYLESHEET_PATH);
                    stage.setScene(scene);
                    stage.setTitle("Agregar detalles");
                    stage.showAndWait();
                }
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(VentaSimpleView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    private void abrirNuevoClienteView() throws FileNotFoundException{
        Stage stage = new Stage();
        NuevoClienteView ncv = new NuevoClienteView(stage); 
        Scene scene = new Scene(ncv.build(), 1100, 600);
        scene.getStylesheets().add(PATHS.STYLESHEET_PATH);
        stage.setScene(scene);
        stage.setTitle("Nuevo Cliente");
        stage.showAndWait();
    }
    
    private Venta createVenta(){
        String numFactura = inputNumFactura.getText();
        String nombreCliente = inputCliente.getText();
        String cedula = inputCedula.getText();
        LocalDate fecha = LocalDate.parse(inputFecha.getText());
        String cedulaEmpleado = DBController.empleado.getCedula();

        return(new Venta(numFactura, nombreCliente, cedula, Date.valueOf(fecha), cedulaEmpleado));
    }
}
