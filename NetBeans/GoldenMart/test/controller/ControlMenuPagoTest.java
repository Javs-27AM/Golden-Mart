/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextArea;
import model.Producto;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Javs
 */
public class ControlMenuPagoTest {
     private List<Producto> productosVendidos;
    private JTextArea textArea;

    @Before
    public void setUp() {
        productosVendidos = new ArrayList<>();
        textArea = new JTextArea();
    }
    
     @Test
     public void testMenuPago() {
       ControlAgregarVenta controlAgregarVenta = new ControlAgregarVenta(1, textArea, productosVendidos);
       ControlAgregarVenta controlAgregarVenta2 = new ControlAgregarVenta(2, textArea, productosVendidos);
       ControlAgregarVenta controlAgregarVenta3 = new ControlAgregarVenta(3, textArea, productosVendidos);
       assertEquals(1, productosVendidos.size());
     }
}
