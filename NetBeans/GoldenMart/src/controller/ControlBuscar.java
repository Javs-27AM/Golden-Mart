package controller;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Producto;
import view.GestionarInventario;

public class ControlBuscar implements ActionListener {
    private GestionarInventario view;
    private Producto producto;
    private DefaultTableModel model;

    // Constructor que recibe la vista y el modelo de la tabla
    public ControlBuscar(GestionarInventario view, DefaultTableModel model) {
        this.view = view;
        this.model = model;
        this.producto = new Producto(); // Instancia del modelo Producto
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.jBusqueda || e.getSource() == view.jBuscar) {
            // Obtener el texto ingresado en el campo de búsqueda
            String textoBusqueda = view.jBusqueda.getText();
            
            // Realizar la búsqueda del producto en la base de datos
            List<Producto> productosEncontrados = producto.buscarProducto(textoBusqueda); // Implementa este método en la clase Producto

            if (!productosEncontrados.isEmpty()) {
                // Mostrar los productos encontrados en la tabla
                cargarProductos(productosEncontrados);
            } else {
                // Limpiar la tabla si no se encuentra ningún producto
                model.setRowCount(0);
                JOptionPane.showMessageDialog(null, "No se encontraron productos con el nombre especificado.", "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // Método para cargar los productos encontrados en la tabla
    private void cargarProductos(List<Producto> productos) {
        model.setRowCount(0); // Limpiar la tabla antes de agregar nuevos datos

        for (Producto producto : productos) {
            JButton botonModificar = new JButton("Modificar");
            JButton botonEliminar = new JButton("Eliminar");

            String rutaImagen = producto.getImagen();
            ImageIcon imageIcon = createImageIcon(rutaImagen);
            JLabel imagenLabel = new JLabel(imageIcon);
            imagenLabel.setPreferredSize(new Dimension(IMAGEN_COLUMN_WIDTH, IMAGEN_COLUMN_HEIGHT));
            Object[] row = {
                producto.getIdProducto(),
                producto.getNombre(),
                producto.getMarca(),
                producto.getContenidoNeto(),
                producto.getCategoria(),
                producto.getPrecio(),
                producto.getCantidadDisponible(),
                imagenLabel,
                botonModificar,
                botonEliminar
            };
            model.addRow(row);
        }
    }

    // Método para crear un ImageIcon a partir de una ruta de imagen
    protected ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            Image image = icon.getImage().getScaledInstance(IMAGEN_COLUMN_WIDTH, IMAGEN_COLUMN_HEIGHT, Image.SCALE_SMOOTH);
            return new ImageIcon(image);
        } else {
            System.err.println("No se pudo encontrar el archivo de imagen: " + path);
            return null;
        }
    }

    // Constantes para el ancho y alto de la columna de imagen
    private static final int IMAGEN_COLUMN_WIDTH = 125;
    private static final int IMAGEN_COLUMN_HEIGHT = 125;
}
