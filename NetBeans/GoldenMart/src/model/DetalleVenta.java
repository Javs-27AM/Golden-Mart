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
import java.sql.SQLException;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class DetalleVenta {
    public Connection con;
    Conexion conexion = new Conexion();
    public int idDetalleVenta;
    public int idVenta;
    public int idProducto;
    public int cantidad;
    public String nombre;
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
    
    public String getNombreProducto() {
        return nombre;
    }

    public void setNombreProducto(String nombre) {
        this.nombre = nombre;
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
           /* Object[] options = {"Aceptar"};
            JOptionPane optionPane = new JOptionPane("Detalle de la venta registrada correctamente.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
            JDialog dialog = optionPane.createDialog("Éxito");
            dialog.setVisible(true);*/

        } catch (SQLException ex) {
            // Mostrar mensaje de error
            Object[] options = {"Aceptar"};
            JOptionPane optionPane = new JOptionPane("Error al registrar el detalle de la venta.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
            JDialog dialog = optionPane.createDialog("Error");
            dialog.setVisible(true);
            
            ex.printStackTrace();
        }
    }

}