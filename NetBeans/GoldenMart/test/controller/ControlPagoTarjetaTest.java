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
import org.junit.Test;
import org.junit.Before;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import view.PagoTarjeta;

public class ControlPagoTarjetaTest {
    private ControlPagoTarjeta controlPagoTarjeta;
    public PagoTarjeta pagoTarjeta;

    @Before
    public void setUp() {
        // Inicializar el controlador y la vista
        float totalVenta = 100.0f; // Supongamos que el total de la venta es $100
        controlPagoTarjeta = new ControlPagoTarjeta(totalVenta);
        pagoTarjeta = controlPagoTarjeta.view;
    }

    @Test
    public void testPagoCorrecto() {
        // Establecer datos válidos en los campos
        pagoTarjeta.jTarjeta.setText("1234567890123456");
        pagoTarjeta.jNombre.setText("Juan Perez");
        pagoTarjeta.jMes.setSelectedItem("12");
        pagoTarjeta.jAnio.setSelectedItem("2025");
        pagoTarjeta.jCVV.setText("123");

        // Simular clic en el botón de pago
        JButton pagoButton = pagoTarjeta.jPago;
        ActionEvent actionEvent = new ActionEvent(pagoButton, ActionEvent.ACTION_PERFORMED, "");
        controlPagoTarjeta.actionPerformed(actionEvent);

    }

    @Test
    public void testDatosInvalidos() {
        // Establecer datos inválidos en los campos
        pagoTarjeta.jTarjeta.setText("1234567890"); // Número de tarjeta inválido
        pagoTarjeta.jNombre.setText(""); // Nombre de tarjeta vacío
        pagoTarjeta.jMes.setSelectedItem("01"); // Fecha de expiración pasada
        pagoTarjeta.jAnio.setSelectedItem("2021"); // Fecha de expiración pasada
        pagoTarjeta.jCVV.setText("12"); // CVV con menos de 3 dígitos

        // Simular clic en el botón de pago
        JButton pagoButton = pagoTarjeta.jPago;
        ActionEvent actionEvent = new ActionEvent(pagoButton, ActionEvent.ACTION_PERFORMED, "");
        controlPagoTarjeta.actionPerformed(actionEvent);

    }

    // Función para verificar si se muestra un diálogo con el mensaje especificado
    
}

