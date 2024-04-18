/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import view.SesionAdmin;
import java.awt.event.*;
import javax.swing.JOptionPane;
/**
 *
 * @author Javs
 */
public class ControlMenuCliente implements ActionListener{
    SesionAdmin view;
    
    public ControlMenuCliente(){
        this.view = new SesionAdmin();
        this.view.jCerrarSesion.addActionListener(this);
        this.view.jRegistrar.addActionListener(this);
        this.view.jGestionar.addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.jCerrarSesion) {
            int opcion = JOptionPane.showConfirmDialog(null, "¿Estás seguro que quieres cerrar sesión?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                // Cerrar sesión y mostrar la vista de login
                ControlMenu controlMenu = new ControlMenu();
                view.dispose();
            }
        } else if (e.getSource() == view.jRegistrar) {
            // Mostrar la vista para registrar producto
           ControlRegistro controlRegistro = new ControlRegistro();
            view.dispose();
        } else if (e.getSource() == view.jGestionar) {
            // Agregar lógica para gestionar inventario
        }
    }
}
