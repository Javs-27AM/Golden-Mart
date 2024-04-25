package controller;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import model.Producto;

public class ControlModificarTest {

    private ControlModificar control;

    @Before
    public void setUp() {
        // Creamos una instancia del controlador para probarlo
        int idProducto = 10; // Supongamos que el ID del producto a modificar es 10
        control = new ControlModificar(idProducto);
    }

    

    

    @Test
    public void testModificarProducto() {
        Producto producto = new Producto();
        // Modificar los campos del formulario
        control.view.jNombre.setText("Leche Descremada");
        control.view.jMarca.setText("Nueva Lechera");
        control.view.jContenido.setText("500ml");
        control.view.jCategoria.setSelectedIndex(1); // Supongamos que la segunda categoría es "Bebidas"
        control.view.jPrecio.setText("1.5");
        control.view.jImagen.setText("nueva_imagen.png");
        control.view.jCantidad.setText("30");
        control.view.jDescripcion.setText("Leche descremada");
        // Simular clic en el botón "Modificar"
        control.view.jModificar.doClick();
        // Verificar que el producto se haya modificado correctamente en la base de datos
        Producto productoModificado = producto.obtenerProductoPorId(10); // Suponiendo que el ID del producto modificado sigue siendo 10
        assertNotNull(productoModificado);
        assertEquals("Leche Descremada", productoModificado.getNombre());
        assertEquals("Nueva Lechera", productoModificado.getMarca());
        assertEquals("500ml", productoModificado.getContenidoNeto());
        assertEquals("Perecedero", productoModificado.getCategoria());
        assertEquals(1.5f, productoModificado.getPrecio(), 0.01f);
        assertEquals("nueva_imagen.png", productoModificado.getImagen());
        assertEquals(30, productoModificado.getCantidadDisponible());
        assertEquals("Leche descremada", productoModificado.getDescripcion());
    }

    
}
