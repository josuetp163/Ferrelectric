/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.inventario;

import controller.DBController;
import ferrelectric.sbd.FerrelectricSBD;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import models.Item;
import view.MainMenuView;
import view.utils.GridComponents;

/**
 *
 * @author xavic
 */
public class InventarioView extends GridComponents{
    private VBox root;
    private final String[] lbl_Filtros = {"Nombre","Marca","Costo"};
    private final String[] lbl_Nombres = {"Nombre","Marca","Precio Unitario","Cantidad"};
    
    public InventarioView() throws FileNotFoundException{
        super("Inventario", new MainMenuView());
    }

    public Parent build() throws FileNotFoundException {
        root = (VBox) super.build(lbl_Nombres, lbl_Filtros);
        
        cargarFilas(dbController.getItems());
        
        addButtonAction();
        searchButtonAction();
        
        return root;
    }
    
    private Parent buildFilter(List<Item> itemsFiltrados) throws FileNotFoundException{
        root = (VBox) super.build(lbl_Nombres, lbl_Filtros);
        
        cargarFilas(itemsFiltrados);
        
        addButtonAction();
        searchButtonAction();
        
        return root;
    }
    
    private void cargarFilas(List<Item> items){
        for(Item item : items){
            System.out.println(item.getNombre());
            addRow(item);
        }
    }
    
    private void addButtonAction(){
        addBtn.setOnMouseClicked(e ->{
            try {
                FerrelectricSBD.setScene(new ItemView().build());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(InventarioView.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(InventarioView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // Cargando registros filtrados
            else if(!(input.getText().equals("")) && filtros.getValue()!=null){
                String filtroElegido = (String) filtros.getValue();
                List<Item> registroFiltrado = DBController.itemsFiltrados(input.getText().trim(), filtroElegido);
                root.getChildren().clear();
                contComponents.getChildren().clear();
                try {
                    FerrelectricSBD.setScene(this.buildFilter(registroFiltrado));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(InventarioView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("Busqueda presionada");
        });
    }
    
}
