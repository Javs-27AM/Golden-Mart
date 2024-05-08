package controller;
/**
 *
 * @author Javs
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import model.Producto;
import model.Venta;

public class ControlRegistrarVenta {
    public ControlRealizarVenta controlRealizarVenta;

    public ControlRegistrarVenta(ControlRealizarVenta controlRealizarVenta) {
        this.controlRealizarVenta = controlRealizarVenta;
    }
    
  
    
    public void insertarVenta(LocalDate fechaVenta, LocalTime horaVenta, float total) {
        /*
        // Imprimir la información recibida en la consola
        System.out.println("Información recibida para insertar venta:");
        System.out.println("Fecha de venta: " + fechaVenta);
        System.out.println("Hora de venta: " + horaVenta);
        System.out.println("Total: " + total);
        */
        // Insertar la venta en la base de datos
        Venta venta = new Venta(fechaVenta, horaVenta, total);
        venta.insertarVentaEnBD(venta);
    }
    
    public void insertarVentaConDetalle(LocalDate fechaVenta, LocalTime horaVenta, float total, List<Producto> productosVendidos) {
        /*
        // Imprimir la información recibida en la consola
        System.out.println("Información recibida para insertar venta con detalle:");
        System.out.println("Fecha de venta: " + fechaVenta);
        System.out.println("Hora de venta: " + horaVenta);
        System.out.println("Total: " + total);
        System.out.println("Productos vendidos:");
        for (Producto producto : productosVendidos) {
            System.out.println("- " + producto.getNombre() + ": $" + producto.getPrecio());
        }
        */
        // Insertar la venta en la base de datos
        Venta venta = new Venta(fechaVenta, horaVenta, total);
        //venta.insertarVentaEnBD(venta);
        
        // Insertar los detalles de la venta en la base de datos (por ejemplo, los productos vendidos)
        // Aquí deberías implementar la lógica para insertar los detalles de la venta en la base de datos
        // Puedes recorrer la lista de productos vendidos y guardar cada uno en la base de datos
        
        // Ejemplo:
        /*
        for (Producto producto : productosVendidos) {
            // Lógica para insertar cada producto en la base de datos
            // producto.insertarProductoEnBD();
        }
        */
        
        //JOptionPane.showMessageDialog(null, "Venta registrada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
    
}

