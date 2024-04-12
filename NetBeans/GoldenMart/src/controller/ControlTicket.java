/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Ticket;
import view.Display;
import view.InterfaceTicket1;

/**
 *
 * @author akram
 */
public class ControlTicket implements ActionListener{
    
    private Display view1; 
    private InterfaceTicket1 view2; 
    private Ticket model; 
    
    public ControlTicket(){
        this.view1 = new Display();
        Frame parentFrame = new Frame();
        this.view2  = new InterfaceTicket1(parentFrame, true);
        this.model = new Ticket(); 
        
        this.view1.ticketButton.addActionListener(this);
        verificarDisponibilidad(); 
    }
    
    public void generarTicket(){
        view1.setTitle("Ecopark-Display");
        view1.setLocationRelativeTo(null); 
    }
    
    public void agregaDatos(){
            String datos = model.obtenerTicketDesdeBD(); 
            view2.ticketDatos.setText(datos); 
            view2.ticketDatos.setEditable(false);
            view2.ticketDatos.setBorder(null);
    }
    
    public void actionPerformed(ActionEvent e){
        
            if(model.verificarLugaresDisponibles()){
                model.obtenerTicket();         
                //Abre dialogo de ticket 
                
                //no hacer visible el ticket sy ya no hay lugare y cambiar la pagina principal
                agregaDatos(); 
                view2.setVisible(true);
            }else{
              
                view2.setVisible(false);
                
                //agregue esto
                view1.Mensaje.setVisible(true); 
                view1.ticketButton.setEnabled(false);
                
            }
    }
    
    private void verificarDisponibilidad() {
        if (!model.verificarLugaresDisponibles()) {
            view1.Mensaje.setVisible(true);
            view1.ticketButton.setEnabled(false);
        } else {
            view1.Mensaje.setVisible(false);
        }
    }
    
    
}
