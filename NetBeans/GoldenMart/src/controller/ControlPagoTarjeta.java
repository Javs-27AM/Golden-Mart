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
import view.PagoTarjeta;

public class ControlPagoTarjeta implements ActionListener {
    public PagoTarjeta view;
    private float totalVenta;

    public ControlPagoTarjeta(float totalVenta) {
        this.totalVenta = totalVenta;
        this.view = new PagoTarjeta();
        this.view.jPago.addActionListener(this);
        this.view.jCancelar.addActionListener(this);
        // Configurar las validaciones de los campos
        configurarValidaciones();
        // Mostrar el total de la venta en el campo correspondiente
        this.view.jTotal.setText(String.valueOf(totalVenta));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.jPago) {
            // Realizar el pago solo si los campos son válidos
            if (validarCampos()) {
                // Aquí irá la lógica para procesar el pago con tarjeta
                Object[] options = {"Aceptar"};
                JOptionPane optionPane = new JOptionPane("Pago realizado correctamente", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
                JDialog dialog = optionPane.createDialog("Éxito");
                dialog.setVisible(true);
            } else {
                Object[] options = {"Aceptar"};
                JOptionPane optionPane = new JOptionPane("Por favor ingrese datos válidos para realizar el pago.", JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
                JDialog dialog = optionPane.createDialog("Advertencia");
                dialog.setVisible(true);
            }
        } else if (e.getSource() == view.jCancelar) {
            // Cerrar la ventana de pago con tarjeta
            view.dispose();
        }
    }

    // Método para configurar las validaciones de los campos
    private void configurarValidaciones() {
    // Validar el número de tarjeta (debe tener exactamente 16 dígitos y solo números)
    view.jTarjeta.addKeyListener(new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if (!Character.isDigit(c) || view.jTarjeta.getText().length() >= 16) {
                e.consume();
            }
        }
    });

    // Validar el nombre de la tarjeta (solo letras)
    view.jNombre.addKeyListener(new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if (!Character.isLetter(c)) {
                e.consume();
            }
        }
    });

    // Validar el CVV (debe tener exactamente 3 dígitos y solo números)
    view.jCVV.addKeyListener(new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if (!Character.isDigit(c) || view.jCVV.getPassword().length >= 3) {
                e.consume();
            }
        }
    });
    
}

// Método para validar los campos de la tarjeta
private boolean validarCampos() {
    // Validar el número de tarjeta (debe tener exactamente 16 dígitos)
    String numeroTarjeta = view.jTarjeta.getText().trim();
    if (numeroTarjeta.length() != 16) {
        Object[] options = {"Aceptar"};
        JOptionPane optionPane = new JOptionPane("El número de tarjeta debe tener exactamente 16 dígitos.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
        JDialog dialog = optionPane.createDialog("Error de Validación");
        dialog.setVisible(true);
        return false;
    }

    // Validar el nombre de la tarjeta (no debe estar vacío)
    String nombreTarjeta = view.jNombre.getText().trim();
    if (nombreTarjeta.isEmpty()) {
        Object[] options = {"Aceptar"};
        JOptionPane optionPane = new JOptionPane("Por favor ingrese el nombre de la tarjeta.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
        JDialog dialog = optionPane.createDialog("Error de Validación");
        dialog.setVisible(true);
        return false;
    }

    // Validar la fecha de expiración (debe ser una fecha futura válida)
    int mesExpiracion = Integer.parseInt((String) view.jMes.getSelectedItem());
    int añoExpiracion = Integer.parseInt((String) view.jAnio.getSelectedItem());
    int añoActual = java.time.LocalDate.now().getYear();
    int mesActual = java.time.LocalDate.now().getMonthValue();
    
    
    if (añoExpiracion < añoActual || (añoExpiracion == añoActual && mesExpiracion < mesActual)) {
        Object[] options = {"Aceptar"};
        JOptionPane optionPane = new JOptionPane("La fecha de expiración de la tarjeta no es válida.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
        JDialog dialog = optionPane.createDialog("Error de Validación");
        dialog.setVisible(true);
        return false;
    }

    // Validar el CVV (debe tener exactamente 3 dígitos)
    String cvv = new String(view.jCVV.getPassword());
    if (cvv.length() != 3) {
         Object[] options = {"Aceptar"};
        JOptionPane optionPane = new JOptionPane("El CVV debe tener exactamente 3 dígitos.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
        JDialog dialog = optionPane.createDialog("Error de Validación");
        dialog.setVisible(true);
        return false;
    }

    // Si todas las validaciones pasan, se considera que los campos son válidos
    return true;
}

}