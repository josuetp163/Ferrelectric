/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author xavic
 */
public class Venta implements Listable{
    private String numFactura;
    private String nombre;
    private String cedula;
    private Date fecha;
    private double total;
    private String cedulaEmpleado;

    public Venta(String numFactura, String nombre, String cedula, Date fecha, double total, String cedulaEmpleado) {
        this(numFactura, nombre, cedula, fecha, cedulaEmpleado);
        this.total = total;
    }
    
    public Venta(String numFactura, String nombre, String cedula, Date fecha, String cedulaEmpleado) {
        this.numFactura = numFactura;
        this.nombre = nombre;
        this.cedula = cedula;
        this.fecha = fecha;
        this.cedulaEmpleado = cedulaEmpleado;
    }

    public String getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(String numFactura) {
        this.numFactura = numFactura;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCedulaEmpleado() {
        return cedulaEmpleado;
    }

    public void setCedulaEmpleado(String cedulaEmpleado) {
        this.cedulaEmpleado = cedulaEmpleado;
    }
    
    @Override
    public List<String> getValues() {
        List<String> values = new ArrayList<>();
        values.add(numFactura);
        values.add(nombre);
        values.add(cedula);
        values.add(fecha.toString()); 
        values.add(String.format("%.2f", total));
        return values;
    }
    
}
