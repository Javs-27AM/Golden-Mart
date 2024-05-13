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
import model.DetalleVenta;
import model.Producto;
import model.Venta;
import view.ReporteVentas;

public class ControlBuscarReporteVentas {
    
    public ReporteVentas view;
    public Producto productoModel;
    public Venta ventaModel;
    public DefaultTableModel model;

    public ControlBuscarReporteVentas(ReporteVentas view, Venta ventaModel) {
        this.view = view;
        this.ventaModel = ventaModel;
        this.view.jBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String textoBusqueda = view.jBusqueda.getText();
                //System.out.println("Texto de búsqueda: " + textoBusqueda);
                cargarVentas(view.jVenta, textoBusqueda);
            }
        });
        this.view.jBuscar.addActionListener(e -> buscarVentas());
    }

    private void buscarVentas() {
        String textoBusqueda = view.jBusqueda.getText();
        //System.out.println("Texto de búsqueda: " + textoBusqueda);
        cargarVentas(view.jVenta, textoBusqueda);
    }

     public void cargarVentas(JTable tabla, String textoBusqueda) {
    List<Venta> ventas = ventaModel.listaVentas(); // Obtener todas las ventas
    if (!textoBusqueda.isEmpty()) {
        ventas = ventaModel.buscarVentas(textoBusqueda); // Filtrar ventas según el texto de búsqueda
    }
    Object[][] data = new Object[ventas.size()][5]; // Ajustar el tamaño según tus necesidades
    String[] columnNames = {"ID Venta", "Fecha", "Hora", "Productos", "Total"};

    for (int i = 0; i < ventas.size(); i++) {
        Venta venta = ventas.get(i);

        // Construir la cadena de productos vendidos
        StringBuilder productosStr = new StringBuilder();
        for (DetalleVenta detalle : venta.getDetallesVenta()) {
            productosStr.append(detalle.getNombreProducto()).append(", ");
        }
        if (productosStr.length() >= 2) {
        productosStr.delete(productosStr.length() - 2, productosStr.length() - 1);
        }

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
    tabla.setModel(model);

    // Ajustar la apariencia de las columnas para que se autoajusten al contenido
    tabla.getColumnModel().getColumn(0).setPreferredWidth(70); // ID Venta
    tabla.getColumnModel().getColumn(1).setPreferredWidth(100); // Fecha
    tabla.getColumnModel().getColumn(2).setPreferredWidth(70); // Hora
    tabla.getColumnModel().getColumn(4).setPreferredWidth(70); // Total
    tabla.getColumnModel().getColumn(3).setPreferredWidth(800); // Anchura fija para la columna de Productos
    tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
}

    
}
