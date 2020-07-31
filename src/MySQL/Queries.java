/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MySQL;

/**
 *
 * @author xavic
 */
public class Queries {
    public static final String getItems = "SELECT nombre, marca, costo, cantidad FROM item";
    
    
    public static final String getCompraProveedor = "select cp.numFactura as numFactura, p.nombre as nombre, p.ruc as ruc, cp.fecha as fecha, total.total as Total, dc.idDetalleCompra as DetalleCompra\n" +
                                                    "from compraproveedor cp, proveedor p, detallecompra dc, (select cp.numFactura as numFactura, sum(i.costo*dc.cantidad) as total\n" +
                                                    "														from detallecompra dc, item i, compraproveedor cp\n" +
                                                    "														where i.idItem=dc.idItem and cp.numFactura=dc.numFactura\n" +
                                                    "														group by cp.numFactura) as total\n" +
                                                    "where cp.numFactura=dc.numFactura and cp.ruc=p.ruc and total.numFactura=cp.numFactura\n" +
                                                    "group by cp.numFactura";
    
    
    public static final String getVentas = "select f.numFactura as numFactura, c.nombre as nombre, c.cedula as cedula, f.fecha as fecha, total.total as total, dv.idVenta as DetalleVenta\n" +
                                            "from factura f, cliente c, descripcionventa dv, (select f.numFactura as numFactura, sum(i.costo*dv.cantidad) as total\n" +
                                            "												from descripcionventa dv, item i, factura f\n" +
                                            "												where i.idItem=dv.idItem and f.numFactura=dv.numFactura\n" +
                                            "												group by f.numFactura) as total \n" +
                                            "where f.cedula=c.cedula and dv.numFactura=f.numFactura and total.numFactura=f.numFactura\n" +
                                            "group by f.numFactura;";

    
    public static final String insertItem = "INSERT INTO `mydb`.`Item` (`Nombre`, `Costo`, `Marca`,`Cantidad`) VALUES (?, ?, ?, ?);";
    
    public static final String insertFactura = "INSERT INTO `mydb`.`Factura` (`numFactura`, `cedulaEmpleado`, `cedula`, `fecha`) VALUES (?, ?, ?, ?)";
    
    public static final String insertCliente = "INSERT INTO `mydb`.`Cliente` (`Cedula`, `Nombre`, `Direccion`, `Telefono`) VALUES (?, ?, ?, ?)";
    
    public static final String insertDetalle = "INSERT INTO `mydb`.`DescripcionVenta` (`Cantidad`, `numFactura`, `idItem`) VALUES (?, ?, ?)";
    
    public static final String insertDescripcionCompra = "INSERT INTO `mydb`.`DetalleCompra` (`Cantidad`, `idItem`, `numFactura`) VALUES (?, ?, ?)";
    
    public static final String insertProveedor = "INSERT INTO `mydb`.`Proveedor` (`Nombre`, `Telefono`, `Ruc`) VALUES (?, ?, ?)";
    
    public static final String insertCompraProveedor = "INSERT INTO `mydb`.`CompraProveedor` (`numFactura`, `Fecha`, `ruc`, `cedulaEmpleado`) VALUES (?, ?, ?, ?)";
    
    public static final String storedDetallesVenta = "{call obtenerDetalleVentaNumFactura(?)}";
    
    public static final String storedDetallesCompra = "{call obtenerDetalleCompraNumFactura(?)}";
    
    public static final String storedobtenerItem = "{call obtenerIdItem(?, ?)}";
    
    public static final String storedVerificarCliente = "{call verificarCliente(?, ?, ?)}";
    
    public static final String storedRestarItems = "{call restarCantidadItems(?, ?)}";
    
    public static final String storedSumarItems = "{call sumarCantidadItems(?, ?)}";
    
    public static final String storedVerificarProveedor = "{call verificarProveedor(?, ?, ?)}";
    
    public static final String storedUpdateItem = "{call updateItem(?, ?, ?, ?, ?)}";
    
    public static final String storedDeleteItem = "{call deleteItem(?)}";
    
    public static String filtrarItemPorNombre(String nombre){
        return "select * from item where nombre like '%"+nombre+"%'";
    }
    
    public static String filtrarItemPorMarca(String marca){
        return "select * from item where marca like '%"+marca+"%'";
    }
    
    public static String filtrarItemPorCosto(String costo){
        return "select * from item where costo = "+costo;
    }
    
