package model;

import static org.junit.Assert.*;
import org.junit.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 *
 * @author Javs
 */
public class ProductoTest {
    
    public ProductoTest() {
    }

    /**
     * Test of getNombre method, of class Producto.
     */
    @Test
    public void testGetNombre() {
        System.out.println("getNombre");
        Producto instance = new Producto("Leche", "La Lechera", "1 litro", "Lácteos", 1.5f, "imagen.png", 10, "Leche entera");
        String expResult = "Leche";
        String result = instance.getNombre();
        assertEquals(expResult, result);
    }

    /**
     * Test of setNombre method, of class Producto.
     */
    @Test
    public void testSetNombre() {
        System.out.println("setNombre");
        String nombre = "Cereal";
        Producto instance = new Producto();
        instance.setNombre(nombre);
        assertEquals(nombre, instance.getNombre());
    }

    /**
     * Test of getPrecio method, of class Producto.
     */
    @Test
    public void testGetPrecio() {
        System.out.println("getPrecio");
        Producto instance = new Producto("Cereal", "Kellogg's", "500g", "Cereales", 3.0f, "imagen.png", 20, "Cereal de trigo");
        float expResult = 3.0f;
        float result = instance.getPrecio();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setPrecio method, of class Producto.
     */
    @Test
    public void testSetPrecio() {
        System.out.println("setPrecio");
        float precio = 4.0f;
        Producto instance = new Producto();
        instance.setPrecio(precio);
        assertEquals(precio, instance.getPrecio(), 0.0);
    }

    /**
     * Test of getCantidadDisponible method, of class Producto.
     */
    @Test
    public void testGetCantidadDisponible() {
        System.out.println("getCantidadDisponible");
        Producto instance = new Producto("Atún", "Isabel", "250g", "Conservas", 2.5f, "imagen.png", 15, "Atún en aceite");
        int expResult = 15;
        int result = instance.getCantidadDisponible();
        assertEquals(expResult, result);
    }

    /**
     * Test of setCantidadDisponible method, of class Producto.
     */
    @Test
    public void testSetCantidadDisponible() {
        System.out.println("setCantidadDisponible");
        int cantidadDisponible = 25;
        Producto instance = new Producto();
        instance.setCantidadDisponible(cantidadDisponible);
        assertEquals(cantidadDisponible, instance.getCantidadDisponible());
    }
    
    @Test
    public void testInsertarProducto() {
    System.out.println("insertarProducto");
    
    // Crear una instancia de Producto con datos de prueba
    Producto producto = new Producto("Leche", "Lechera", "1 litro", "Lácteos", 2.5f, "imagen.png", 20, "Leche entera");
    
    // Ejecutar el método insertarProducto
    try {
        producto.insertarProducto();
    } catch (Exception e) {
        fail("Se lanzó una excepción: " + e.getMessage());
    }
    
    // Si llegamos aquí sin lanzar una excepción, la prueba pasa
    assertTrue(true);
    }
    
}
