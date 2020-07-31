/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.utils;

import ferrelectric.sbd.FerrelectricSBD;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import res.PATHS;
import view.View;

/**
 *
 * @author xavic
 */
public class Header {
    private AnchorPane header;
    private Label title;
    private ImageView back;
    private HBox backCont;
    private static final String BACK_IMAGE = "flecha-atras.png";
    
    
    public Header(String lbl_title) throws FileNotFoundException{
        title = new Label(lbl_title);
        backCont = new HBox();        
        back = new ImageView(new Image(new FileInputStream(PATHS.IMAGE_PATH+BACK_IMAGE)));
        backCont.getChildren().add(back);
        backCont.setAlignment(Pos.CENTER);

        // Labels settings
        title.setAlignment(Pos.CENTER);
        
        // back settings
        back.setFitHeight(32);
        back.setFitWidth(32);
        back.setPreserveRatio(true);
        
        // styles classes
        backCont.getStyleClass().add("back_cont");
        back.getStyleClass().add("back_btn");
        title.getStyleClass().add("title_lbl");
        
        // Anchor settings
        header = new AnchorPane(backCont, title);
        header.getStyleClass().add("header");
        AnchorPane.setLeftAnchor(backCont, 0.0);
        AnchorPane.setTopAnchor(title, 0.0);
        AnchorPane.setLeftAnchor(title, 50.0);
    }
    
    public Node render(){
        return header;
    }
    
    public void addBackEventListener(View view){
        if(null != view){
            backCont.setOnMouseClicked(e ->{
                try {
                    FerrelectricSBD.setScene(view.build());
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Header.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }else{
            backCont.setVisible(false);
        }
        
    }
}
