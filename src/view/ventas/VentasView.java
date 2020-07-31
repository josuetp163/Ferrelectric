/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.ventas;

import controller.DBController;
import ferrelectric.sbd.FerrelectricSBD;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import models.Item;
import models.Venta;
import view.MainMenuView;
import view.proveedores.ProveedoresView;
import view.utils.GridComponents;

/**
 *
 * @author xavic
 */
public class VentasView extends GridComponents{
    private VBox root;
    private String[] nombreCampos = {"Num. Factura","Cliente","Cedula","Fecha","Total"};
    private String[] nombreFiltros = {"Nombre","Cedula","Fecha"};
    
    public VentasView() throws FileNotFoundException{
        super("Ventas", new MainMenuView());

    }

    public Parent build() throws FileNotFoundException {
        root = (VBox) super.build(nombreCampos, nombreFiltros);
        
        cargarFilas(dbController.getVentas());
        
        addButtonAction();
        searchButtonAction();
        
        return root;
    }
    
    private Parent buildFilter(List<Venta> ventasFiltrados) throws FileNotFoundException{
        root = (VBox) super.build(nombreCampos, nombreFiltros);
        
        cargarFilas(ventasFiltrados);
        
        addButtonAction();
        searchButtonAction();
        
        return root;
    }
    
    private void cargarFilas(List<Venta> ventas){
        for(Venta venta : ventas){
            System.out.println(venta.getFecha().toString());
            addRow(venta);
        }
    }
    
    private void addButtonAction(){
        addBtn.setOnMouseClicked(e ->{
            try {
                FerrelectricSBD.setScene(new VentaSimpleView().build());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ProveedoresView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    private void searchButtonAction(){
        searchBtn.setOnAction(e -> {
            // Volviendo a cargar todos los registros
            if(input.getText().equals("")){
                root.getChildren().clear();
                contComponents.getChildren().clear();
                try {
                    FerrelectricSBD.setScene(this.build());
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(VentasView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // Cargando registros filtrados
            else if(!(input.getText().equals("")) && filtros.getValue()!=null){
                String filtroElegido = (String) filtros.getValue();
                List<Venta> registroFiltrado = DBController.ventasFiltrados(input.getText().trim(), filtroElegido);
                root.getChildren().clear();
                contComponents.getChildren().clear();
                try {
                    FerrelectricSBD.setScene(this.buildFilter(registroFiltrado));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(VentasView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("Busqueda presionada");
        });
    }

}
