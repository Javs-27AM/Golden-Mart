package controller;

import java.awt.event.*;
import javax.swing.JOptionPane;
import model.Producto;

public class ControlEliminar implements ActionListener {
    private Producto producto;
    public int idProductoEliminar;

    // Constructor que recibe el ID del producto a eliminar como argumento
    public ControlEliminar(int idProductoEliminar) {
        this.idProductoEliminar = idProductoEliminar;
        this.producto = new Producto();
        
       try {
            Producto productoAEliminar = producto.obtenerProductoPorId(idProductoEliminar);
            if (productoAEliminar != null) {
                int opcion = JOptionPane.showOptionDialog(null, "¿Estás seguro que deseas eliminar este producto?", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Sí", "No"}, "No");
                if (opcion == JOptionPane.YES_OPTION) {
                    producto.eliminarProducto(idProductoEliminar);
                    // Suponiendo que ControlGestionarInventario tiene un método cargarProductos
                    ControlGestionarInventario controlGestionarInventario = new ControlGestionarInventario();
                    controlGestionarInventario.view.setVisible(true);
                } else {
                    // Si el usuario elige "No" o cierra el diálogo, cargar el controlador del inventario
                    ControlGestionarInventario controlGestionarInventario = new ControlGestionarInventario();
                    controlGestionarInventario.iniciar();
                } 
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún producto con el ID especificado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            // Manejo de la excepción, muestra un mensaje de error
            JOptionPane.showMessageDialog(null, "Error al eliminar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
