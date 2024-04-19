package controller;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import model.Producto;
import view.RealizarVenta;

public class ControlBuscarVenta {
    
    private RealizarVenta view;
    private Producto productoModel;
    private final int IMAGEN_COLUMN_WIDTH = 125;
    private final int IMAGEN_COLUMN_HEIGHT = 125;
    private DefaultTableModel model;

    public ControlBuscarVenta(RealizarVenta view, Producto productoModel) {
        this.view = view;
        this.productoModel = productoModel;
        this.view.jBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String textoBusqueda = view.jBusqueda.getText();
                //System.out.println("Texto de búsqueda: " + textoBusqueda);
                cargarProductos(view.jProducto, textoBusqueda);
            }
        });
        this.view.jBuscar.addActionListener(e -> buscarProducto());
    }

    private void buscarProducto() {
        String textoBusqueda = view.jBusqueda.getText();
        //System.out.println("Texto de búsqueda: " + textoBusqueda);
        cargarProductos(view.jProducto, textoBusqueda);
    }

    public void cargarProductos(JTable tabla, String textoBusqueda) {
            List<Producto> productos = (textoBusqueda.isEmpty()) ? productoModel.listaProductos() : productoModel.buscarProductos(textoBusqueda);
            model = new DefaultTableModel();
            model.addColumn("ID Producto"); // Se agrega el ID de Producto como columna oculta
            model.addColumn("Nombre");
            model.addColumn("Marca");
            model.addColumn("Contenido Neto");
            model.addColumn("Precio");
            model.addColumn("Cantidad Disponible");
            model.addColumn("Imagen");
            model.addColumn("Agregar"); // Se cambia "Modificar" por "Agregar"
            model.addColumn("Eliminar");

            for (Producto producto : productos) {
                JButton botonAgregar = new JButton("Agregar"); // Cambiado a botón "Agregar"
                JButton botonEliminar = new JButton("Eliminar");

                String rutaImagen = producto.getImagen();
                ImageIcon imageIcon = createImageIcon(rutaImagen);
                JLabel imagenLabel = new JLabel(imageIcon);
                imagenLabel.setPreferredSize(new Dimension(IMAGEN_COLUMN_WIDTH, IMAGEN_COLUMN_HEIGHT));
                Object[] row = {
                    producto.getIdProducto(), // Se agrega el ID de Producto como oculto
                    producto.getNombre(),
                    producto.getMarca(),
                    producto.getContenidoNeto(),
                    producto.getPrecio(),
                    producto.getCantidadDisponible(),
                    imagenLabel,
                    botonAgregar,
                    botonEliminar
                };
                model.addRow(row);
            }
            tabla.setModel(model);

            TableColumnModel columnModel = tabla.getColumnModel();
            for (int column = 0; column < tabla.getColumnCount(); column++) {
                if (column == 6) { // Columna de la imagen
                    columnModel.getColumn(column).setPreferredWidth(IMAGEN_COLUMN_WIDTH);
                    columnModel.getColumn(column).setMinWidth(IMAGEN_COLUMN_WIDTH);
                    columnModel.getColumn(column).setMaxWidth(IMAGEN_COLUMN_WIDTH);
                    columnModel.getColumn(column).setResizable(false);
                    tabla.setRowHeight(IMAGEN_COLUMN_HEIGHT);
                } else {
                    int width = 15;
                    for (int row = 0; row < tabla.getRowCount(); row++) {
                        TableCellRenderer renderer = tabla.getCellRenderer(row, column);
                        Component comp = tabla.prepareRenderer(renderer, row, column);
                        width = Math.max(comp.getPreferredSize().width + 1, width);
                    }
                    columnModel.getColumn(column).setPreferredWidth(width);
                }
            }
            tabla.getColumnModel().getColumn(0).setMinWidth(0);
            tabla.getColumnModel().getColumn(0).setMaxWidth(0);
            tabla.getColumnModel().getColumn(0).setWidth(0);
            tabla.getColumnModel().getColumn(7).setCellRenderer(new ComponentCellRenderer());
            tabla.getColumnModel().getColumn(8).setCellRenderer(new ComponentCellRenderer());
}


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
}
