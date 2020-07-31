use mydb;
select * from factura;
select * from proveedor;
select * from compraproveedor;
select * from detallecompra;
select * from descripcionventa;
select * from item;
select * from cliente;

### DETALLE DE PROVEEDORES
select cp.numFactura as numFactura, p.nombre as nombre, p.ruc as ruc, cp.fecha as fecha, total.total as Total, dc.idDetalleCompra as DetalleCompra
from compraproveedor cp, proveedor p, detallecompra dc, (select cp.numFactura as numFactura, sum(i.costo*dc.cantidad) as total
														from detallecompra dc, item i, compraproveedor cp
														where i.idItem=dc.idItem and cp.numFactura=dc.numFactura
														group by cp.numFactura) as total
where cp.numFactura=dc.numFactura and cp.ruc=p.ruc and total.numFactura=cp.numFactura
group by cp.numFactura;

### DETALLE DE VENTAS A CLIENTES
select f.numFactura as numFactura, c.nombre as nombre, c.cedula as cedula, f.fecha as fecha, total.total as total, dv.idVenta as DetalleVenta
from factura f, cliente c, descripcionventa dv, (select f.numFactura as numFactura, sum(i.costo*dv.cantidad) as total
												from descripcionventa dv, item i, factura f
												where i.idItem=dv.idItem and f.numFactura=dv.numFactura
												group by f.numFactura) as total 
where f.cedula=c.cedula and dv.numFactura=f.numFactura and total.numFactura=f.numFactura
group by f.numFactura;

### COSTO TOTAL POR COMPRA A PROVEEDOR
select cp.numFactura as numFactura, sum(i.costo*dc.cantidad) as total
from detallecompra dc, item i, compraproveedor cp
where i.idItem=dc.idItem and cp.numFactura=dc.numFactura
group by cp.numFactura;

### COSTO TOTAL POR FACTURA
select f.numFactura, sum(i.costo*dv.cantidad) as total
from descripcionventa dv, item i, factura f
where i.idItem=dv.idItem and f.numFactura=dv.numFactura
group by f.numFactura;

### ITEMS POR NOMBRE
select * from item where nombre like '%?%';

### ITEMS POR MARCA
select * from item where marca like '%?%';

### ITEMS POR COSTO
select * from item where costo = ?;

### VENTAS POR NOMBRE
select f.numFactura as numFactura, c.nombre as nombre, c.cedula as cedula, f.fecha as fecha, total.total as total, dv.idVenta as DetalleVenta
from factura f, cliente c, descripcionventa dv, (select f.numFactura as numFactura, sum(i.costo*dv.cantidad) as total
												from descripcionventa dv, item i, factura f
												where i.idItem=dv.idItem and f.numFactura=dv.numFactura
												group by f.numFactura) as total 
where f.cedula=c.cedula and dv.numFactura=f.numFactura and total.numFactura=f.numFactura and c.nombre like '%?%'
group by f.numFactura; 

### VENTAS POR CEDULA
select f.numFactura as numFactura, c.nombre as nombre, c.cedula as cedula, f.fecha as fecha, total.total as total, dv.idVenta as DetalleVenta
from factura f, cliente c, descripcionventa dv, (select f.numFactura as numFactura, sum(i.costo*dv.cantidad) as total
												from descripcionventa dv, item i, factura f
												where i.idItem=dv.idItem and f.numFactura=dv.numFactura
												group by f.numFactura) as total 
where f.cedula=c.cedula and dv.numFactura=f.numFactura and total.numFactura=f.numFactura and c.cedula = ?
group by f.numFactura; 

### VENTAS POR FECHA
select f.numFactura as numFactura, c.nombre as nombre, c.cedula as cedula, f.fecha as fecha, total.total as total, dv.idVenta as DetalleVenta
from factura f, cliente c, descripcionventa dv, (select f.numFactura as numFactura, sum(i.costo*dv.cantidad) as total
												from descripcionventa dv, item i, factura f
												where i.idItem=dv.idItem and f.numFactura=dv.numFactura
												group by f.numFactura) as total 
where f.cedula=c.cedula and dv.numFactura=f.numFactura and total.numFactura=f.numFactura and f.fecha ='2019-09-19'
group by f.numFactura; 

