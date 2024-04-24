package controller;

import java.util.List;
import javax.swing.JOptionPane;
import model.Producto;

public class ControlEliminarVenta {
    private List<Producto> productosVendidos;

    public ControlEliminarVenta(int idProductoEliminar, List<Producto> productosVendidos) {
        this.productosVendidos = productosVendidos;

        // Verificar si la lista de productos vendidos está vacía
        if (productosVendidos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos en el carrito.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Salir del constructor si no hay productos en el carrito
        }

        // Llamar a la función eliminarVenta
        eliminarVenta(idProductoEliminar);
    }

    // Función para eliminar el producto de la lista usando la función eliminarVenta de la clase Producto
    private void eliminarVenta(int idProductoEliminar) {
        // Crear una instancia de Producto para acceder a la función eliminarVenta
        Producto producto = new Producto();
        // Llamar a la función eliminarVenta
        producto.eliminarVenta(idProductoEliminar, productosVendidos);
    }
}
