/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xavic
 */
public class Connector {
    private static Connection conexion;
    private static final String user = "root";  // COLOCAR SU USUARIO    
    private static final String password = "pass";  // COLOCAR SU CONTRASEÑA
    private static final String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC";
    
    public static void conectar(){
        conexion = null;
        try{
            // Class.forName(driver);
            conexion = DriverManager.getConnection(url, user, password);
            if(conexion != null)
                System.out.println("Conexión establecida");
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void desconectar(){
        conexion = null;
        if(conexion == null)
            System.out.println("Conexión terminada");
    }
    
    public static Connection getConnection(){
        return conexion;
    }
}
