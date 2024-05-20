package controller;
/**
 *
 * @author Javs
 */
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import model.Producto;

public class ControlAgregarVenta {
    public List<Producto> productosVendidos;
    public Producto producto;

    public ControlAgregarVenta(int idProductoAgregar, JTextArea textArea, List<Producto> productosVendidos) {
        this.productosVendidos = productosVendidos;
        this.producto = new Producto();

        try {
            Producto productoAAgregar = producto.obtenerProductoPorId(idProductoAgregar);
            if (productoAAgregar != null) {
                // Verificar si la cantidad disponible es mayor que cero
                if (productoAAgregar.getCantidadDisponible() > 0) {
                    int opcion = JOptionPane.showOptionDialog(null, "¿Desea agregar este producto al carrito?", "Agregar al carrito", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Sí", "No"}, "No");
                    if (opcion == JOptionPane.YES_OPTION) {
                        //productosVendidos.add(productoAAgregar);
                        producto.agregarVenta(idProductoAgregar, textArea, productosVendidos);
                         // Agregar el producto a la lista de productos vendidos

                        // Mostrar la lista completa de productos vendidos
                        //mostrarProductosVendidos();
                        Object[] options = {"Aceptar"};
                        JOptionPane optionPane = new JOptionPane("Producto agregado al carrito.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
                        JDialog dialog = optionPane.createDialog("Éxito");
                        dialog.setVisible(true);
                    } 
                } else {
                    Object[] options = {"Aceptar"};
                    JOptionPane optionPane = new JOptionPane("El producto seleccionado no está disponible en este momento.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
                    JDialog dialog = optionPane.createDialog("Error");
                    dialog.setVisible(true);
                }
            } else {
                    Object[] options = {"Aceptar"};
                    JOptionPane optionPane = new JOptionPane("No se encontró ningún producto con el ID especificado.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
                    JDialog dialog = optionPane.createDialog("Error");
                    dialog.setVisible(true);

            }
        } catch (Exception ex) {
            // Manejo de la excepción, muestra un mensaje de error
            JOptionPane optionPane = new JOptionPane("Error al agregar el producto al carrito: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            JDialog dialog = optionPane.createDialog("Error");
            dialog.setVisible(true);
            ex.printStackTrace();


        }
    }

    
}
