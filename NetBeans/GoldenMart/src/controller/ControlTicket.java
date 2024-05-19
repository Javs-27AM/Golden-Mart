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
import model.TicketXMLGenerator;
import view.TicketVista;


public class ControlTicket   {
    public TicketVista view;
    public float cantidadPagada;
    public float cambio;// Variable para almacenar la cantidad pagada
    public ControlRealizarVenta controlRealizarVenta;
    public TicketXMLGenerator ticketXMLGenerator;

    


 public ControlTicket(ControlRealizarVenta controlRealizarVenta) {
        this.controlRealizarVenta = controlRealizarVenta;
        this.view = new TicketVista(null,true);
        this.ticketXMLGenerator = new TicketXMLGenerator();
        
    }


    public void mostrarTicket(float cantidadPagada, float cambio, List<Producto> productosVendidos) {
    // Agregar el contenido del ticket al JTextArea jTicketImpreso
    controlRealizarVenta.agregarContenidoEfectivoTicket(productosVendidos, view.jTicketImpreso, cantidadPagada, cambio);
       // String xmlTicket = ticketXMLGenerator.generarXML(productosVendidos, cantidadPagada, cambio, "", true);
        //guardarTicketEnArchivo(xmlTicket);
        // Mostrar la ventana
        view.setVisible(true);
}
    public void mostrarTicketTarjeta(float cantidadPagada, String ultimosCuatroDigitosTarjeta, List<Producto> productosVendidos) {
    // Agregar el contenido del ticket al JTextArea jTicketImpreso
    controlRealizarVenta.agregarContenidoTarjetaTicket(productosVendidos, view.jTicketImpreso, cantidadPagada, ultimosCuatroDigitosTarjeta);
        //String xmlTicket = ticketXMLGenerator.generarXML(productosVendidos, cantidadPagada, 0, ultimosCuatroDigitosTarjeta, false);
        //guardarTicketEnArchivo(xmlTicket);
        // Mostrar la ventana
        view.setVisible(true);
}
    
    public void guardarTicketEnArchivo(String xmlTicket) {
        // Llamar a la función de TicketXMLGenerator para generar y guardar el XML en un archivo
        ticketXMLGenerator.generarYGuardarXML(xmlTicket);
    }

    
    public void insertarTicketBD(int idVenta) {
    Ticket ticket = new Ticket(); // Crear una instancia de Ticket
    ticket.setIdVenta(idVenta); // Establecer el ID de la venta
    
    // Insertar el ticket en la base de datos
    ticket.crearTicket(idVenta);
}

}