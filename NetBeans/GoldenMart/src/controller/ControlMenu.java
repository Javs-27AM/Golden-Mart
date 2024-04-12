/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import java.awt.event.*;
import view.Menu;

/**
 *
 * @author Javs
 */
public class ControlMenu implements ActionListener{
    Menu view;
   
    
    public ControlMenu(){
        this.view = new Menu();
        this.view.button_cajero.addActionListener(this);
        this.view.button_administrador.addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        
        if(source == view.button_cajero){
            ControlMenuCliente controlMenuCliente = new ControlMenuCliente();
            view.dispose();
        }
        else if(source == view.button_administrador){
            ControlAdministrador controlAdministrador = new ControlAdministrador();
            view.dispose();
            
        }
    }
    
    
}
