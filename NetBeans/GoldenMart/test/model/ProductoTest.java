package model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextArea;
import static org.junit.Assert.*;
import org.junit.Test;
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
     @Test
    public void testModificarProducto() {
        System.out.println("modificarProducto");

        // Datos originales del producto a modificar
        int idProducto = 11;
        String nombreOriginal = "Leche";
        String marcaOriginal = "La Lechera";
        String contenidoNetoOriginal = "1 litro";
        String categoriaOriginal = "Lácteos";
        float precioOriginal = 1.5f;
        String imagenOriginal = "imagen.png";
        int cantidadDisponibleOriginal = 10;
        String descripcionOriginal = "Leche condensada";

        // Nuevos datos para modificar el producto
        String nombreNuevo = "Leche condensada";
        String marcaNuevo = "Nestlé";
        String contenidoNetoNuevo = "375 g.";
        String categoriaNuevo = "Lácteos";
        float precioNuevo = 1.5f;
        String imagenNuevo = "/imagenes/lechera.png";
        int cantidadDisponibleNuevo = 10;
        String descripcionNuevo = "Por más de 85 años la leche condensada La Lechera ha sido el complemento perfecto para dar sabor a tus postres, platillos y bebidas. -Elaborada con 100% leche de vaca ";

        // Crear una instancia de Producto con los datos originales
        Producto producto = new Producto();
        producto.setIdProducto(idProducto); // Establecer el ID del producto existente
        producto.setNombre(nombreOriginal);
        producto.setMarca(marcaOriginal);
        producto.setContenidoNeto(contenidoNetoOriginal);
        producto.setCategoria(categoriaOriginal);
        producto.setPrecio(precioOriginal);
        producto.setImagen(imagenOriginal);
        producto.setCantidadDisponible(cantidadDisponibleOriginal);
        producto.setDescripcion(descripcionOriginal);

        // Ejecutar el método modificarProducto con los nuevos datos
        try {
            producto.modificarProducto(idProducto, nombreNuevo, marcaNuevo, contenidoNetoNuevo, categoriaNuevo, precioNuevo, imagenNuevo, cantidadDisponibleNuevo, descripcionNuevo);
        } catch (Exception e) {
            fail("Se lanzó una excepción: " + e.getMessage());
        }

        // Obtener el producto modificado
        Producto productoModificado = producto.obtenerProductoPorId(idProducto);

        // Verificar que se haya obtenido el producto modificado
        assertNotNull(productoModificado);

        // Verificar que los valores del producto modificado sean los esperados
        assertEquals(nombreNuevo, productoModificado.getNombre());
        assertEquals(marcaNuevo, productoModificado.getMarca());
        assertEquals(contenidoNetoNuevo, productoModificado.getContenidoNeto());
        assertEquals(categoriaNuevo, productoModificado.getCategoria());
        assertEquals(precioNuevo, productoModificado.getPrecio(), 0.01);
        assertEquals(imagenNuevo, productoModificado.getImagen());
        assertEquals(cantidadDisponibleNuevo, productoModificado.getCantidadDisponible());
        assertEquals(descripcionNuevo, productoModificado.getDescripcion());
    }
    
   @Test
    public void testEliminarProducto() {
        System.out.println("testEliminarProducto");
        // Eliminamos el producto con ID 11
        try {
            Producto producto = new Producto();
            producto.eliminarProducto(11);
            // Verificamos que el producto se haya eliminado correctamente
            Producto productoEliminado = producto.obtenerProductoPorId(11);
            assertNull(productoEliminado);
        } catch (Exception e) {
            fail("Se lanzó una excepción: " + e.getMessage());
        }
    }

    @Test
    public void testBuscarProductos() {
        System.out.println("testBuscarProductos");
        // Realizamos una búsqueda de productos con texto "Leche condensada"
        try {
            Producto producto = new Producto();
            List<Producto> productosEncontrados = producto.buscarProductos("Leche condensada");

            // Imprimimos el tamaño de la lista de productos encontrados
            System.out.println("Número de productos encontrados: " + productosEncontrados.size());

            // Imprimimos los nombres de los productos encontrados
            for (Producto p : productosEncontrados) {
                System.out.println("Nombre del producto encontrado: " + p.getNombre());
            }

            // Verificamos que se haya encontrado al menos un producto
            assertFalse(productosEncontrados.isEmpty());
        } catch (Exception e) {
            fail("Se lanzó una excepción: " + e.getMessage());
        }
    }
    @Test
    public void testAgregarVenta_ProductoDisponible() {
        System.out.println("testAgregarVenta_ProductoDisponible");
        Producto producto = new Producto();

        // Verificamos la cantidad disponible inicial del producto
        int cantidadInicial = producto.obtenerCantidadDisponibleInicial(11);

        // Realizamos la venta del producto
        JTextArea jTicket = new JTextArea();
        List<Producto> productosVendidos = new ArrayList<>();
        producto.agregarVenta(11, jTicket, productosVendidos);

        // Verificamos que se haya reducido la cantidad disponible en la base de datos
        int cantidadFinal = producto.obtenerCantidadDisponibleInicial(11);
        assertEquals(cantidadInicial - 1, cantidadFinal);

        // Verificamos que el producto se haya agregado a la lista de productos vendidos
        assertEquals(1, productosVendidos.size());
    }

    @Test
    public void testAgregarVenta_ProductoAgotado() {
        System.out.println("testAgregarVenta_ProductoAgotado");
        Producto producto = new Producto();

        // Agotamos el producto
        producto.actualizarCantidadDisponibleEnBD(11, 0);

        // Intentamos agregar un producto agotado
        JTextArea jTicket = new JTextArea();
        List<Producto> productosVendidos = new ArrayList<>();
        producto.agregarVenta(11, jTicket, productosVendidos);

        // Verificamos que no se haya reducido la cantidad disponible en la base de datos
        int cantidadFinal = producto.obtenerCantidadDisponibleInicial(11);
        assertEquals(0, cantidadFinal);

        // Verificamos que la lista de productos vendidos esté vacía
        assertTrue(productosVendidos.isEmpty());
    }

    @Test
    public void testAgregarVenta_ProductoNoDisponible() {
        System.out.println("testAgregarVenta_ProductoNoDisponible");
        Producto producto = new Producto();

        // Eliminamos el producto
        producto.eliminarProducto(11);

        // Intentamos agregar un producto no disponible
        JTextArea jTicket = new JTextArea();
        List<Producto> productosVendidos = new ArrayList<>();
        producto.agregarVenta(11, jTicket, productosVendidos);

        // Verificamos que no se haya reducido la cantidad disponible en la base de datos
        int cantidadFinal = producto.obtenerCantidadDisponibleInicial(11);
        assertEquals(0, cantidadFinal);

        // Verificamos que la lista de productos vendidos esté vacía
        assertTrue(productosVendidos.isEmpty());
    }

    @Test
public void testEliminarVenta_ProductoEnLista() {
    System.out.println("testEliminarVenta_ProductoEnLista");

    // Creamos una instancia de Producto para simular la venta
    Producto producto = new Producto();
    producto.setIdProducto(11); // ID del producto a eliminar
    producto.setNombre("Leche condensada");
    producto.setPrecio(26);
    producto.setCantidadDisponible(40); // Cantidad inicial del producto
    
    // Creamos una lista de productos vendidos y agregamos el producto
    List<Producto> productosVendidos = new ArrayList<>();
    productosVendidos.add(producto);
    producto.agregarVenta(producto.getIdProducto(), null, productosVendidos);
    // Llamamos al método eliminarVenta
    producto.eliminarVenta(producto.getIdProducto(), productosVendidos);
     System.out.println("Eliminando venta del producto con ID " + producto.getIdProducto());


    // Verificamos que el producto ya no esté en la lista de productos vendidos
    assertFalse(productosVendidos.contains(producto));

    // Verificamos que la cantidad disponible del producto se haya incrementado nuevamente a 40 después de eliminar la venta
    Producto productoEliminado = producto.obtenerProductoPorId(producto.getIdProducto());
    assertEquals(40, productoEliminado.getCantidadDisponible());
}



}
