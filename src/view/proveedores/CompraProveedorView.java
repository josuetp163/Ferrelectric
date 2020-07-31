/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.proveedores;

import controller.Alertas;
import controller.DBController;
import ferrelectric.sbd.FerrelectricSBD;
import java.io.FileNotFoundException;
import java.time.LocalDate;
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
import models.CompraProveedor;
import res.PATHS;
import view.View;
import view.inventario.ItemView;
import view.utils.Header;
import view.ventas.VentaSimpleView;

/**
 *
 * @author xavic
 */
public class CompraProveedorView implements View {
    private VBox root, content;
    private Label name, labelNumFactura, labelProveedor, labelRuc, labelFecha;
    private TextField inputNumFactura, inputProveedor, inputRuc, inputFecha;
    private GridPane body;
    private Button saveBtn, agregarDetalles;
    private Header header;
    private CompraProveedor compra;
    private AgregarDescripcionView adc;

    @Override
    public Parent build() throws FileNotFoundException {
        root = new VBox();
        content = new VBox();
        header = new Header("Compra Proveedor");
        header.addBackEventListener(new ProveedoresView());
        name = new Label("Compra a Proveedor");
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
        labelProveedor = new Label("Proveedor");
        labelRuc = new Label("RUC");
        labelFecha = new Label("Fecha");
        inputNumFactura = new TextField();
        inputProveedor = new TextField();
        inputRuc = new TextField();
        inputFecha = new TextField();
        
        //ES POSIBLE QUE HAYA UNA FORMA MÁS EFICIENTE DE ASIGNAR ESTO
        //POR AHORA SE QUEDA ASÍ
        labelNumFactura.getStyleClass().add("grid_lbl_save");
        labelProveedor.getStyleClass().add("grid_lbl_save");
        labelRuc.getStyleClass().add("grid_lbl_save");
        labelFecha.getStyleClass().add("grid_lbl_save");
        inputNumFactura.getStyleClass().add("grid_input");
        inputProveedor.getStyleClass().add("grid_input");
        inputRuc.getStyleClass().add("grid_input");
        inputFecha.getStyleClass().add("grid_input");

        body.add(labelNumFactura, 0, 0);
        body.add(inputNumFactura, 1, 0);
        body.add(labelProveedor, 0, 1);
        body.add(inputProveedor, 1, 1);
        body.add(labelRuc, 0, 3);
        body.add(inputRuc, 1, 3);
        body.add(labelFecha, 0, 4);
        body.add(inputFecha, 1, 4);
    }
    
    private void saveButtonAction(){
        saveBtn.setOnAction(e ->{
            boolean existeProveedor = DBController.verificarProveedor(inputProveedor.getText(), inputRuc.getText());
            if(!existeProveedor){
                Alertas.errorAlert("ERROR", "Error de datos", "Los datos ingresados no son correctos").showAndWait();
                try {
                    abrirNuevoProveedorView();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(CompraProveedorView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                compra = createCompra();
                DBController.guardarCompraProveedor(adc.devolverListaDetalles(), compra);
                Alertas.informationAlert("SAVE", "Datos guardados", "Los datos ingresados se han guardado en la base de datos").showAndWait();
            }
        });
    }
    
    private void addDetalleButtonAction(){
        agregarDetalles.setOnAction(e -> {
            try {
                boolean existeProveedor = DBController.verificarProveedor(inputProveedor.getText(), inputRuc.getText());
                if(!existeProveedor){
                    Alertas.errorAlert("ERROR", "Error de datos", "Los datos ingresados no son correctos").showAndWait();
                    abrirNuevoProveedorView();   
                }else{
                    //existeProveedor();
                    Stage stage = new Stage();
                    adc = new AgregarDescripcionView(stage); 
                    Scene scene = new Scene(adc.build(), 1200, 400);
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
    
    private CompraProveedor createCompra(){
        String numFactura = inputNumFactura.getText();
        String proveedor = inputProveedor.getText();
        String ruc = inputRuc.getText();
        LocalDate fecha = LocalDate.parse(inputFecha.getText());
        String cedulaEmpleado = DBController.empleado.getCedula();
        
        return (new CompraProveedor(numFactura, proveedor, ruc, java.sql.Date.valueOf(fecha), cedulaEmpleado));
    }
    
    private void abrirNuevoProveedorView() throws FileNotFoundException{
        Stage stage = new Stage();
        NuevoProveedorView ncv = new NuevoProveedorView(stage); 
        Scene scene = new Scene(ncv.build(), 1100, 600);
        scene.getStylesheets().add(PATHS.STYLESHEET_PATH);
        stage.setScene(scene);
        stage.setTitle("Nuevo Proveedor");
        stage.showAndWait();
    }
}