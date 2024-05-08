package controller;
/*
 *
 * @author Javs
 */
import javax.swing.JOptionPane;
import model.Producto;
import java.util.List;

public class ControlCancelarVenta {
    private List<Producto> productosVendidos;

    public ControlCancelarVenta(List<Producto> productosVendidos) {
        this.productosVendidos = productosVendidos;

        // Verificar si la lista de productos vendidos está vacía
        if (productosVendidos.isEmpty()) {
            JOptionPane optionPane = new JOptionPane("No hay productos en el carrito.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{ "Aceptar" });
            optionPane.createDialog(null, "Error").setVisible(true);
            return; // Salir del constructor si no hay productos en el carrito
        }

        // Llamar a la función eliminarVenta por cada producto en la lista hasta que esté vacía
        while (!productosVendidos.isEmpty()) {
            eliminarVenta(productosVendidos.get(0));
        }

        // Mostrar mensaje de éxito solo si se ha cancelado al menos un producto
        JOptionPane optionPane = new JOptionPane("Venta cancelada.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{ "Aceptar" });
        optionPane.createDialog(null, "Éxito").setVisible(true);
    }

    // Función para llamar al método eliminarVenta de la clase Producto y eliminar un producto de la lista
    private void eliminarVenta(Producto producto) {
        producto.eliminarVenta(producto.getIdProducto(), productosVendidos);
    }
}
