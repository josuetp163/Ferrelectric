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
public class Item implements Listable {
    private String nombre;
    private String marca;
    private double precioUnidad;
    private int cantidad;

    public Item(String nombre, String marca, double precioUnidad, int cantidad) {
        this.nombre = nombre;
        this.marca = marca;
        this.precioUnidad = precioUnidad;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPrecioUnidad() {
        return precioUnidad;
    }

    public void setPrecioUnidad(double precioUnidad) {
        this.precioUnidad = precioUnidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public List<String> getValues() {
        List<String> values = new ArrayList<>();
        values.add(nombre);
        values.add(marca);
        values.add(String.valueOf(precioUnidad));
        values.add(String.valueOf(cantidad));
        return values;
    }
    
    
}
