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
import java.time.LocalDate;
import java.time.LocalTime;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import view.PagoEfectivo;


public class ControlPagoEfectivo implements ActionListener {
    public PagoEfectivo view;
    public int idVenta;    
    public float totalVenta;
    public LocalDate fechaVenta; // Cambiar el tipo de dato de Date a LocalDate
    public LocalTime horaVenta;
    public float cantidadPagada = 0;// Variable para almacenar la cantidad pagada
    public ControlRealizarVenta controlRealizarVenta;
    
     public ControlPagoEfectivo(float totalVenta, ControlRealizarVenta controlRealizarVenta) {
        this.totalVenta = totalVenta;
        this.controlRealizarVenta = controlRealizarVenta;
        this.view = new PagoEfectivo();
        this.view.jPago.addActionListener(this);
        this.view.jCancelar.addActionListener(this);
        this.view.jTotal.setText(String.valueOf(totalVenta)); // Mostrar el total de la venta en el campo correspondiente
        this.view.jTotal.setEditable(false); // Deshabilitar la edición del campo de texto del total
        this.view.jCambio.setEditable(false); // Deshabilitar la edición del campo de texto del cambio
        
        // Filtrar entrada para aceptar solo números
        ((AbstractDocument) view.jPagado.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
                if (newText.matches("\\d*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        // Deshabilitar pegado mediante Ctrl+V
        view.jPagado.setTransferHandler(null);

        
        
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
                    fechaVenta = LocalDate.now();
                    horaVenta = LocalTime.now();
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
                     // Mostrar la cantidad pagada y el cambio en el ticket
                    //controlRealizarVenta.agregarContenidoEfectivo(controlRealizarVenta.getProductosVendidos(), controlRealizarVenta.view.jTicket, cantidadPagada, cambio);
                    controlRealizarVenta.agregarContenidoEfectivoTicket(controlRealizarVenta.getProductosVendidos(), controlRealizarVenta.view.jTicket, cantidadPagada, cambio);
                    
                    ControlTicket controlTicket = new ControlTicket(controlRealizarVenta);
                    controlTicket.mostrarTicket(cantidadPagada, cambio, controlRealizarVenta.getProductosVendidos());

                    ControlRegistrarVenta controlRegistrarVenta = new ControlRegistrarVenta(controlRealizarVenta);
                    idVenta = controlRegistrarVenta.insertarVenta(fechaVenta, horaVenta, totalVenta);
                    
                    controlTicket.insertarTicketBD(idVenta);
                    
                    ControlDetalleVenta controlDetalleVenta = new ControlDetalleVenta(controlRealizarVenta);
                    controlDetalleVenta.insertarDetalleVentaBD(idVenta, controlRealizarVenta.getProductosVendidos());
                    //controlRegistrarVenta.insertarVentaConDetalle(fechaVenta, horaVenta, totalVenta, controlRealizarVenta.getProductosVendidos());
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
        
    }
}