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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;


public class Producto {
    private Connection con;
    Conexion conexion = new Conexion();
    private int idProducto;
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
    public Producto(int idProducto, String nombre, String marca, String contenidoNeto, String categoria, float precio, String imagen, int cantidadDisponible, String descripcion) {
        this.idProducto = idProducto;
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
    
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
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

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
    public String getImagen() {
        return imagen;
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
    
    public void modificarProducto(int idProducto, String nombre, String marca, String contenidoNeto, String categoria, float precio, String imagen, int cantidadDisponible, String descripcion) {
    String sql = "UPDATE producto SET Nombre = ?, Marca = ?, ContenidoNeto = ?, Categoria = ?, Precio = ?, Imagen = ?, Cantidad_Disponible = ?, Descripcion = ? WHERE IdProducto = ?";
    
    try (Connection con = conexion.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setString(1, nombre);
        pstmt.setString(2, marca);
        pstmt.setString(3, contenidoNeto);
        pstmt.setString(4, categoria);
        pstmt.setFloat(5, precio);
        pstmt.setString(6, imagen);
        pstmt.setInt(7, cantidadDisponible);
        pstmt.setString(8, descripcion);
        pstmt.setInt(9, idProducto); // Establecer el ID como último parámetro
        int filasActualizadas = pstmt.executeUpdate();
        if (filasActualizadas > 0) {
            JOptionPane.showMessageDialog(null, "Producto modificado correctamente en la base de datos.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró ningún producto con el ID especificado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta SQL para modificar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
}



    public void eliminarProducto(int idProducto) {
    String sql = "UPDATE producto SET Eliminado = 1 WHERE IdProducto = ?";
    
    try (Connection con = conexion.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setInt(1, idProducto);
       int filasActualizadas = pstmt.executeUpdate();
       if (filasActualizadas > 0) {
            JOptionPane optionPane = new JOptionPane("Producto eliminado correctamente de la base de datos.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{"Aceptar"}, "Aceptar");
            JDialog dialog = optionPane.createDialog("Éxito");
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró ningún producto con el ID especificado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta SQL para eliminar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
}
    
    public void buscarProducto(String textoBusqueda) {
    String sql = "SELECT * FROM producto WHERE Nombre LIKE ? OR Marca LIKE ? OR Categoria LIKE ?";
    
    try (Connection con = conexion.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setString(1, "%" + textoBusqueda + "%");
        pstmt.setString(2, "%" + textoBusqueda + "%");
        pstmt.setString(3, "%" + textoBusqueda + "%");
        
        ResultSet rs = pstmt.executeQuery();
        // Aquí puedes manejar los resultados de la búsqueda, por ejemplo, mostrarlos en una tabla
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta SQL para buscar productos.", "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
}
    public Producto obtenerProductoPorId(int idProducto) {
    String sql = "SELECT * FROM producto WHERE IdProducto = ? AND Eliminado = 0"; // Solo productos no eliminados
    
    try (Connection con = conexion.getConnection();
         PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setInt(1, idProducto);
        
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(rs.getInt("IdProducto"));
                producto.setNombre(rs.getString("Nombre"));
                producto.setMarca(rs.getString("Marca"));
                producto.setContenidoNeto(rs.getString("ContenidoNeto"));
                producto.setCategoria(rs.getString("Categoria"));
                producto.setPrecio(rs.getFloat("Precio"));
                producto.setImagen(rs.getString("Imagen"));
                producto.setCantidadDisponible(rs.getInt("Cantidad_Disponible"));
                producto.setDescripcion(rs.getString("Descripcion"));
                
                return producto;
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún producto con el ID especificado.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta SQL para obtener el producto por ID.", "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
        return null;
    }
}

    public List<Producto> listaProductos() {
        List<Producto> productos = new ArrayList<>();
         String sql = "SELECT * FROM producto WHERE Eliminado = 0"; // Selecciona solo los productos no eliminados
    
        try (Connection con = conexion.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(rs.getInt("IdProducto"));
                producto.setNombre(rs.getString("Nombre"));
                producto.setMarca(rs.getString("Marca"));
                producto.setContenidoNeto(rs.getString("ContenidoNeto"));
                producto.setCategoria(rs.getString("Categoria"));
                producto.setPrecio(rs.getFloat("Precio"));
                producto.setImagen(rs.getString("Imagen"));
                producto.setCantidadDisponible(rs.getInt("Cantidad_Disponible"));
                producto.setDescripcion(rs.getString("Descripcion"));
                
                //System.out.println("Nombre: " + producto.getNombre());
                //System.out.println("Marca: " + producto.getMarca());
                productos.add(producto);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener la lista de productos.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        
        return productos;
    }
}

