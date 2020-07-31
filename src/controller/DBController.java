/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import MySQL.Connector;
import MySQL.Queries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.CompraProveedor;
import models.Detalle;
import models.Empleado;
import models.Item;
import models.Listable;
import models.Venta;
import view.ventas.DetalleVenta;

/**
 *
 * @author xavic
 */
public class DBController {
    private static Connection conn;
    public static Empleado empleado;
    
    
    public DBController(){
        conn = Connector.getConnection();
    }
    
    // YA ESTA IMPLEMENTADA LA VALIDACION DEL USUARIO 
    // LOS USUARIOS ESTAN DENTRO DE LA BASE DE DATOS
    // LA CONTRASEÑA ES LA CEDULA DEL EMPLEADO
    public static boolean existeUsuario(String nombre, String password) throws SQLException{
        PreparedStatement ps = conn.prepareStatement("SELECT * from empleado;");
        ResultSet result = ps.executeQuery();
        while(result.next()){
            String cedula = result.getString("Cedula");
            String name = result.getString("Nombre");
            String pass = result.getString("Pass");
            boolean admin = result.getBoolean("Administrador");
            if(name.equals(nombre) && password.equals(pass) && admin){
                DBController.empleado = new Empleado(cedula, name, pass, admin);
                return true;
            }       
        }
        return false;
    }
    