### COMPRAS POR NOMBRE
select cp.numFactura as numFactura, p.nombre as nombre, p.ruc as ruc, cp.fecha as fecha, total.total as Total, dc.idDetalleCompra as DetalleCompra
from compraproveedor cp, proveedor p, detallecompra dc, (select cp.numFactura as numFactura, sum(i.costo*dc.cantidad) as total
														from detallecompra dc, item i, compraproveedor cp
														where i.idItem=dc.idItem and cp.numFactura=dc.numFactura
														group by cp.numFactura) as total
where cp.numFactura=dc.numFactura and cp.ruc=p.ruc and total.numFactura=cp.numFactura and p.nombre like '%%'
group by cp.numFactura;

### COMPRAS POR RUC
select cp.numFactura as numFactura, p.nombre as nombre, p.ruc as ruc, cp.fecha as fecha, total.total as Total, dc.idDetalleCompra as DetalleCompra
from compraproveedor cp, proveedor p, detallecompra dc, (select cp.numFactura as numFactura, sum(i.costo*dc.cantidad) as total
														from detallecompra dc, item i, compraproveedor cp
														where i.idItem=dc.idItem and cp.numFactura=dc.numFactura
														group by cp.numFactura) as total
where cp.numFactura=dc.numFactura and cp.ruc=p.ruc and total.numFactura=cp.numFactura and cp.fecha = ?
group by cp.numFactura;

### COMPRAS POR FECHA
select cp.numFactura as numFactura, p.nombre as nombre, p.ruc as ruc, cp.fecha as fecha, total.total as Total, dc.idDetalleCompra as DetalleCompra
from compraproveedor cp, proveedor p, detallecompra dc, (select cp.numFactura as numFactura, sum(i.costo*dc.cantidad) as total
														from detallecompra dc, item i, compraproveedor cp
														where i.idItem=dc.idItem and cp.numFactura=dc.numFactura
														group by cp.numFactura) as total
where cp.numFactura=dc.numFactura and cp.ruc=p.ruc and total.numFactura=cp.numFactura and p.ruc = ?
group by cp.numFactura;

-- ### INSERTAR UN ITEM
-- INSERT INTO `mydb`.`Item` (`Nombre`, `Costo`, `Marca`,`Cantidad`) VALUES (?, ?, ?, ?);

-- ### INSERTAR UNA FACTURA
-- INSERT INTO `mydb`.`Factura` (`numFactura`, `cedulaEmpleado`, `cedula`, `fecha`) VALUES (?, ?, ?, ?);

-- ### INSERTAR UN DETALLE A UNA FACTURA
-- INSERT INTO `mydb`.`DescripcionVenta` (`Cantidad`, `numFactura`, `idItem`) VALUES (?, ?, ?);

-- ### INSERTAR UN CLIENTE
-- INSERT INTO `mydb`.`Cliente` (`Cedula`, `Nombre`, `Direccion`, `Telefono`) VALUES (?, ?, ?, ?);

-- ### INSERTAR UN PROVEEDOR
-- INSERT INTO `mydb`.`Proveedor` (`Nombre`, `Telefono`, `Ruc`) VALUES (?, ?, ?);

-- ### INSERTAR UNA COMPRA A PROVEEDOR
-- INSERT INTO `mydb`.`CompraProveedor` (`numFactura`, `Fecha`, `ruc`, `cedulaEmpleado`) VALUES (?, ?, ?, ?);

-- ### INSERTAR UNA DESCRIPCION DE COMPRA A PROVEEDOR
-- INSERT INTO `mydb`.`DetalleCompra` (`Cantidad`, `idItem`, `numFactura`) VALUES (?, ?, ?);

### BUSCAR DESCRIPCION DE VENTA A CLIENTES POR NUMFACTURA EN DESCRIPCION DE VENTAS
DELIMITER //
CREATE PROCEDURE obtenerDetalleVentaNumFactura(in numFactura int)
BEGIN
	select i.nombre, dv.cantidad, i.costo from descripcionventa dv, item i
	where dv.numfactura=numFactura and dv.idItem=i.idItem;
END //
DELIMITER ;

### BUSCAR DESCRIPCION DE COMPRA A PROVEEDORES POR NUMFACTURA EN COMPRA PROVEEDOR
DELIMITER //
CREATE PROCEDURE obtenerDetalleCompraNumFactura(in numFactura int)
BEGIN
	select i.nombre, dc.cantidad, i.costo from detallecompra dc, item i
	where dc.numfactura=numFactura and dc.idItem=i.idItem;
END //
DELIMITER ;

