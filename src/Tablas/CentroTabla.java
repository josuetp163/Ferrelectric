/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tablas;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ADMIN
 */

public class CentroTabla extends Application {
    //ME DA PROBLEMAS LA FECHA
    @Override
    public void start(Stage primaryStage) {
        Tablas tablita = new Tablas();
        
        Scene scene = new Scene(tablita.getRoot());
        primaryStage.setTitle("Tabla de datos");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
