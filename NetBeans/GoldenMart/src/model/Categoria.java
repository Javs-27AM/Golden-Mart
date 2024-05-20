
package model;
/*
 *
 * @author Javs
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JDialog;
import javax.swing.JOptionPane;


public class Categoria {
    public Connection con;
    public Conexion conexion = new Conexion();
    public String nombreCategoria;
    public int idCategoria;
    
    
    
    public Categoria() {
        }


    
    public Categoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
    
    public Categoria(int idCategoria,String nombreCategoria) {
        this.idCategoria = idCategoria;
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
        
        Object[] options = {"Aceptar"};
        if (nombreCategoria.isEmpty()) {
            JOptionPane optionPane = new JOptionPane("El campo Nombre Categoria es obligatorio.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
            JDialog dialog = optionPane.createDialog("Error");
            dialog.setVisible(true); return;
        }

        String sql = "INSERT INTO categoria (NombreCategoria) VALUES (?)";

        try (Connection con = conexion.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, nombreCategoria);

            pstmt.executeUpdate();
            JOptionPane optionPane = new JOptionPane("Categoria insertada correctamente en la base de datos.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{"Aceptar"}, "Aceptar");
            JDialog dialog = optionPane.createDialog("Éxito");
            dialog.setVisible(true);} catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta SQL para insertar una nueva categoría.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
