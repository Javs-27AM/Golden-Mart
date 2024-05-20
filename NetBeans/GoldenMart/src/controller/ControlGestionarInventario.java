package controller;

/**
 *
 * @author Javs
 */
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
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import model.Producto;
import view.GestionarInventario;

public class ControlGestionarInventario implements ActionListener {

    public GestionarInventario view;
    public Producto productoModel;
    private DefaultTableModel model;
    private final int IMAGEN_COLUMN_WIDTH = 125;
    private final int IMAGEN_COLUMN_HEIGHT = 125;

    public ControlGestionarInventario() {
        this.view = new GestionarInventario();
        this.view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Simulamos el evento de clic en el botón "Regresar"
                actionPerformed(new ActionEvent(view.jRegresar, ActionEvent.ACTION_PERFORMED, "Regresar"));
            }
        });
        this.view.jAgregar.addActionListener(this);
        this.view.jBuscar.addActionListener(this);
        this.view.jRegresar.addActionListener(this);
        this.view.jRegiCate.addActionListener(this);
        this.view.jBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String textoBusqueda = view.jBusqueda.getText();
                //System.out.println("Texto de búsqueda: " + textoBusqueda);
                ControlBuscar controlBuscar = new ControlBuscar(view, productoModel);
                controlBuscar.cargarProductos(view.jProducto, textoBusqueda);
            }
        });
        this.view.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.view.jProducto.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            int fila = view.jProducto.rowAtPoint(e.getPoint());
            int columna = view.jProducto.columnAtPoint(e.getPoint());
            if (fila >= 0 && columna == 7 || columna == 8) { // Columna de Modificar
                int idProducto = (int) view.jProducto.getValueAt(fila, 0); // Suponiendo que el ID del producto está en la primera columna
                ControlModificar controlModificar = new ControlModificar(idProducto);
                controlModificar.view.setVisible(true);
                view.dispose();
            } else if (fila >= 0 && columna == 9) { // Columna de Eliminar
                int idProducto = (int) view.jProducto.getValueAt(fila, 0); // Suponiendo que el ID del producto está en la primera columna
                ControlEliminar controlEliminar = new ControlEliminar(idProducto);
                view.dispose();
            }
        }
    });
        

        this.productoModel = new Producto();
        cargarProductos();
    }

    public void iniciar() {
        view.setLocationRelativeTo(null);
        //view.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.jAgregar) {
            // Abre la ventana para registrar un nuevo producto
            ControlRegistro controlRegistro = new ControlRegistro();
            view.dispose();
        } else if (e.getSource() == view.jBuscar) {
            String textoBusqueda = view.jBusqueda.getText();
            //System.out.println("Texto de búsqueda: " + textoBusqueda);
            // Aquí instanciamos el controlador ControlBuscar
            ControlBuscar controlBuscar = new ControlBuscar(view, productoModel);
            // Llamamos al método cargarProductos del controlador ControlBuscar
            controlBuscar.cargarProductos(view.jProducto, textoBusqueda);
        } else if (e.getSource() == view.jRegresar) {
            ControlSesionAdmin controlSesionAdmin = new ControlSesionAdmin();
            controlSesionAdmin.view.setVisible(true);
            view.dispose();
        }
        else if (e.getSource() == view.jRegiCate) {
            ControlRegistroCategoria controlRegistroCategoria = new ControlRegistroCategoria();
            view.dispose();
        }
    }
    
    
    public void cargarProductos() {
    List<Producto> productos = productoModel.listaProductos();
    Object[][] data = new Object[productos.size()][9]; // Ajusta el tamaño según tus necesidades
    String[] columnNames = {"ID Producto", "Nombre", "Marca", "Contenido Neto","Categoria", "Precio", "Cantidad Disponible", "Imagen", "Modificar Producto", "Eliminar"};
    for (int i = 0; i < productos.size(); i++) {
        Producto producto = productos.get(i);
        JButton botonModificar= new JButton("Modificar Producto");
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
            producto.getCategoria(),
            producto.getPrecio(),
            producto.getCantidadDisponible(),
            imagenLabel,
            botonModificar,
            botonEliminar
        };
    }
    int[] nonEditableColumns = {0, 1, 2, 3, 4, 5, 6}; // Columnas de 0 a 5
    NonEditableTableModel model = new NonEditableTableModel(data, columnNames, nonEditableColumns);
    view.jProducto.setModel(model);

            TableColumnModel columnModel = view.jProducto.getColumnModel();
            for (int column = 0; column < view.jProducto.getColumnCount(); column++) {
                if (column == 7) { // Columna de la imagen
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
            //System.err.println("No se pudo encontrar el archivo de imagen: " + path);
            return null;
        }
    }
    
   public void cargarProductos(JTable tabla, String textoBusqueda) {
    List<Producto> productos = (textoBusqueda.isEmpty()) ? productoModel.listaProductos() : productoModel.buscarProductos(textoBusqueda);
    Object[][] data = new Object[productos.size()][9]; // Ajusta el tamaño según tus necesidades
    String[] columnNames = {"ID Producto", "Nombre", "Marca", "Contenido Neto","Categoria", "Precio", "Cantidad Disponible", "Imagen", "Modificar Producto", "Eliminar"};
    for (int i = 0; i < productos.size(); i++) {
        Producto producto = productos.get(i);
        JButton botonModificar= new JButton("Modificar Producto");
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
            producto.getCategoria(),
            producto.getPrecio(),
            producto.getCantidadDisponible(),
            imagenLabel,
            botonModificar,
            botonEliminar
        };
    }
    int[] nonEditableColumns = {0, 1, 2, 3, 4, 5, 6}; // Columnas de 0 a 5
    NonEditableTableModel model = new NonEditableTableModel(data, columnNames, nonEditableColumns);
    view.jProducto.setModel(model);

            TableColumnModel columnModel = view.jProducto.getColumnModel();
            for (int column = 0; column < view.jProducto.getColumnCount(); column++) {
                if (column == 7) { // Columna de la imagen
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
   
   
}