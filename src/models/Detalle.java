/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xavic
 */
public class Detalle implements Listable {
    private String nombre;
    private int cantidad;
    private double costo;

    public Detalle(String nombre, int cantidad, double costo) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.costo = costo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    @Override
    public List<String> getValues() {
        List<String> values = new ArrayList<>();
        values.add(nombre);
        values.add(String.valueOf(cantidad));
        values.add(String.format("%.2f", costo));
        return values;
    }
    
    
    
}
