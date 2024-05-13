/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Javs
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class DetalleVenta {
    private Connection con;
    Conexion conexion = new Conexion();
    private int idDetalleVenta;
    private int idVenta;
    private int idProducto;
    private int cantidad;

    // Constructor vacío
    public DetalleVenta() {
    }

    // Constructor con todos los atributos
    public DetalleVenta(int idDetalleVenta, int idVenta, int idProducto, int cantidad) {
        this.idDetalleVenta = idDetalleVenta;
        this.idVenta = idVenta;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    // Getters y setters
    public int getIdDetalleVenta() {
        return idDetalleVenta;
    }

    public void setIdDetalleVenta(int idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    
public void crearDetalleVenta(int idVenta, List<Producto> productosVendidos) {
        String sql = "INSERT INTO DetalleVenta (IdVenta, IdProducto, Cantidad) VALUES (?, ?, 1)";

        try (Connection con = conexion.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            for (Producto producto : productosVendidos) {
                pstmt.setInt(1, idVenta);
                pstmt.setInt(2, producto.getIdProducto()); // Suponiendo que producto.getId() devuelve el ID del producto
               // pstmt.setInt(3, producto.getCantidadDisponible()); // Suponiendo que producto.getCantidad() devuelve la cantidad vendida
                pstmt.executeUpdate();
            }

            // Mostrar mensaje de éxito
            Object[] options = {"Aceptar"};
            JOptionPane optionPane = new JOptionPane("Detalle de la venta registrada correctamente.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
            JDialog dialog = optionPane.createDialog("Éxito");
            dialog.setVisible(true);

        } catch (SQLException ex) {
            // Mostrar mensaje de error
            Object[] options = {"Aceptar"};
            JOptionPane optionPane = new JOptionPane("Error al registrar el detalle de la venta.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
            JDialog dialog = optionPane.createDialog("Error");
            dialog.setVisible(true);
            
            ex.printStackTrace();
        }
    }

public List<Producto> obtenerProductosVenta(int idVenta) {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT p.* " +
                     "FROM detalleventa dv " +
                     "INNER JOIN producto p ON dv.IdProducto = p.IdProducto " +
                     "WHERE dv.IdVenta = ?";

        try (Connection con = conexion.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, idVenta);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Producto producto = new Producto();
                    producto.setNombre(rs.getString("Nombre"));
                    producto.setPrecio(rs.getFloat("Precio"));
                    productos.add(producto);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Manejar la excepción según tu necesidad
        }

        return productos;
    }
}