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
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextArea;
import model.Producto;

public class ControlRealizarVentaTest {

    private List<Producto> productosVendidos;
    private JTextArea textArea;

    @Before
    public void setUp() {
        productosVendidos = new ArrayList<>();
        textArea = new JTextArea();
    }

    @Test
    public void testAgregarProducto() {
        // Crear instancia del controlador
        ControlAgregarVenta controlAgregarVenta = new ControlAgregarVenta(1, textArea, productosVendidos);
        
        // Verificar que el producto se agregó a la lista de productos vendidos
        assertEquals(1, productosVendidos.size());
    }

    @Test
    public void testEliminarProducto() {
        // Caso 1: Agregar un producto a la lista de productos vendidos y eliminarlo
        //Producto producto1 = new Producto(1); // Producto con ID 1
        //productosVendidos.add(producto1);

        // Crear instancia del controlador para agregar el producto
        ControlAgregarVenta controlAgregarVenta = new ControlAgregarVenta(1, textArea, productosVendidos);
        
        // Verificar que el producto se agregó a la lista de productos vendidos
        assertEquals(1, productosVendidos.size());
        
        // Crear instancia del controlador para eliminar el producto agregado
        ControlEliminarVenta controlEliminarVenta = new ControlEliminarVenta(1, productosVendidos);
        
        // Verificar que el producto se eliminó de la lista de productos vendidos
        assertEquals(0, productosVendidos.size());

        // Caso 2: Intentar eliminar un producto que no existe en la lista
        ControlEliminarVenta controlEliminarVenta2 = new ControlEliminarVenta(2, productosVendidos);
        
        // Verificar que la lista no cambió porque no había un producto con ID 2
        assertEquals(0, productosVendidos.size());

        // Caso 3: Intentar eliminar un producto cuando la lista está vacía
        productosVendidos.clear(); // Limpiar la lista para simular una lista vacía
        
        // Crear instancia del controlador para eliminar un producto de una lista vacía
        ControlEliminarVenta controlEliminarVenta3 = new ControlEliminarVenta(1, productosVendidos);
        
        // Verificar que la lista sigue vacía
        assertEquals(0, productosVendidos.size());
    }
    @Test
    public void testCancelarVenta() {
        // Agregar tres productos a la lista de productos vendidos
        ControlAgregarVenta controlAgregarVenta1 = new ControlAgregarVenta(1, textArea, productosVendidos);
        ControlAgregarVenta controlAgregarVenta2 = new ControlAgregarVenta(2, textArea, productosVendidos);
        ControlAgregarVenta controlAgregarVenta3 = new ControlAgregarVenta(3, textArea, productosVendidos);// Cancelar la venta
        assertEquals(3, productosVendidos.size());
        ControlCancelarVenta controlCancelarVenta = new ControlCancelarVenta(productosVendidos);
        // Verificar que la lista de productos vendidos está vacía después de cancelar la venta
        assertEquals(0, productosVendidos.size());
    }
}

