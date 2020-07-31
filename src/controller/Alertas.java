/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.scene.control.Alert;

/**
 *
 * @author xavic
 */
public class Alertas {
    private static Alert alert;
    
    public static Alert informationAlert(String title, String headerText, String contentText){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert;
    }
    
    public static Alert confirmationAlert(String title, String headerText, String contentText){
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert;
    }
    
    public static Alert errorAlert(String title, String headerText, String contentText){
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert;
    }
    
}
