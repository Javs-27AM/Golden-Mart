/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.*;
import javax.swing.JOptionPane;
import view.SesionAdmin;

/**
 *
 * @author Javs
 */

public class ControlSesionAdmin implements ActionListener {
    public SesionAdmin view;

    public ControlSesionAdmin() {
        this.view = new SesionAdmin();
        this.view.jCerrarSesion.addActionListener(this);
        this.view.jRegistrar.addActionListener(this);
        this.view.jGestionar.addActionListener(this);
        this.view.jVentas.addActionListener(this);
        this.view.jRegiCate.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.jCerrarSesion) {
            Object[] options = {"Sí", "No"};
            int opcion = JOptionPane.showOptionDialog(null, "¿Estás seguro que quieres cerrar sesión?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
            if (opcion == JOptionPane.YES_OPTION) {
                // Cerrar sesión y mostrar la vista de login
                ControlAdministrador controlAdministrador = new ControlAdministrador();
                controlAdministrador.view.setVisible(true);
                view.dispose();
            }
        } else if (e.getSource() == view.jRegistrar) {
            // Mostrar la vista para registrar producto
           ControlRegistro controlRegistro = new ControlRegistro();
            view.dispose();
        } else if (e.getSource() == view.jGestionar) {
            ControlGestionarInventario controlGestionarInventario = new ControlGestionarInventario();
            //controlGestionarInventario.view.setVisible(true);
            view.dispose();
         } else if (e.getSource() == view.jVentas) {
            // Mostrar la vista para registrar producto
           ControlGestionVentas controlGestionVentas = new ControlGestionVentas();
           view.dispose();
        }   
         else if (e.getSource() == view.jRegiCate) {
            // Mostrar la vista para registrar producto
           ControlRegistroCategoria controlRegistroCategoria = new ControlRegistroCategoria();
            view.dispose();
        }
    }
}
