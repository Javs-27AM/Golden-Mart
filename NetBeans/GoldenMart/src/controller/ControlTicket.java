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
import model.Producto;
import model.Ticket;
import view.TicketVista;


public class ControlTicket   {
    public TicketVista view;
    public float cantidadPagada;
    public float cambio;// Variable para almacenar la cantidad pagada
    public ControlRealizarVenta controlRealizarVenta;



 public ControlTicket(ControlRealizarVenta controlRealizarVenta) {
        this.controlRealizarVenta = controlRealizarVenta;
        this.view = new TicketVista(null,true);
       
        
    }


    public void mostrarTicket(float cantidadPagada, float cambio, List<Producto> productosVendidos) {
    // Agregar el contenido del ticket al JTextArea jTicketImpreso
    controlRealizarVenta.agregarContenidoEfectivoTicket(productosVendidos, view.jTicketImpreso, cantidadPagada, cambio);
        
        // Mostrar la ventana
        view.setVisible(true);
}
    public void mostrarTicketTarjeta(float cantidadPagada, String ultimosCuatroDigitosTarjeta, List<Producto> productosVendidos) {
    // Agregar el contenido del ticket al JTextArea jTicketImpreso
    controlRealizarVenta.agregarContenidoTarjetaTicket(productosVendidos, view.jTicketImpreso, cantidadPagada, ultimosCuatroDigitosTarjeta);
        
        // Mostrar la ventana
        view.setVisible(true);
}


    
    public void insertarTicketBD(int idVenta) {
    Ticket ticket = new Ticket(); // Crear una instancia de Ticket
    ticket.setIdVenta(idVenta); // Establecer el ID de la venta
    
    // Insertar el ticket en la base de datos
    ticket.crearTicket(idVenta);
}

}