    public List<Item> getItems(){
        List<Item> items = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(Queries.getItems);
            ResultSet result = ps.executeQuery();
            
            while(result.next()){
                String nombre = result.getString("nombre");
                String marca = result.getString("marca");
                double precioUnitario = result.getDouble("costo");
                int cantidad = result.getInt("cantidad");
                items.add(new Item(nombre, marca, precioUnitario, cantidad));                 
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return items;
    }
    
    public List<CompraProveedor> getComprasProveedor(){
        List<CompraProveedor> proveedores = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(Queries.getCompraProveedor);
            ResultSet result = ps.executeQuery();
            
            while(result.next()){
                String numFactura = String.valueOf(result.getInt("numfactura"));
                String nombre = result.getString("nombre");
                String ruc = result.getString("ruc");
                Date fecha = result.getDate("fecha");
                double total = result.getDouble("total");
                proveedores.add(new CompraProveedor(numFactura, nombre, ruc, fecha, total, empleado.getCedula()));                 
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return proveedores;
    }
    
    public List<Venta> getVentas(){
        List<Venta> ventas = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(Queries.getVentas);
            ResultSet result = ps.executeQuery();
            
            while(result.next()){
                String numFactura = String.valueOf(result.getInt("numfactura"));
                String nombre = result.getString("nombre");
                String cedula = result.getString("cedula");
                Date fecha = result.getDate("fecha");
                double total = result.getDouble("total");
                ventas.add(new Venta(numFactura, nombre, cedula, fecha, total, empleado.getCedula()));                 
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ventas;
    }
    
    // DEVUELVE TRUE SI EXISTE UN ITEM, FALSE EN CASO DE NO EXISTIR
    public static boolean verifyItemInDatabase(Item item){
        int idItem = obtenerIdItem(item.getNombre());
        return idItem!=0;
    }
    
    public static boolean insertItem(Item item){
        try {
            PreparedStatement ps = conn.prepareStatement(Queries.insertItem);
            ps.setString(1, item.getNombre().toUpperCase());
            ps.setDouble(2, item.getPrecioUnidad());
            ps.setString(3, item.getMarca().toUpperCase());
            ps.setInt(4, item.getCantidad());
            ps.execute();
            System.out.println("Item Guardado");
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public static boolean updateItem(Item item){
        try {
            CallableStatement  cs = conn.prepareCall(Queries.storedUpdateItem);
            cs.setInt(1, obtenerIdItem(item.getNombre()));
            cs.setString(2, item.getNombre().toUpperCase());
            cs.setString(3, item.getMarca().toUpperCase());
            cs.setDouble(4, item.getPrecioUnidad());
            cs.setInt(5, item.getCantidad());
            cs.execute();
            System.out.println("Item Guardado");
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public static boolean deleteItem(Item item){
        try {
            CallableStatement  cs = conn.prepareCall(Queries.storedDeleteItem);
            cs.setInt(1, obtenerIdItem(item.getNombre()));
            cs.execute();
            System.out.println("Item Eliminado");
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    // TRUE SI ES UNA VENTA, FALSE SI ES UNA COMPRA
    public static List<Detalle> getDetalles(String numFactura, boolean venta){
        List<Detalle> detalles = new ArrayList<>();
        CallableStatement  cs;
        try{
            if(venta){
            cs = conn.prepareCall(Queries.storedDetallesVenta);
            }else{
                cs = conn.prepareCall(Queries.storedDetallesCompra);
            }
            cs.setInt(1, Integer.valueOf(numFactura));
            ResultSet result = cs.executeQuery();
            while(result.next()){
                String nombre = result.getString("nombre");
                int cantidad = result.getInt("cantidad");
                double costo = result.getDouble("costo");
                Detalle detalle = new Detalle(nombre, cantidad, costo);
                detalles.add(detalle);
            }
        }catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return detalles;
    } 
    
    
    public static void guardarFactura(List<Detalle> detalles, Venta venta){
        try {
            PreparedStatement ps = conn.prepareStatement(Queries.insertFactura);
            ps.setString(1, venta.getNumFactura());
            ps.setString(3, venta.getCedula());
            ps.setString(2, venta.getCedulaEmpleado());
            ps.setDate(4, new java.sql.Date(venta.getFecha().getTime()));
            ps.execute();
            System.out.println("Factura Guardada");
            // GUARDANDO LOS DETALLES DE LA FACTURA 
            for(Detalle detalle : detalles){
                int idItem = obtenerIdItem(detalle.getNombre());
                guardarDetalleVenta(detalle, venta, idItem);
                actualizarCantidadItems(detalle.getNombre(), detalle.getCantidad(), true);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    public static void guardarDetalleVenta(Detalle detalle, Venta venta, int idItem){
        try {
            PreparedStatement ps = conn.prepareStatement(Queries.insertDetalle);
            ps.setInt(1, detalle.getCantidad());
            ps.setInt(2, Integer.parseInt(venta.getNumFactura()));
            ps.setInt(3, idItem);
            ps.execute();
            System.out.println("Detalle de venta en la factura guardada");
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static int obtenerIdItem(String nombreItem){
        try {
            CallableStatement  cs = conn.prepareCall(Queries.storedobtenerItem);
            cs.setString(1, nombreItem);
            cs.registerOutParameter(2, Types.INTEGER);
            cs.execute();
            return cs.getInt(2);
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    // FALSE PARA SUMAR ITEMS, TRUE PARA RESTAR ITEMS
    public static void actualizarCantidadItems(String nombreItem, int cantidad, boolean restar){
        try {
            if(restar){
                PreparedStatement ps = conn.prepareStatement(Queries.storedRestarItems);
                int idItem = obtenerIdItem(nombreItem);
                ps.setInt(1, idItem);
                ps.setInt(2, cantidad);
                ps.execute();
            }else{
                PreparedStatement ps = conn.prepareStatement(Queries.storedSumarItems);
                int idItem = obtenerIdItem(nombreItem);
                ps.setInt(1, idItem);
                ps.setInt(2, cantidad);
                ps.execute();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void crearCliente(String nombre, String cedula, String direccion, String telefono){
        try{
            PreparedStatement ps = conn.prepareStatement(Queries.insertCliente);
            ps.setString(1, cedula);
            ps.setString(2, nombre);
            ps.setString(3, direccion);
            ps.setString(4, telefono);
            ps.execute();
            System.out.println("Nuevo Cliente creado!!");
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static boolean verificarCliente(String nombre, String cedula){
        try {
            CallableStatement  cs = conn.prepareCall(Queries.storedVerificarCliente);
            cs.setString(1, nombre);
            cs.setString(2, cedula);
            cs.registerOutParameter(3, Types.VARCHAR);
            cs.execute();
            String cedulaExiste = cs.getString(3);
            return cedulaExiste!=null;
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static void guardarCompraProveedor(List<Detalle> detalles, CompraProveedor compra){
        try {
            PreparedStatement ps = conn.prepareStatement(Queries.insertCompraProveedor);
            ps.setString(1, compra.getNumFactura());
            ps.setDate(2, new java.sql.Date(compra.getFecha().getTime()));
            ps.setString(4, compra.getCedulaEmpleado());
            ps.setString(3, compra.getRuc());
            ps.execute();
            System.out.println("Factura Guardada");
            // GUARDANDO LOS DETALLES DE LA FACTURA 
            for(Detalle detalle : detalles){
                int idItem = obtenerIdItem(detalle.getNombre());
                guardarDetalleCompra(detalle, compra, idItem);
                actualizarCantidadItems(detalle.getNombre(), detalle.getCantidad(), false);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    public static void guardarDetalleCompra(Detalle detalle, CompraProveedor compra, int idItem){
        try {
            PreparedStatement ps = conn.prepareStatement(Queries.insertDescripcionCompra);
            ps.setInt(1, detalle.getCantidad());
            ps.setInt(2, idItem);
            ps.setInt(3, Integer.parseInt(compra.getNumFactura()));
            ps.execute();
            System.out.println("Detalle de compra en la compra a proveedor guardada");
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void crearProveedor(String nombre, String ruc, String telefono){
        try{
            PreparedStatement ps = conn.prepareStatement(Queries.insertProveedor);
            ps.setString(1, nombre);
            ps.setString(2, telefono);
            ps.setString(3, ruc);
            ps.execute();
            System.out.println("Nuevo Proveedor creado!!");
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static boolean verificarProveedor(String nombre, String ruc){
        try {
            CallableStatement  cs = conn.prepareCall(Queries.storedVerificarProveedor);
            cs.setString(1, nombre);
            cs.setString(2, ruc);
            cs.registerOutParameter(3, Types.VARCHAR);
            cs.execute();
            String cedulaExiste = cs.getString(3);
            return cedulaExiste!=null;
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static List<Item> itemsFiltrados(String valor, String filtro){
        List<Item> items = new ArrayList<>();
        try {
            PreparedStatement ps = null;
            int tipoFiltro = filtrosDeItems(filtro);
            if(tipoFiltro==1) ps = conn.prepareStatement(Queries.filtrarItemPorNombre(valor));
            else if(tipoFiltro==2) ps = conn.prepareStatement(Queries.filtrarItemPorMarca(valor));
            else if(tipoFiltro==3) ps = conn.prepareStatement(Queries.filtrarItemPorCosto(valor));
            ResultSet result = ps.executeQuery(); 
            while(result.next()){
                String nombre = result.getString("nombre");
                String marca = result.getString("marca");
                double precioUnitario = result.getDouble("costo");
                int cantidad = result.getInt("cantidad");
                items.add(new Item(nombre, marca, precioUnitario, cantidad));                 
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return items;
    }
    
    private static int filtrosDeItems(String filtro){
        switch(filtro){
            case "Nombre":
                return 1;
            case "Marca":
                return 2;
            case "Costo":
                return 3;
        }
        return 0;
    }
    
    public static List<Venta> ventasFiltrados(String valor, String filtro){
        List<Venta> ventas = new ArrayList<>();
        try {
            PreparedStatement ps = null;
            int tipoFiltro = filtrosDeVentas(filtro);
            if(tipoFiltro==1) ps = conn.prepareStatement(Queries.filtrarVentaPorNombre(valor));
            else if(tipoFiltro==2) ps = conn.prepareStatement(Queries.filtrarVentaPorCedula(valor));
            else if(tipoFiltro==3) ps = conn.prepareStatement(Queries.filtrarVentaPorFecha(valor));
            ResultSet result = ps.executeQuery();
            
            while(result.next()){
                String numFactura = String.valueOf(result.getInt("numfactura"));
                String nombre = result.getString("nombre");
                String cedula = result.getString("cedula");
                Date fecha = result.getDate("fecha");
                double total = result.getDouble("total");
                ventas.add(new Venta(numFactura, nombre, cedula, fecha, total, empleado.getCedula()));                 
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ventas;
    }
    
    private static int filtrosDeVentas(String filtro){
        switch(filtro){
            case "Nombre":
                return 1;
            case "Cedula":
                return 2;
            case "Fecha":
                return 3;
        }
        return 0;
    }
    
    public static List<CompraProveedor> comprasFiltrados(String valor, String filtro){
        List<CompraProveedor> compras = new ArrayList<>();
        try {
            PreparedStatement ps = null;
            int tipoFiltro = filtrosDeCompras(filtro);
            if(tipoFiltro==1) ps = conn.prepareStatement(Queries.filtrarCompraPorNombre(valor));
            else if(tipoFiltro==2) ps = conn.prepareStatement(Queries.filtrarCompraPorRuc(valor));
            else if(tipoFiltro==3) ps = conn.prepareStatement(Queries.filtrarCompraPorFecha(valor));
            ResultSet result = ps.executeQuery();
            
            while(result.next()){
                String numFactura = String.valueOf(result.getInt("numfactura"));
                String nombre = result.getString("nombre");
                String ruc = result.getString("ruc");
                Date fecha = result.getDate("fecha");
                double total = result.getDouble("total");
                compras.add(new CompraProveedor(numFactura, nombre, ruc, fecha, total, empleado.getCedula()));                 
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return compras;
    }
    
    private static int filtrosDeCompras(String filtro){
        switch(filtro){
            case "Nombre":
                return 1;
            case "Ruc":
                return 2;
            case "Fecha":
                return 3;
        }
        return 0;
    }
    
    public static List<Detalle> getReporte(){
        List<Detalle> listita = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(Queries.getReporte);
            ResultSet result = ps.executeQuery();
            
            while(result.next()){
                String nombre = result.getString("nombre");
                int cantidad = result.getInt("cantidad");
                double costo = result.getDouble("costo");
                listita.add(new Detalle(nombre, cantidad, costo));                 
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listita;                        
    }
    
    public static String getTotalDia(){
        double total = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(Queries.getReporte);
            ResultSet result = ps.executeQuery();
            
            while(result.next()){
                int cantidad = result.getInt("cantidad");
                double costo = result.getDouble("costo");
                total+=cantidad*costo;                 
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return total+"";                        
    }
}
