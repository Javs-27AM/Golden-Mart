/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
    * @author Javs
 */
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import view.PagoEfectivo;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;

public class ControlPagoEfectivoTest {

    private PagoEfectivo pagoEfectivo;
    private ControlPagoEfectivo controlPagoEfectivo;
    private float totalVenta;

    @Before
    public void setUp() {
        totalVenta = 100.0f; // Total de la venta ficticio para el test
        pagoEfectivo = new PagoEfectivo();
        controlPagoEfectivo = new ControlPagoEfectivo(totalVenta);
        controlPagoEfectivo.view = pagoEfectivo;
    }

    @Test
    public void testPagoCorrecto() {
        // Establecer la cantidad pagada en el campo de texto
        pagoEfectivo.jPagado.setText("150.0");

        // Simular el clic en el botón de pago
        controlPagoEfectivo.actionPerformed(new ActionEvent(pagoEfectivo.jPago, ActionEvent.ACTION_PERFORMED, ""));

        // Verificar que se muestre el cambio correctamente
        assertEquals("50.0", pagoEfectivo.jCambio.getText());
    }

    @Test
    public void testPagoSinCantidad() {
        // Dejar el campo de texto de pago vacío

        // Simular el clic en el botón de pago
        controlPagoEfectivo.actionPerformed(new ActionEvent(pagoEfectivo.jPago, ActionEvent.ACTION_PERFORMED, ""));

        // Verificar que se muestre un mensaje de advertencia
        assertTrue(dialogIsVisible("Por favor ingrese la cantidad pagada."));
    }

    @Test
    public void testCancelarPago() {
        // Simular el clic en el botón de cancelar
        controlPagoEfectivo.actionPerformed(new ActionEvent(pagoEfectivo.jCancelar, ActionEvent.ACTION_PERFORMED, ""));

        // Verificar que la ventana de pago en efectivo se cierre
        assertFalse(pagoEfectivo.isVisible());
    }

    // Función para verificar si se muestra un diálogo con el mensaje especificado
    private boolean dialogIsVisible(String message) {
        JOptionPane pane = (JOptionPane) pagoEfectivo.getComponent(0);
        String dialogMessage = (String) pane.getMessage();
        return dialogMessage.contains(message);
    }
}

