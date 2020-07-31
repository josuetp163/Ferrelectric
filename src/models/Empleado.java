/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author xavic
 */
public class Empleado {
    private String cedula;
    private String nombre;
    private String pass;
    private boolean admin;

    public Empleado(String cedula, String nombre, String pass, boolean admin) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.pass = pass;
        this.admin = admin;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    
}
