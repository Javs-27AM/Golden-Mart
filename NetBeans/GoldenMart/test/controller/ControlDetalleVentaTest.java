/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author Javs
 */
import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.JTextArea;
import model.DetalleVenta;
import model.Producto;

public class ControlDetalleVentaTest {

    private ControlDetalleVenta controlDetalleVenta;
    private List<Producto> productosVendidos;
    private JTextArea textArea;

    @Before
    public void setUp() {
        // Inicializar el controlador y la lista de productos vendidos
        controlDetalleVenta = new ControlDetalleVenta(null); // Puedes pasar null para controlRealizarVenta si no es necesario en las pruebas
        productosVendidos = new ArrayList<>();
        textArea = new JTextArea();
    }

   @Test
public void testInsertarVentaYCrearDetalleVenta() {
    // Simular una venta con una fecha, hora y total específicos
    LocalDate fechaVenta = LocalDate.now();
    LocalTime horaVenta = LocalTime.now();
    float total = 230.30f; // Total de la venta

    // Crear instancia del controlador para agregar productos
    ControlAgregarVenta controlAgregarVenta = new ControlAgregarVenta(1, textArea, productosVendidos);
    // Agregar algunos productos a la lista de productos vendidos
    // Por ejemplo, podríamos agregar dos productos con IDs 1 y 2
    productosVendidos.add(new Producto(1));
    productosVendidos.add(new Producto(2));

    // Insertar la venta en la base de datos y obtener el ID de la venta
    // Luego, crear instancia del controlador para registrar la venta
    ControlRegistrarVenta controlRegistrarVenta = new ControlRegistrarVenta(null); // Puedes pasar null para controlRealizarVenta si no es necesario en las pruebas
    int idVenta = controlRegistrarVenta.insertarVenta(fechaVenta, horaVenta, total);

    // Verificar que el ID de la venta devuelto sea válido
    assertTrue(idVenta > 0); // El ID de la venta debe ser un número positivo

    // Crear instancia del controlador para crear los detalles de la venta
    ControlDetalleVenta controlDetalleVenta = new ControlDetalleVenta(null); // Puedes pasar null para controlRealizarVenta si no es necesario en las pruebas
    // Llamar a la función que crea los detalles de la venta
    controlDetalleVenta.insertarDetalleVentaBD(idVenta, productosVendidos);

    // Verificar que los detalles de la venta se hayan creado correctamente
    // Por ejemplo, podrías verificar el número de detalles de venta en la base de datos
}




}

