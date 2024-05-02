package controller;

/*
 *
 * @author Javs
 */

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import model.Producto;
import model.Venta;
import view.RealizarVenta;

public class ControlRealizarVenta implements ActionListener {

    public RealizarVenta view;
    public Producto productoModel;
    public Venta ventaModel;
    private float totalVenta; 
    private DefaultTableModel model;
    private final int IMAGEN_COLUMN_WIDTH = 125;
    private final int IMAGEN_COLUMN_HEIGHT = 125;
    private List<Producto> productosVendidos = new ArrayList<>();

    public ControlRealizarVenta() {
        this.view = new RealizarVenta();
        this.view.jPago.addActionListener(this);
        this.view.jBuscar.addActionListener(this);
        this.view.jRegresar.addActionListener(this);
        this.view.jCancelar.addActionListener(this);
        
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
                 ControlAgregarVenta controlAgregarVenta = new ControlAgregarVenta(idProducto, view.jTicket, productosVendidos);
                 agregarContenido(productosVendidos, view.jTicket);
            } else if (fila >= 0 && columna == 8) { // Columna de Eliminar
                int idProducto = (int) view.jProducto.getValueAt(fila, 0); // Suponiendo que el ID del producto está en la primera columna
                ControlEliminarVenta controlEliminarVenta = new ControlEliminarVenta(idProducto, productosVendidos);
                eliminarContenido(productosVendidos, view.jTicket);
                
            }
        }
    });
        
        this.view.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.view.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            if (getTotalVenta() > 0) {
                int opcion = JOptionPane.showOptionDialog(null, "¿Desea cancelar la venta actual?", "Cancelar Venta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Sí", "No"}, "No");
                if (opcion == JOptionPane.YES_OPTION) {
                    ControlCancelarVenta controlCancelarVenta = new ControlCancelarVenta(productosVendidos);
                    eliminarContenido(productosVendidos, view.jTicket);
                    totalVenta = 0;
                    ControlMenu controlMenu = new ControlMenu();
                    controlMenu.view.setVisible(true); 
                    view.dispose();
                }
            } else {
                ControlMenu controlMenu = new ControlMenu();
                controlMenu.view.setVisible(true); 
                view.dispose();
            }
        }
    });

        this.productoModel = new Producto();
        this.ventaModel = new Venta();
        cargarProductos();
    }

    public void iniciar() {
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.jPago) {
            if (getTotalVenta() == 0) {
            // Mostrar mensaje de error
            JOptionPane optionPane = new JOptionPane("No hay productos en el carrito.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{ "Aceptar" });
            optionPane.createDialog(null, "Error").setVisible(true);
            } else {
            ControlMenuPago controlMenuPago = new ControlMenuPago(getTotalVenta(),this);
            controlMenuPago.view.setVisible(true);
        }
        } else if (e.getSource() == view.jBuscar) {
            String textoBusqueda = view.jBusqueda.getText();
            ControlBuscarVenta controlBuscarVenta = new ControlBuscarVenta(view, productoModel);         
            controlBuscarVenta.cargarProductos(view.jProducto, textoBusqueda);
        } else if (e.getSource() == view.jRegresar) {
            if (getTotalVenta() > 0) {
                int opcion = JOptionPane.showOptionDialog(null, "¿Desea cancelar la venta actual?", "Cancelar Venta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Sí", "No"}, "No");
                if (opcion == JOptionPane.YES_OPTION) {
                    ControlCancelarVenta controlCancelarVenta = new ControlCancelarVenta(productosVendidos);
                    eliminarContenido(productosVendidos, view.jTicket);
                    totalVenta = 0;
                    ControlMenu controlMenu = new ControlMenu();
                    controlMenu.view.setVisible(true); 
                    this.view.dispose();
                }
        } else {
                ControlMenu controlMenu = new ControlMenu();
                controlMenu.view.setVisible(true); 
                this.view.dispose();
                }
            }

        else if (e.getSource() == view.jCancelar) {
              ControlCancelarVenta controlCancelarVenta = new ControlCancelarVenta(productosVendidos);
              eliminarContenido(productosVendidos, view.jTicket);
              totalVenta = 0;
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
   
    
    public void agregarContenido(List<Producto> productosVendidos, JTextArea jTicket) {
        // Crear un panel para agregar elementos de forma más flexible
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        
         panelContenido.removeAll();
        // Agregar el logo de la empresa
        ImageIcon logo = new ImageIcon(getClass().getResource("/imagenes/logoticket.png"));
        JLabel logoLabel = new JLabel(logo);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContenido.add(logoLabel);

        // Agregar la fecha actual
        JLabel fechaLabel = new JLabel("Fecha: " + LocalDate.now());
        fechaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContenido.add(fechaLabel);

        // Agregar la hora actual
        LocalTime horaActual = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String horaFormateada = horaActual.format(formatter);
        JLabel horaLabel = new JLabel("Hora: " + horaFormateada);
        horaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContenido.add(horaLabel);

        // Agregar los productos vendidos
        JLabel productosLabel = new JLabel("Productos:");
        productosLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContenido.add(productosLabel);

        for (Producto producto : productosVendidos) {
            JLabel productoLabel = new JLabel(producto.getNombre() + " - Precio: $" + producto.getPrecio());
            productoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelContenido.add(productoLabel);
        }

        // Calcular el total
         totalVenta  = 0;
        for (Producto producto : productosVendidos) {
            totalVenta  += producto.getPrecio();
        }

        // Agregar el total
        JLabel totalLabel = new JLabel("Total: $" + totalVenta );
        totalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContenido.add(totalLabel);

        // Limpiar el JTextArea y agregar el contenido del panel
        jTicket.removeAll();
        jTicket.setLayout(new BorderLayout());
        jTicket.add(panelContenido, BorderLayout.CENTER);
        jTicket.revalidate();
        jTicket.repaint();
    }
    
    
    public void eliminarContenido(List<Producto> productosVendidos, JTextArea jTicket) {
    // Crear un panel para agregar elementos de forma más flexible
    JPanel panelContenido = new JPanel();
    panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
    
    panelContenido.removeAll();
    // Agregar el logo de la empresa
    ImageIcon logo = new ImageIcon(getClass().getResource("/imagenes/logoticket.png"));
    JLabel logoLabel = new JLabel(logo);
    logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    panelContenido.add(logoLabel);

    // Agregar la fecha actual
    JLabel fechaLabel = new JLabel("Fecha: " + LocalDate.now());
    fechaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    panelContenido.add(fechaLabel);

    // Agregar la hora actual
    LocalTime horaActual = LocalTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    String horaFormateada = horaActual.format(formatter);
    JLabel horaLabel = new JLabel("Hora: " + horaFormateada);
    horaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    panelContenido.add(horaLabel);

    // Agregar los productos vendidos
    JLabel productosLabel = new JLabel("Productos:");
    productosLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    panelContenido.add(productosLabel);

    for (Producto producto : productosVendidos) {
        JLabel productoLabel = new JLabel(producto.getNombre() + " - Precio: $" + producto.getPrecio());
        productoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContenido.add(productoLabel);
    }

    // Calcular el total
    float total = 0;
    for (Producto producto : productosVendidos) {
        total += producto.getPrecio();
    }

    // Agregar el total
    JLabel totalLabel = new JLabel("Total: $" + total);
    totalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    panelContenido.add(totalLabel);

    // Limpiar el JTextArea y agregar el contenido del panel
    jTicket.removeAll();
    jTicket.setLayout(new BorderLayout());
    jTicket.add(panelContenido, BorderLayout.CENTER);
    jTicket.revalidate();
    jTicket.repaint();
}

    
    public float getTotalVenta() {
        return totalVenta;
    }
    
    
    public List<Producto> getProductosVendidos() {
        return productosVendidos;
    }
    
    
    public void reiniciarControlador() {
        productosVendidos.clear(); // Limpiar la lista de productos vendidos
        totalVenta = 0; // Reiniciar el total de la venta
        cargarProductos(); // Recargar los productos en la vista
        view.jTicket.removeAll(); // Limpiar el área de ticket
        view.jTicket.revalidate();
        view.jTicket.repaint();
    }
}