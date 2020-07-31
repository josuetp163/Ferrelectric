/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tablas;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class CargarFactura {
    //ME DA PROBLEMAS LA FECHA
    private static ArrayList<FacturasCo> facturas;    
    
    public static void leerArchivo(){
        facturas = new ArrayList<FacturasCo>();
        String filename = "src/tablas/Facturas.txt";        
        try(BufferedReader bf = new BufferedReader(new FileReader(filename))){           
            String linea;
            while((linea=bf.readLine())!=null){                
                String no = linea.split(",")[0].toUpperCase();
                double total= Double.valueOf(linea.split(",")[1]);
                int cantidad= Integer.valueOf(linea.split(",")[2]);
                String nombre= linea.split(",")[3].toUpperCase();
                //CREA LA FECHA
                LocalDate date = LocalDate.of(Integer.valueOf(linea.split(",")[4].split("-")[2]),
                                                Integer.valueOf(linea.split(",")[4].split("-")[1]),
                                                Integer.valueOf(linea.split(",")[4].split("-")[0]));
                Date fecha = Date.valueOf(date);
                String descripcion= linea.split(",")[5].toUpperCase();
                String direccion= linea.split(",")[6].toUpperCase();
                facturas.add(new FacturasCo(no, total, cantidad, nombre, fecha, descripcion, direccion));                
            }                                             
        }catch(FileNotFoundException fnex){
            System.out.println("No se pudo leer el archivo");
            System.out.println(fnex.getMessage());
        }catch(IOException ioex){
            System.out.println("Ocurrio un error con el archivo");
            System.out.println(ioex.getMessage());
        }                   
    }

    public static ArrayList<FacturasCo> getFacturas() {
        return facturas;
    }
    
    public static void main (String[] args){
        leerArchivo();
        for(FacturasCo fac: facturas){
            System.out.println(fac.toString());
        }        
    }
    
    
}