### BUSCAR ITEM SEGUN SU NOMBRE
DELIMITER //
CREATE PROCEDURE obtenerIdItem(in nombreItem varchar(40), out id int)
BEGIN
	select idItem into id from item where nombre=nombreItem;
END //
DELIMITER ;

### UPDATE TODOS LOS VALORES DE UN ITEM
DELIMITER //
CREATE PROCEDURE updateItem(in id int, in nombreItem varchar(40), in marcaItem varchar(40), in costoItem double, in cantidadItem int)
BEGIN
	UPDATE item SET nombre=nombreItem,  marca=marcaItem, costo=costoItem, cantidad=cantidadItem WHERE idItem=id;
END //
DELIMITER ;

### ELIMINAR UN ITEM DEL DATABASE
DELIMITER //
CREATE PROCEDURE deleteItem(in id int)
BEGIN
	DELETE FROM item WHERE idItem=id;
END //
DELIMITER ;

### UPDATE CANTIDAD DE ITEMS LUEGO DE GRABAR UNA NUEVA VENTA
DELIMITER //
CREATE PROCEDURE restarCantidadItems(in id int, in cantidadRetirada int)
BEGIN
	UPDATE item SET cantidad = cantidad - cantidadRetirada WHERE idItem = id;
END //
DELIMITER ;

### UPDATE CANTIDAD DE ITEMS LUEGO DE GRABAR UNA NUEVA COMPRA
DELIMITER //
CREATE PROCEDURE sumarCantidadItems(in id int, in cantidadAgregada int)
BEGIN
	UPDATE item SET cantidad = cantidad + cantidadAgregada WHERE idItem = id;
END //
DELIMITER ;

### TRIGGER PARA VERIFICAR QUE LOS DATOS DEL CLIENTE CREADO SEAN VALIDOS
DELIMITER //
CREATE TRIGGER crearCliente BEFORE INSERT ON cliente
FOR EACH ROW
BEGIN 
	IF NEW.direccion="" THEN
		SET NEW.direccion = "None";
	END IF;
END //
DELIMITER ;

DELIMITER //
CREATE TRIGGER crearClienteContinuacion BEFORE INSERT ON cliente
FOR EACH ROW
BEGIN 
	IF NEW.telefono="" THEN
		SET NEW.telefono = "None";
	END IF;
END //
DELIMITER ;

### STORED PROCEDURE PARA SABER SI UN CLIENTE EXISTE, RETORNARA NULL O LA CEDULA DEL CLIENTE
DELIMITER //
CREATE PROCEDURE verificarCliente(in nombreCliente varchar(40), in cedulaCliente varchar(10), out cedulaExiste varchar(10))
BEGIN 
	SELECT cedula into cedulaExiste from cliente where nombre=nombreCliente and cedula=cedulaCliente;
END //
DELIMITER ;

### STORED PROCEDURE PARA SABER SI UN PROVEEDOR EXISTE, RETORNARA NULL O EL RUC DEL PROVEEDOR
DELIMITER //
CREATE PROCEDURE verificarProveedor(in nombreProveedor varchar(40), in rucProveedor varchar(10), out rucExiste varchar(10))
BEGIN 
	SELECT ruc into rucExiste from proveedor where nombre=nombreProveedor and ruc=rucProveedor;
END //
DELIMITER ;

### INDEX EN EL NOMBRE DE UN ITEM PARA UNA BUSQUEDA MAS OPTIMIZADA
CREATE INDEX nombreItem on item(nombre);

### INDEX EN EL NOMBRE DE UN PROVEEDOR PARA UNA BUSQUEDA MAS OPTIMIZADA
### INDEX EN FECHA DE UNA COMPRA AL PROVEEDOR
CREATE INDEX nombreProveedor on proveedor(nombre);
CREATE INDEX fechaCompra on compraproveedor(fecha);

### INDEX EN EL NOMBRE DE UN CLIENTE PARA UNA BUSQUEDA MAS OPTIMIZADA
### INDEX EN FECHA DE UNA FACTURA EN UNA VENTA
CREATE INDEX cliente on cliente(nombre);
CREATE INDEX fechaFactura on factura(fecha);

create view  ReporteDiario as 
	select i.nombre, dv.cantidad, i.costo from descripcionventa dv, item i, factura f
	where dv.numfactura=numFactura and dv.idItem=i.idItem and f.fecha= (select CURDATE());
    