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
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class Venta {
    private Connection con;
    Conexion conexion = new Conexion();
    private int idVenta;
    private Date fechaVenta;
    private Time horaVenta;
    private float total;
    private int cantidadProductosVendidos;

    public Venta(int idVenta, Date fechaVenta, Time horaVenta, float total) {
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.horaVenta = horaVenta;
        this.total = total;
        this.cantidadProductosVendidos=0;
    }

    public Venta(Date fechaVenta, Time horaVenta, float total) {
        this.fechaVenta = fechaVenta;
        this.horaVenta = horaVenta;
        this.total = total;
    }
    
    public Venta(){

    }
    // Getters y setters para los atributos de la clase Venta

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Time getHoraVenta() {
        return horaVenta;
    }

    public void setHoraVenta(Time horaVenta) {
        this.horaVenta = horaVenta;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    // Métodos de la clase Venta

    public void insertarVentaEnBD(Venta venta) {
        String sql = "INSERT INTO Venta (FechaVenta, HoraVenta, Total) VALUES (?, ?, ?)";
        
        try (Connection con = conexion.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setDate(1, new java.sql.Date(venta.getFechaVenta().getTime()));
            pstmt.setTime(2, venta.getHoraVenta());
            pstmt.setFloat(3, venta.getTotal());
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Venta registrada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al registrar la venta.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public List<Venta> buscarVentas(String textoBusqueda) {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT * FROM venta WHERE (IdVenta LIKE ? OR FechaVenta LIKE ? OR HoraVenta LIKE ? OR Total LIKE ?)";

        try (Connection con = conexion.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, "%" + textoBusqueda + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Venta venta = new Venta();
                    venta.setIdVenta(rs.getInt("IdVenta"));
                    venta.setFechaVenta(rs.getDate("FechaVenta"));
                    venta.setHoraVenta(rs.getTime("HoraVenta"));
                    venta.setTotal(rs.getFloat("Total"));
                    ventas.add(venta);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar ventas.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        
        return ventas;
    }
    public void incrementarCantidadProductosVendidos(int cantidad) {
        this.cantidadProductosVendidos += cantidad;
    }
}
