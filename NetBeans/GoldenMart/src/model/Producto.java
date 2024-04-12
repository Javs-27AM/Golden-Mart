/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
/*
 *
 * @author Javs
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class Producto {
    private Connection con;
    Conexion conexion = new Conexion();
    
    private String nombre;
    private float precio;
    private String contenidoNeto;
    private String categoria;
    private String descripcion;
    private String marca;
    private int cantidadDisponible;
    private String imagen;
    

    public Producto(String nombre, String marca, String contenidoNeto, String categoria, float precio, String imagen, int cantidadDisponible, String descripcion) {
        this.nombre = nombre;
        this.marca = marca;
        this.contenidoNeto = contenidoNeto;
        this.categoria = categoria;
        this.precio = precio;
        this.imagen = imagen;
        this.cantidadDisponible = cantidadDisponible;
        this.descripcion = descripcion;
    }
    public Producto() {
    }

    // Getters y setters para los atributos de la clase Producto
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
     public String getContenidoNeto() {
        return contenidoNeto;
    }

    public void setContenidoNeto(String contenidoNeto) {
        this.contenidoNeto = contenidoNeto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        if (precio >= 0) {
            this.precio = precio;
        } else {
            JOptionPane.showMessageDialog(null, "El precio debe ser mayor o igual a cero.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        if (cantidadDisponible >= 0) {
            this.cantidadDisponible = cantidadDisponible;
        } else {
            JOptionPane.showMessageDialog(null, "La cantidad disponible debe ser mayor o igual a cero.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    // Método para insertar un nuevo producto en la base de datos
    public void insertarProducto() {
    if (nombre.isEmpty() || marca.isEmpty() || imagen.isEmpty() || descripcion.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Error: Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
       String sql = "INSERT INTO producto (Nombre, Marca, ContenidoNeto, Categoria, Precio, Imagen, Cantidad_Disponible, Descripcion, Eliminado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 0)";
 
    try (Connection con = conexion.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        pstmt.setString(1, nombre);
        pstmt.setString(2, marca);
        pstmt.setString(3, contenidoNeto);
        pstmt.setString(4, categoria);
        pstmt.setFloat(5, precio);
        pstmt.setString(6, imagen);
        pstmt.setInt(7, cantidadDisponible);
        pstmt.setString(8, descripcion);
        pstmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Producto insertado correctamente en la base de datos.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta SQL para insertar un nuevo producto.", "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
}
}

