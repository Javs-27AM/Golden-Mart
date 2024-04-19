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

public class DetalleVenta {
    private Connection con;
    Conexion conexion = new Conexion();
    private int idDetalleVenta;
    private int idVenta;
    private int idProducto;
    private int cantidad;
    private float precioUnitario;

    // Constructor vacío
    public DetalleVenta() {
    }

    // Constructor con todos los atributos
    public DetalleVenta(int idDetalleVenta, int idVenta, int idProducto, int cantidad, float precioUnitario) {
        this.idDetalleVenta = idDetalleVenta;
        this.idVenta = idVenta;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
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

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
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