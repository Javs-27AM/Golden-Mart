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
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import view.PagoEfectivo;

public class ControlPagoEfectivo implements ActionListener {
    public PagoEfectivo view;
    private float totalVenta;

    public ControlPagoEfectivo(float totalVenta) {
        this.totalVenta = totalVenta;
        this.view = new PagoEfectivo();
        this.view.jPago.addActionListener(this);
        this.view.jCancelar.addActionListener(this);
        this.view.jTotal.setText(String.valueOf(totalVenta)); // Mostrar el total de la venta en el campo correspondiente
        this.view.jTotal.setEditable(false); // Deshabilitar la edición del campo de texto del total
        this.view.jCambio.setEditable(false); // Deshabilitar la edición del campo de texto del cambio
        view.jPagado.addKeyListener(new KeyAdapter() {
    @Override
    public void keyPressed(KeyEvent e) {
        // Verifica si se presiona la tecla "Enter"
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            // Verificar si el campo de texto jPagado está vacío
            if (!view.jPagado.getText().isEmpty()) {
                // Obtener la cantidad pagada del campo de texto
                float cantidadPagada = Float.parseFloat(view.jPagado.getText());
                // Calcular el cambio
                float cambio = cantidadPagada - totalVenta;
                // Mostrar el cambio en el campo correspondiente
                view.jCambio.setText(String.valueOf(cambio));
                // Aquí puedes agregar la lógica adicional, como registrar el pago en el sistema
                Object[] options = {"Aceptar"};
                JOptionPane optionPane = new JOptionPane("Pago realizado correctamente", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
                JDialog dialog = optionPane.createDialog("Éxito");
                dialog.setVisible(true);

            } else {
                Object[] options = {"Aceptar"};
                JOptionPane optionPane = new JOptionPane("Por favor ingrese la cantidad pagada.", JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
                JDialog dialog = optionPane.createDialog("Advertencia");
                dialog.setVisible(true);

            }
        }
    }
});
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.jPago) {
            // Verificar si el campo de texto jPagado está vacío
            if (!view.jPagado.getText().isEmpty()) {
                // Obtener la cantidad pagada del campo de texto
                float cantidadPagada = Float.parseFloat(view.jPagado.getText());
                // Calcular el cambio
                float cambio = cantidadPagada - totalVenta;
                // Mostrar el cambio en el campo correspondiente
                view.jCambio.setText(String.valueOf(cambio));
                // Aquí puedes agregar la lógica adicional, como registrar el pago en el sistema
                Object[] options = {"Aceptar"};
                JOptionPane optionPane = new JOptionPane("Pago realizado correctamente", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
                JDialog dialog = optionPane.createDialog("Éxito");
                dialog.setVisible(true);

            } else {
                Object[] options = {"Aceptar"};
                JOptionPane optionPane = new JOptionPane("Por favor ingrese la cantidad pagada.", JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
                JDialog dialog = optionPane.createDialog("Advertencia");
                dialog.setVisible(true);

            }
        } else if (e.getSource() == view.jCancelar) {
            // Cerrar la ventana de pago en efectivo
            view.dispose();
        }


    }
}
