/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author Javs
 */

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import view.PagoTarjeta;

public class ControlPagoTarjeta implements ActionListener {
    public PagoTarjeta view;
    public float totalVenta;
    public int idVenta;  
    public LocalDate fechaVenta; // Cambiar el tipo de dato de Date a LocalDate
    public LocalTime horaVenta;
    public float cantidadPagada;
    public String ultimosCuatroDigitosTarjeta;
    public ControlRealizarVenta controlRealizarVenta;

    public ControlPagoTarjeta(float totalVenta, ControlRealizarVenta controlRealizarVenta) { // Modificamos el constructor para aceptar la referencia al controlador de Realizar Venta
        this.totalVenta = totalVenta;
        this.controlRealizarVenta = controlRealizarVenta; // Almacenamos la referencia al controlador de Realizar Venta
        this.view = new PagoTarjeta();
        this.view.jPago.addActionListener(this);
        this.view.jCancelar.addActionListener(this);
        configurarValidaciones();
        this.view.jTotal.setText(String.valueOf(totalVenta));
        this.view.jTotal.setEditable(false);
        this.cantidadPagada = totalVenta; // Suponiendo que al inicio la cantidad pagada es igual al total de la venta
        this.ultimosCuatroDigitosTarjeta = "";
        
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.jPago) {
            // Realizar el pago solo si los campos son válidos
            if (validarCampos()) {
                // Mostrar la animación de carga
                mostrarAnimacionDeCarga();
                
                // Ocultar la animación de carga
                ocultarAnimacionDeCarga();
                
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

    // Validar el nombre de la tarjeta (solo letras y espacios)
    view.jNombre.addKeyListener(new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if (!Character.isLetter(c) && c != KeyEvent.VK_SPACE) {
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
    
    view.jTarjeta.addKeyListener(new KeyAdapter() {
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
            // Obtener el texto del portapapeles
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable contents = clipboard.getContents(null);
            if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    String textoPegado = (String) contents.getTransferData(DataFlavor.stringFlavor);
                    // Verificar que el texto pegado tenga exactamente 16 caracteres y sean números
                    if (textoPegado.length() == 16 && textoPegado.matches("\\d+")) {
                        // Permitir la acción de pegado
                    } else {
                        // Denegar la acción de pegado
                        e.consume();
                    }
                } catch (UnsupportedFlavorException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
});
    
    // Validar el CVV (debe tener exactamente 3 dígitos y solo números)
    view.jCVV.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
                // Obtener el texto del portapapeles
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                Transferable contents = clipboard.getContents(null);
                if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    try {
                        String textoPegado = (String) contents.getTransferData(DataFlavor.stringFlavor);
                        // Verificar que el texto pegado tenga exactamente 3 caracteres y sean números
                        if (textoPegado.length() == 3 && textoPegado.matches("\\d+")) {
                            // Permitir la acción de pegado
                        } else {
                            // Denegar la acción de pegado
                            e.consume();
                        }
                    } catch (UnsupportedFlavorException | IOException ex) {
                        ex.printStackTrace();
                    }
                }
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
        } else {
        // Asignar los últimos cuatro dígitos de la tarjeta
        this.ultimosCuatroDigitosTarjeta = numeroTarjeta.substring(numeroTarjeta.length() - 4);
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
    
    
   private void mostrarAnimacionDeCarga() {
    // Cargar la imagen de la animación
    ImageIcon iconoAnimado = new ImageIcon(getClass().getResource("/imagenes/cargandoPago.gif"));

    // Crear un JLabel para mostrar la animación
    JLabel etiquetaAnimacion = new JLabel(iconoAnimado);

    // Crear un JFrame para mostrar la animación de carga
    JFrame frame = new JFrame("Procesando Pago");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cerrar el frame al salir
    frame.getContentPane().add(etiquetaAnimacion); // Agregar el JLabel al contenido del frame
    frame.pack(); // Ajustar el tamaño del frame automáticamente
    frame.setLocationRelativeTo(null); // Centrar el frame en la pantalla
    frame.setVisible(true); // Mostrar el frame

    // Configurar un temporizador para cerrar el frame después de 3 segundos
    Timer timer = new Timer(3000, e -> {
        frame.dispose(); // Cerrar el frame de la animación
        // Simular el procesamiento del pago (reemplaza con tu lógica real)
        boolean pagoExitoso = simularProcesamientoDePago();
        // Mostrar mensaje de éxito o error
        if (pagoExitoso) {
            fechaVenta = LocalDate.now();
            horaVenta = LocalTime.now();
            mostrarMensaje("Pago realizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE); 
            view.jNombre.setEnabled(false);
            view.jTarjeta.setEnabled(false);
            view.jCVV.setEnabled(false);
            view.jMes.setEnabled(false);
            view.jAnio.setEnabled(false);
            // Deshabilitar el botón de cancelar
            ((JButton) view.jCancelar).setEnabled(false);
            ((JButton) view.jPago).setEnabled(false);
            
            controlRealizarVenta.agregarContenidoTarjetaTicket(controlRealizarVenta.getProductosVendidos(), controlRealizarVenta.view.jTicket, cantidadPagada, ultimosCuatroDigitosTarjeta);
                    
            ControlTicket controlTicket = new ControlTicket(controlRealizarVenta);
            controlTicket.mostrarTicketTarjeta(cantidadPagada, ultimosCuatroDigitosTarjeta, controlRealizarVenta.getProductosVendidos());

            ControlRegistrarVenta controlRegistrarVenta = new ControlRegistrarVenta(controlRealizarVenta);
            idVenta = controlRegistrarVenta.insertarVenta(fechaVenta, horaVenta, totalVenta);
                    
            controlTicket.insertarTicketBD(idVenta);
            
            ControlDetalleVenta controlDetalleVenta = new ControlDetalleVenta(controlRealizarVenta);
            controlDetalleVenta.insertarDetalleVentaBD(idVenta, controlRealizarVenta.getProductosVendidos());
            
            controlRealizarVenta.reiniciarControlador();
            view.dispose();
        } else {
            mostrarMensaje("El pago no se pudo procesar. Por favor, intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    });
    timer.setRepeats(false); // Establecer para que el temporizador solo se ejecute una vez
    timer.start();
}
    
   
    private void ocultarAnimacionDeCarga() {
        // Oculta la animación de carga
        JOptionPane.getRootFrame().dispose();
    }
    
    private boolean simularProcesamientoDePago() {
        // Simula el procesamiento del pago
        
        return Math.random() < 0.9; // Simula un 80% de probabilidad de éxito
    }
    
    private void mostrarMensaje(String mensaje, String titulo, int tipoMensaje) {
    JOptionPane optionPane = new JOptionPane(mensaje, tipoMensaje, JOptionPane.DEFAULT_OPTION, null, new Object[]{"Aceptar"});
    JDialog dialog = optionPane.createDialog(titulo);
    dialog.setVisible(true);
}

}