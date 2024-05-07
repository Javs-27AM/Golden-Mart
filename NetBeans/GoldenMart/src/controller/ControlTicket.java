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
//import model.Ticket;
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


}