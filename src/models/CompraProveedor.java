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
public class CompraProveedor implements Listable {
    private String numFactura;
    private String nombre;
    private String ruc;
    private Date fecha;
    private double total;
    private String cedulaEmpleado;

    public CompraProveedor(String numFactura, String nombre, String ruc, Date fecha, String cedulaEmpleado) {
        this.numFactura = numFactura;
        this.nombre = nombre;
        this.ruc = ruc;
        this.fecha = fecha;
        this.total = total;
        this.cedulaEmpleado = cedulaEmpleado;
    }
    
    public CompraProveedor(String numFactura, String nombre, String ruc, Date fecha, double total, String cedulaEmpleado) {
        this(numFactura, nombre, ruc, fecha, cedulaEmpleado);
        this.total = total;
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

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
        values.add(ruc);
        values.add(fecha.toString());
        values.add(String.format("%.2f", total));
        return values;
    }
    
}
