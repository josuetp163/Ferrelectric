/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tablas;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import models.Listable;

/**
 *
 * @author ADMIN
 */
public class FacturasCo implements Listable{  
    //ME DA PROBLEMAS LA FECHA
    String no;
    double total;
    int cantidad;
    String cliente;
    Date fecha;
    String descripcion;
    String direccion;

    public FacturasCo(String no, double total, int cantidad,String cliente, Date fecha, String descripcion, String direccion) {
        this.no = no;
        this.total = total;
        this.cantidad = cantidad;
        this.cliente = cliente;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.direccion = direccion;
    }        
    
    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    } 
    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }   

    @Override
    public String toString() {
        return "FacturasCo{" + "no=" + no + ", total=" + total + ", cantidad=" + cantidad + ", fecha=" + fecha.toString() + ", nombre=" + cliente + ", descripcion=" + descripcion + ", direccion=" + direccion + '}';
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    @Override
    public List<String> getValues() {
        List<String> lista =  new ArrayList<>();
        lista.add(no);
        lista.add(String.valueOf(total));
        lista.add(String.valueOf(cantidad));
        lista.add(cliente);
        lista.add(new SimpleDateFormat("dd/MM/yyyy").format(fecha));
        lista.add(descripcion);
        lista.add(direccion);
        return lista;
    }
    
    

}
