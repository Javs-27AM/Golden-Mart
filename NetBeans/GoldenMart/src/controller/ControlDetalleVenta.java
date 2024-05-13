/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author Javs
 */

import java.util.List;
import model.DetalleVenta;
import model.Producto;
import model.Ticket;


public class ControlDetalleVenta   {
    public float cantidadPagada;
    public float cambio;// Variable para almacenar la cantidad pagada
    public ControlRealizarVenta controlRealizarVenta;



 public ControlDetalleVenta(ControlRealizarVenta controlRealizarVenta) {
        this.controlRealizarVenta = controlRealizarVenta;
       
        
    }


    
    public void insertarDetalleVentaBD(int idVenta, List<Producto> productosVendidos) {
    DetalleVenta detalleVenta = new DetalleVenta(); // Crear una instancia de DetalleVenta
    // Establecer el ID de la venta si es necesario
    
    // Insertar el detalle de venta en la base de datos
    detalleVenta.crearDetalleVenta(idVenta, productosVendidos); // Suponiendo que tienes un método en DetalleVenta para crear el detalle de venta
}


}