    public static String filtrarVentaPorNombre(String nombre){
        return "select f.numFactura as numFactura, c.nombre as nombre, c.cedula as cedula, f.fecha as fecha, total.total as total, dv.idVenta as DetalleVenta\n" +
                "from factura f, cliente c, descripcionventa dv, (select f.numFactura as numFactura, sum(i.costo*dv.cantidad) as total\n" +
                "												from descripcionventa dv, item i, factura f\n" +
                "												where i.idItem=dv.idItem and f.numFactura=dv.numFactura\n" +
                "												group by f.numFactura) as total \n" +
                "where f.cedula=c.cedula and dv.numFactura=f.numFactura and total.numFactura=f.numFactura and c.nombre like '%"+nombre.trim()+"%'\n" +
                "group by f.numFactura";
    }
    
    public static String filtrarVentaPorCedula(String cedula){
        return "select f.numFactura as numFactura, c.nombre as nombre, c.cedula as cedula, f.fecha as fecha, total.total as total, dv.idVenta as DetalleVenta\n" +
                "from factura f, cliente c, descripcionventa dv, (select f.numFactura as numFactura, sum(i.costo*dv.cantidad) as total\n" +
                "												from descripcionventa dv, item i, factura f\n" +
                "												where i.idItem=dv.idItem and f.numFactura=dv.numFactura\n" +
                "												group by f.numFactura) as total \n" +
                "where f.cedula=c.cedula and dv.numFactura=f.numFactura and total.numFactura=f.numFactura and c.cedula = "+cedula.trim()+"\n" +
                "group by f.numFactura;";
    }
    
    public static String filtrarVentaPorFecha(String fecha){
        return "select f.numFactura as numFactura, c.nombre as nombre, c.cedula as cedula, f.fecha as fecha, total.total as total, dv.idVenta as DetalleVenta\n" +
                "from factura f, cliente c, descripcionventa dv, (select f.numFactura as numFactura, sum(i.costo*dv.cantidad) as total\n" +
                "												from descripcionventa dv, item i, factura f\n" +
                "												where i.idItem=dv.idItem and f.numFactura=dv.numFactura\n" +
                "												group by f.numFactura) as total \n" +
                "where f.cedula=c.cedula and dv.numFactura=f.numFactura and total.numFactura=f.numFactura and f.fecha = "+fecha.trim()+"\n" +
                "group by f.numFactura;";
    }
    
    public static String filtrarCompraPorNombre(String nombre){
        return "select cp.numFactura as numFactura, p.nombre as nombre, p.ruc as ruc, cp.fecha as fecha, total.total as Total, dc.idDetalleCompra as DetalleCompra\n" +
                "from compraproveedor cp, proveedor p, detallecompra dc, (select cp.numFactura as numFactura, sum(i.costo*dc.cantidad) as total\n" +
                "														from detallecompra dc, item i, compraproveedor cp\n" +
                "														where i.idItem=dc.idItem and cp.numFactura=dc.numFactura\n" +
                "														group by cp.numFactura) as total\n" +
                "where cp.numFactura=dc.numFactura and cp.ruc=p.ruc and total.numFactura=cp.numFactura and p.nombre like '%"+nombre.trim()+"%'\n" +
                "group by cp.numFactura";
    }
    
    public static String filtrarCompraPorRuc(String ruc){
        return "select cp.numFactura as numFactura, p.nombre as nombre, p.ruc as ruc, cp.fecha as fecha, total.total as Total, dc.idDetalleCompra as DetalleCompra\n" +
                "from compraproveedor cp, proveedor p, detallecompra dc, (select cp.numFactura as numFactura, sum(i.costo*dc.cantidad) as total\n" +
                "														from detallecompra dc, item i, compraproveedor cp\n" +
                "														where i.idItem=dc.idItem and cp.numFactura=dc.numFactura\n" +
                "														group by cp.numFactura) as total\n" +
                "where cp.numFactura=dc.numFactura and cp.ruc=p.ruc and total.numFactura=cp.numFactura and p.ruc = "+ruc.trim()+"\n" +
                "group by cp.numFactura";
    }
    
    public static String filtrarCompraPorFecha(String fecha){
        return "select cp.numFactura as numFactura, p.nombre as nombre, p.ruc as ruc, cp.fecha as fecha, total.total as Total, dc.idDetalleCompra as DetalleCompra\n" +
                "from compraproveedor cp, proveedor p, detallecompra dc, (select cp.numFactura as numFactura, sum(i.costo*dc.cantidad) as total\n" +
                "														from detallecompra dc, item i, compraproveedor cp\n" +
                "														where i.idItem=dc.idItem and cp.numFactura=dc.numFactura\n" +
                "														group by cp.numFactura) as total\n" +
                "where cp.numFactura=dc.numFactura and cp.ruc=p.ruc and total.numFactura=cp.numFactura and cp.fecha = "+fecha.trim()+"\n" +
                "group by cp.numFactura";
    }
    
    public static String getReporte="select * from ReporteDiario";
    public static String getTotal = "select sum(costo) from ReporteDiario";

}
