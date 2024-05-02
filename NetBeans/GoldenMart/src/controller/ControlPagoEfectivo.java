/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author Javs
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import view.PagoEfectivo;


public class ControlPagoEfectivo implements ActionListener {
    public PagoEfectivo view;
    private float totalVenta;
    private float cantidadPagada = 0;
    public ControlRealizarVenta controlRealizarVenta;// Variable para almacenar la cantidad pagada

     public ControlPagoEfectivo(float totalVenta, ControlRealizarVenta controlRealizarVenta) {
        this.totalVenta = totalVenta;
        this.controlRealizarVenta = controlRealizarVenta;
        this.view = new PagoEfectivo();
        this.view.jPago.addActionListener(this);
        this.view.jCancelar.addActionListener(this);
        this.view.jTotal.setText(String.valueOf(totalVenta)); // Mostrar el total de la venta en el campo correspondiente
        this.view.jTotal.setEditable(false); // Deshabilitar la edición del campo de texto del total
        this.view.jCambio.setEditable(false); // Deshabilitar la edición del campo de texto del cambio
        this.view.jPagado.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Verificar si se presiona la tecla "Enter"
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    procesarPago();
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.jPago) {
            procesarPago();
        } else if (e.getSource() == view.jCancelar) {
            view.dispose(); // Cerrar la ventana de pago en efectivo
        }
    }

    public void procesarPago() {
        String cantidadPagadaStr = view.jPagado.getText(); // Obtener la cantidad pagada del campo de texto
        if (!cantidadPagadaStr.isEmpty()) {
            float pago = Float.parseFloat(cantidadPagadaStr);
            if (pago > 0) {
                cantidadPagada += pago; // Acumular la cantidad pagada
                if (cantidadPagada >= totalVenta) {
                    float cambio = cantidadPagada - totalVenta;
                    view.jCambio.setText(String.valueOf(cambio));
                    // Aquí puedes agregar la lógica adicional, como registrar el pago en el sistema
                    Object[] options = {"Aceptar"};
                    JOptionPane optionPane = new JOptionPane("Pago realizado correctamente", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
                    JDialog dialog = optionPane.createDialog("Éxito");
                    dialog.setVisible(true);
                    
                    // Deshabilitar el campo de cantidad pagada
                    view.jPagado.setEnabled(false);
                    // Deshabilitar el botón de cancelar
                    ((JButton) view.jCancelar).setEnabled(false);
                    ((JButton) view.jPago).setEnabled(false);
                    controlRealizarVenta.reiniciarControlador();
                    
                } else {
                    float restante = totalVenta - cantidadPagada;
                    view.jTotal.setText(String.valueOf(restante)); // Mostrar la cantidad restante a pagar
                    view.jPagado.setText(""); // Limpiar el campo de texto para el siguiente pago
                }
            } else {
                Object[] options = {"Aceptar"};
                JOptionPane optionPane = new JOptionPane("El pago debe ser mayor que cero.", JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
                JDialog dialog = optionPane.createDialog("Advertencia");
                dialog.setVisible(true);
            }
        } else {
            Object[] options = {"Aceptar"};
            JOptionPane optionPane = new JOptionPane("Por favor ingrese la cantidad pagada.", JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
            JDialog dialog = optionPane.createDialog("Advertencia");
            dialog.setVisible(true);
        }
    }
    
    
    public void cerrarVentana() {
        // Cerrar la ventana actual
        view.dispose();

        // Abrir el controlador ControlTicket
        //ControlTicket controlTicket = new ControlTicket(/* parámetros */);
        
        // Abrir el controlador ControlVentaBD
        //ControlVentaBD controlVentaBD = new ControlVentaBD(/* parámetros */);

        // Cerrar el controlador que lo llamó (ControlMenuPago)
        if (view.getControladorMenuPago() != null) {
            view.getControladorMenuPago().cerrarVentana();
        }
    }
}