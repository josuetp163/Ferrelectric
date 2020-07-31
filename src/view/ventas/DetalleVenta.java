/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.ventas;

import controller.DBController;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import models.Detalle;
import models.Venta;
import view.View;
import view.utils.Header;

/**
 *
 * @author xavic
 */
public class DetalleVenta implements View {
    private VBox root, content;
    private Label lbl_nombre;
    private TableView table;
    private Header header;
    private Venta venta;
    
    public DetalleVenta(Venta venta){
        this.venta = venta;
    }

    @Override
    public Parent build() throws FileNotFoundException {
        root = new VBox();
        content = new VBox();
        lbl_nombre = new Label("Detalle de Venta");
        header = new Header("Detalle");
        header.addBackEventListener(new VentasView());
        
        // SET STYLES
        content.getStyleClass().add("cont_view");
        
        
        table = new TableView();
        ArrayList<String> campos = new ArrayList<>();
        campos.add("Nombre");
        campos.add("Cantidad");
        campos.add("Costo");
        
        table.getStyleClass().add("table_detalle");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        //CREACION DE LAS  COLUNMAS Y SUS CELDAS
        for(String campo : campos){
            TableColumn<String, Venta> column = new TableColumn<>(campo);
            column.setCellValueFactory(new PropertyValueFactory<>(campo));
            table.getColumns().add(column);
        }
       
        //EVITA QUE SE APILEN LOS DATOS CADA VEZ QUE SE CORRE
        table.getItems().clear();
        
        //SE CARGAN LAS FACTURAS Y SE LAS AGREGA A LA TABLA
        for(Detalle detalle: DBController.getDetalles(venta.getNumFactura(), true)){
            table.getItems().add(detalle);
        }
        
        //SE UBICA LA TABLA EN EL CENTRO
        content.getChildren().addAll(lbl_nombre, table);
        root.getChildren().addAll(header.render(), content);
        return root;
    }
}
