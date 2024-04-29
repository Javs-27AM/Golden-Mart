package controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Producto;

public class ControlEliminarVenta {
    private List<Producto> productosVendidos;

    public ControlEliminarVenta(int idProductoEliminar, List<Producto> productosVendidos) {
    this.productosVendidos = productosVendidos;

    // Verificar si la lista de productos vendidos está vacía
        if (productosVendidos.isEmpty()) {
            JOptionPane optionPane = new JOptionPane("No hay productos en el carrito.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{ "Aceptar" });
            optionPane.createDialog(null, "Error").setVisible(true);
            return; // Salir del constructor si no hay productos en el carrito
        }

        try {
            // Iterar sobre la lista y buscar el producto con el ID especificado para eliminarlo
            for (Producto producto : productosVendidos) {
                if (producto.getIdProducto() == idProductoEliminar) {
                    eliminarVenta(producto);
                    productosVendidos.remove(producto); // Eliminar el producto de la lista original
                    break; // Salir del bucle una vez que se haya eliminado el producto
                }
            }
        } catch (Exception ex) {
            // Manejo de la excepción, muestra un mensaje de error
            JOptionPane.showMessageDialog(null, "Error al eliminar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // Imprimir la traza de la excepción para fines de depuración
        }
    }



// Función para llamar al método eliminarVenta de la clase Producto
private void eliminarVenta(Producto producto) {
        producto.eliminarVenta(producto.getIdProducto(), productosVendidos);
    }


}

    