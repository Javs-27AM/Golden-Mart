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
import model.Venta;
import model.DetalleVenta;
import view.ReporteVentas;

public class ControlGestionVentas implements ActionListener {

    public ReporteVentas view;
    public Producto productoModel;
    public Venta ventaModel;
    public DetalleVenta DetalleVentaModel;
    private DefaultTableModel model;
    private final int IMAGEN_COLUMN_WIDTH = 125;
    private final int IMAGEN_COLUMN_HEIGHT = 125;

    public ControlGestionVentas() {
        this.view = new ReporteVentas();
        this.view.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Simulamos el evento de clic en el botón "Regresar"
                actionPerformed(new ActionEvent(view.jRegresar, ActionEvent.ACTION_PERFORMED, "Regresar"));
            }
        });
        this.view.jBuscar.addActionListener(this);
        this.view.jRegresar.addActionListener(this);
        this.view.jActualizar.addActionListener(this);
        /*this.view.jBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String textoBusqueda = view.jBusqueda.getText();
                //System.out.println("Texto de búsqueda: " + textoBusqueda);
                //ControlBuscar controlBuscar = new ControlBuscar(view, productoModel);
                //controlBuscar.cargarProductos(view.jVenta, textoBusqueda);
            }
        });
        /*this.view.jVenta.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            int fila = view.jVenta.rowAtPoint(e.getPoint());
            int columna = view.jVenta.columnAtPoint(e.getPoint());
            if (fila >= 0 && columna == 7 || columna == 8) { // Columna de Modificar
                int idProducto = (int) view.jVenta.getValueAt(fila, 0); // Suponiendo que el ID del producto está en la primera columna
                ControlModificar controlModificar = new ControlModificar(idProducto);
                controlModificar.view.setVisible(true);
                view.dispose();
            } else if (fila >= 0 && columna == 9) { // Columna de Eliminar
                int idProducto = (int) view.jVenta.getValueAt(fila, 0); // Suponiendo que el ID del producto está en la primera columna
                ControlEliminar controlEliminar = new ControlEliminar(idProducto);
                view.dispose();
            }
        }
    });*/
        

        this.ventaModel = new Venta();
        cargarVentas();
    }

    public void iniciar() {
        view.setLocationRelativeTo(null);
        //view.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.jBuscar) {
            //String textoBusqueda = view.jBusqueda.getText();
            //System.out.println("Texto de búsqueda: " + textoBusqueda);
            // Aquí instanciamos el controlador ControlBuscar
            //ControlBuscar controlBuscar = new ControlBuscar(view, productoModel);
            // Llamamos al método cargarProductos del controlador ControlBuscar
            //controlBuscar.cargarProductos(view.jVenta, textoBusqueda);
        } else if (e.getSource() == view.jRegresar) {
            ControlSesionAdmin controlSesionAdmin = new ControlSesionAdmin();
            controlSesionAdmin.view.setVisible(true);
            view.dispose();
        }
        else if (e.getSource() == view.jActualizar) {
            cargarVentas();
        }
    }
    public void cargarVentas() {
    List<Venta> ventas = ventaModel.listaVentas(); // Obtener todas las ventas
    Object[][] data = new Object[ventas.size()][5]; // Ajustar el tamaño según tus necesidades
    String[] columnNames = {"ID Venta", "Fecha", "Hora", "Productos", "Total"};

    for (int i = 0; i < ventas.size(); i++) {
        Venta venta = ventas.get(i);

        // Construir la cadena de productos vendidos
        StringBuilder productosStr = new StringBuilder();
        for (DetalleVenta detalle : venta.getDetallesVenta()) {
            // Aquí podrías obtener los nombres de los productos o cualquier otro detalle que desees mostrar en la tabla
            productosStr.append(detalle.getNombreProducto()).append(", ");
        }
        productosStr.delete(productosStr.length() - 2, productosStr.length() - 1); // Eliminar la última coma y el espacio

        data[i] = new Object[]{
            venta.getIdVenta(),
            venta.getFechaVenta(),
            venta.getHoraVenta(),
            productosStr.toString(),
            venta.getTotal()
        };
    }

    // Crear el modelo de la tabla utilizando DefaultTableModel
    DefaultTableModel model = new DefaultTableModel(data, columnNames);
    view.jVenta.setModel(model);

    // Ajustar la apariencia de las columnas para que se autoajusten al contenido
    view.jVenta.getColumnModel().getColumn(0).setPreferredWidth(70); // ID Venta
    view.jVenta.getColumnModel().getColumn(1).setPreferredWidth(100); // Fecha
    view.jVenta.getColumnModel().getColumn(2).setPreferredWidth(70); // Hora
    view.jVenta.getColumnModel().getColumn(4).setPreferredWidth(70); // Total
    view.jVenta.getColumnModel().getColumn(3).setPreferredWidth(800); // Anchura fija para la columna de Productos
   view.jVenta.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

}


   
}