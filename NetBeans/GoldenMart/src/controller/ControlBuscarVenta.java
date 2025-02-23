package controller;
/*
 *
 * @author Javs
 */

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
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import model.Producto;
import view.RealizarVenta;

public class ControlBuscarVenta {
    
    public RealizarVenta view;
    public Producto productoModel;
    private final int IMAGEN_COLUMN_WIDTH = 125;
    private final int IMAGEN_COLUMN_HEIGHT = 125;
    //public DefaultTableModel model;

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
    Object[][] data = new Object[productos.size()][9]; // Ajusta el tamaño según tus necesidades
    String[] columnNames = {"ID Producto", "Nombre", "Marca", "Contenido Neto", "Precio", "Cantidad Disponible", "Imagen", "Agregar", "Eliminar"};
    for (int i = 0; i < productos.size(); i++) {
        Producto producto = productos.get(i);
        JButton botonAgregar = new JButton("Agregar al carrito");
        JButton botonEliminar = new JButton("Eliminar");
        String rutaImagen = producto.getImagen();
        ImageIcon imageIcon = createImageIcon(rutaImagen);
        JLabel imagenLabel = new JLabel(imageIcon);
        imagenLabel.setPreferredSize(new Dimension(IMAGEN_COLUMN_WIDTH, IMAGEN_COLUMN_HEIGHT));
        data[i] = new Object[]{
            producto.getIdProducto(),
            producto.getNombre(),
            producto.getMarca(),
            producto.getContenidoNeto(),
            producto.getPrecio(),
            producto.getCantidadDisponible(),
            imagenLabel,
            botonAgregar,
            botonEliminar
        };
    }
    int[] nonEditableColumns = {0, 1, 2, 3, 4, 5}; // Columnas de 0 a 5
    NonEditableTableModel model = new NonEditableTableModel(data, columnNames, nonEditableColumns);
    view.jProducto.setModel(model);

    TableColumnModel columnModel = view.jProducto.getColumnModel();
            for (int column = 0; column < view.jProducto.getColumnCount(); column++) {
                if (column == 6) { // Columna de la imagen
                    columnModel.getColumn(column).setPreferredWidth(IMAGEN_COLUMN_WIDTH);
                    columnModel.getColumn(column).setMinWidth(IMAGEN_COLUMN_WIDTH);
                    columnModel.getColumn(column).setMaxWidth(IMAGEN_COLUMN_WIDTH);
                    columnModel.getColumn(column).setResizable(false);
                    view.jProducto.setRowHeight(IMAGEN_COLUMN_HEIGHT);
                } else {
                    int width = 15;
                    for (int row = 0; row < view.jProducto.getRowCount(); row++) {
                        TableCellRenderer renderer = view.jProducto.getCellRenderer(row, column);
                        Component comp = view.jProducto.prepareRenderer(renderer, row, column);
                        width = Math.max(comp.getPreferredSize().width + 1, width);
                    }
                    columnModel.getColumn(column).setPreferredWidth(width);
                }
            }
            view.jProducto.getColumnModel().getColumn(0).setMinWidth(0);
            view.jProducto.getColumnModel().getColumn(0).setMaxWidth(0);
            view.jProducto.getColumnModel().getColumn(0).setWidth(0);
            view.jProducto.getColumnModel().getColumn(7).setCellRenderer(new ComponentCellRenderer());
            view.jProducto.getColumnModel().getColumn(8).setCellRenderer(new ComponentCellRenderer());
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
