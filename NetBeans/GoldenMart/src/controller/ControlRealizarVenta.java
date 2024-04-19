package controller;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import model.Producto;
import view.RealizarVenta;

public class ControlRealizarVenta implements ActionListener {

    public RealizarVenta view;
    public Producto productoModel;
    private DefaultTableModel model;
    private final int IMAGEN_COLUMN_WIDTH = 125;
    private final int IMAGEN_COLUMN_HEIGHT = 125;

    public ControlRealizarVenta() {
        this.view = new RealizarVenta();
        this.view.jPago.addActionListener(this);
        this.view.jBuscar.addActionListener(this);
        this.view.jRegresar.addActionListener(this);
        this.view.jCancelar.addActionListener(this);
        // Suponiendo que tienes una instancia de ControlBuscar llamada controlBuscar

        this.view.jBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String textoBusqueda = view.jBusqueda.getText();
                //System.out.println("Texto de búsqueda: " + textoBusqueda);
                ControlBuscarVenta controlBuscarVenta = new ControlBuscarVenta(view, productoModel);
                controlBuscarVenta.cargarProductos(view.jProducto, textoBusqueda);
            }
        });

        this.view.jProducto.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            int fila = view.jProducto.rowAtPoint(e.getPoint());
            int columna = view.jProducto.columnAtPoint(e.getPoint());
            if (fila >= 0 && columna == 7) { // Columna de Agregar
                int idProducto = (int) view.jProducto.getValueAt(fila, 0); // Suponiendo que el ID del producto está en la primera columna
               // ControlModificar controlModificar = new ControlModificar(idProducto);
                //controlModificar.view.setVisible(true);
                view.dispose();
            } else if (fila >= 0 && columna == 8) { // Columna de Eliminar
                int idProducto = (int) view.jProducto.getValueAt(fila, 0); // Suponiendo que el ID del producto está en la primera columna
                //ControlEliminar controlEliminar = new ControlEliminar(idProducto);
                view.dispose();
            }
        }
    });

        this.productoModel = new Producto();
        cargarProductos();
    }

    public void iniciar() {
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.jPago) {
            // Abre la ventana para registrar un nuevo producto
           // ControlRegistro controlRegistro = new ControlRegistro();
           // view.dispose();
        } else if (e.getSource() == view.jBuscar) {
            String textoBusqueda = view.jBusqueda.getText();
            //System.out.println("Texto de búsqueda: " + textoBusqueda);
            // Aquí instanciamos el controlador ControlBuscar
            ControlBuscarVenta controlBuscarVenta = new ControlBuscarVenta(view, productoModel);
            // Llamamos al método cargarProductos del controlador ControlBuscar
            controlBuscarVenta.cargarProductos(view.jProducto, textoBusqueda);
        } else if (e.getSource() == view.jRegresar) {
            ControlMenu controlMenu = new ControlMenu(); // Crear una instancia del controlador del menú
            controlMenu.view.setVisible(true); 
            this.view.dispose();
        }
        else if (e.getSource() == view.jCancelar) {
            cargarProductos();
        }
    }

    public void cargarProductos() {
            List<Producto> productos = productoModel.listaProductos();
            model = new DefaultTableModel();
            model.addColumn("ID Producto");
            model.addColumn("Nombre");
            model.addColumn("Marca");
            model.addColumn("Contenido Neto");
            model.addColumn("Precio");
            model.addColumn("Cantidad Disponible");
            model.addColumn("Imagen");
            model.addColumn("Agregar");
            model.addColumn("Eliminar");

            for (Producto producto : productos) {
                JButton botonAgregar = new JButton("Agregar al Carrito");
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
                    producto.getPrecio(),
                    producto.getCantidadDisponible(),
                    imagenLabel,
                    botonAgregar,
                    botonEliminar
                };
                model.addRow(row);
            }
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
    
    public void cargarProductos(JTable tabla, String textoBusqueda) {
    List<Producto> productos = (textoBusqueda.isEmpty()) ? productoModel.listaProductos() : productoModel.buscarProductos(textoBusqueda);
    model = new DefaultTableModel();
    model.addColumn("ID Producto");
    model.addColumn("Nombre");
    model.addColumn("Marca");
    model.addColumn("Contenido Neto");
    model.addColumn("Precio");
    model.addColumn("Cantidad Disponible");
    model.addColumn("Imagen");
    model.addColumn("Modificar");
    model.addColumn("Eliminar");

    for (Producto producto : productos) {
        JButton botonAgregar = new JButton("Agregar al carrito");
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
    
    public void agregarContenido(JTextArea jTicket, int idTicket) {
        // Creamos un StringBuilder para construir el contenido del JTextArea
        StringBuilder contenido = new StringBuilder();

        // Agregamos el logo de la empresa
        ImageIcon logo = new ImageIcon("/imagenes/logoticket.png"); // Reemplaza la ruta con la ubicación de tu archivo de imagen
        contenido.append(logo).append("\n\n");

        // Agregamos el ID del ticket
        contenido.append("Ticket ID: ").append(idTicket).append("\n\n");

        // Agregamos la fecha actual
        contenido.append("Fecha: [Fecha actual aquí]\n");

        // Agregamos la hora actual
        contenido.append("Hora: [Hora actual aquí]\n\n");

        // Agregamos los productos y el total debajo de estos elementos.
        contenido.append("Productos:\n");
        // Agrega aquí la lista de productos
        
        // Agregamos el total
        contenido.append("\nTotal: [Total aquí]\n");

        // Establecemos el contenido en el JTextArea
        jTicket.setText(contenido.toString());
    }
}