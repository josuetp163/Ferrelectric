/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import ferrelectric.sbd.FerrelectricSBD;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import res.PATHS;
import view.inventario.InventarioView;
import view.proveedores.ProveedoresView;
import view.ventas.VentasView;

/**
 *
 * @author xavic
 */
public class MainMenuView implements View{
    private static final String[] OPTS_IMGS = {"factura.png", "inventario.png", "proveedor.png"};
    private static final String[] OPTS_LABELS = {"Ventas", "Inventario", "Proveedores"};
    protected VBox root, cont, imgCont;
    protected HBox optionsContainer, captionCont;
    protected Label option;
    protected GridPane pane;
    protected ImageView img;
    
   
    public MainMenuView(){
        // Initialize
        root = new VBox();
        root.setAlignment(Pos.CENTER);
        optionsContainer = new HBox();
        
        // container settings
        optionsContainer.setAlignment(Pos.CENTER);
        optionsContainer.setSpacing(50);
        optionsContainer.getStyleClass().add("opt-container");
       
    }

    @Override
    public Parent build() throws FileNotFoundException{
        for(int i = 0; i < OPTS_IMGS.length ; i++){
            
            // img settings
            img = new ImageView(new Image(new FileInputStream(PATHS.IMAGE_PATH+OPTS_IMGS[i])));
            img.setFitHeight(256);
            img.setFitWidth(256);
            //img.setPreserveRatio(true);
            
            // img container setup
            imgCont = new VBox();
            imgCont.getChildren().add(img);
            imgCont.getStyleClass().add("img-container");
            
            
            // captions setup
            captionCont = new HBox();
            captionCont.setAlignment(Pos.CENTER);
            captionCont.getStyleClass().add("caption_cont");
            captionCont.getChildren().add(new Label(OPTS_LABELS[i]));
            
            // container setup
            cont = new VBox();
            cont.getStyleClass().add("cont");
            cont.setAlignment(Pos.CENTER);
            cont.getChildren().addAll(imgCont, captionCont);
            
            // hbox container
            optionsContainer.getChildren().add(cont);
            
            addOnClickListeners(i);
        }    
        
        // Adds elements to root parent
        root.getChildren().addAll(optionsContainer);
        
        return root;
    }
    
    public void addOnClickListeners(int i){
        switch (i) {
                case 0:     //VENTAS
                    cont.setOnMouseClicked(e ->{
                        try {
                            FerrelectricSBD.setScene(new VentasView().build());
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(MainMenuView.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    break;
                case 1:     //INVENTARIO
                    cont.setOnMouseClicked(e ->{
                        try {
                            FerrelectricSBD.setScene(new InventarioView().build());
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(MainMenuView.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    break;
                case 2:     //PROVEEDORES
                    cont.setOnMouseClicked(e ->{
                        try {
                            FerrelectricSBD.setScene(new ProveedoresView().build());
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(MainMenuView.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    break;
                default:
                    break;
        } 
    }
    
}
