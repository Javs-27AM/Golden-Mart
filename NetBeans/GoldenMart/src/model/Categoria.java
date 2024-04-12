
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
/*
 *
 * @author Javs
 */

public class Categoria {
    private Connection con;
    private Conexion conexion = new Conexion();

    private String nombreCategoria;

    
    public Categoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    // Getters y setters para los atributos de la clase Categoria
    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    // Método para insertar una nueva categoría en la base de datos
    public void insertarCategoria() {
        if (nombreCategoria.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error: El nombre de la categoría es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "INSERT INTO categoria (NombreCategoria) VALUES (?)";

        try (Connection con = conexion.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, nombreCategoria);

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Categoría insertada correctamente en la base de datos.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta SQL para insertar una nueva categoría.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
