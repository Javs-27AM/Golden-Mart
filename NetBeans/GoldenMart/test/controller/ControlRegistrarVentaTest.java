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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import model.Producto;

public class ControlRegistrarVentaTest {

    private ControlRegistrarVenta controlRegistrarVenta;
    private List<Producto> productosVendidos;

    @Before
    public void setUp() {
        // Inicializar el controlador y la lista de productos vendidos
        controlRegistrarVenta = new ControlRegistrarVenta(null); // Puedes pasar null para controlRealizarVenta si no es necesario en las pruebas
        productosVendidos = new ArrayList<>();
    }

    @Test
    public void testInsertarVenta() {
        // Simular una venta con una fecha, hora y total específicos
        LocalDate fechaVenta = LocalDate.now();
        LocalTime horaVenta = LocalTime.now();
        float total = 100.0f; // Total de la venta

        // Insertar la venta en la base de datos y obtener el ID de la venta
        int idVenta = controlRegistrarVenta.insertarVenta(fechaVenta, horaVenta, total);

        // Verificar que el ID de la venta devuelto sea válido
        assertTrue(idVenta > 0); // El ID de la venta debe ser un número positivo
    }

    
}
