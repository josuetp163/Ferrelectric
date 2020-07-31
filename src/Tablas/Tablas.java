/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tablas;

import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author ADMIN
 */
public class Tablas {
    //ME DA PROBLEMAS LA FECHA
    private BorderPane root;
    private TableView tabla;
    
    
    public Tablas(){
        root = new BorderPane();
        Label label = new Label("Tabla de datos");
        root.getChildren().add(label);
        aggContenido();
    }
    
    public void aggContenido(){
        tabla = new TableView();
        ArrayList<String> campos = new ArrayList<>();
        campos.add("No");campos.add("Total");campos.add("Cantidad");campos.add("Cliente");
        /*campos.add("Fecha");*/campos.add("Descripcion");campos.add("Direccion");
        
        //CREACION DE LAS  COLUNMAS Y SUS CELDAS
        for (String campo: campos){
            TableColumn<String, FacturasCo> colum = new TableColumn<>(campo);
            colum.setCellValueFactory(new PropertyValueFactory<>(campo.toLowerCase()));
            tabla.getColumns().add(colum);
        }
        //EVITA QUE SE APILEN LOS DATOS CADA VEZ QUE SE CORRE
        tabla.getItems().clear();
        
        //SE CARGAN LAS FACTURAS Y SE LAS AGREGA A LA TABLA
        CargarFactura.leerArchivo();
        for(FacturasCo fac: CargarFactura.getFacturas()){
            tabla.getItems().add(fac);
        }
        
        //SE UBICA LA TABLA EN EL CENTRO
        root.setCenter(tabla);
    }
    
    public BorderPane getRoot(){
        return root;
    }
    
}
