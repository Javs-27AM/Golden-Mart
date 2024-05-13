package model;

/**
 *
 * @author Javs
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Venta {
    private Connection con;
    Conexion conexion = new Conexion();
    public int idVenta;
    public LocalDate fechaVenta; // Cambiar el tipo de dato de Date a LocalDate
    public LocalTime horaVenta;
    private float total;

    public Venta(int idVenta, LocalDate fechaVenta, LocalTime horaVenta, float total) {
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.horaVenta = horaVenta;
        this.total = total;
    }

    public Venta(LocalDate fechaVenta, LocalTime horaVenta, float total) {
        this.fechaVenta = fechaVenta;
        this.horaVenta = horaVenta;
        this.total = total;
    }
    
    public Venta(){
    }

    // Getters y setters para los atributos de la clase Venta
    // Métodos de la clase Venta

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public LocalTime getHoraVenta() {
        return horaVenta;
    }

    public void setHoraVenta(LocalTime horaVenta) {
        this.horaVenta = horaVenta;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int insertarVentaEnBD(Venta venta) {
    String sql = "INSERT INTO Venta (FechaVenta, HoraVenta, Total) VALUES (?, ?, ?)";
    idVenta = -1; // Inicializar el ID de venta con un valor negativo por defecto
    
    try (Connection con = conexion.getConnection();
         PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
        pstmt.setDate(1, java.sql.Date.valueOf(venta.getFechaVenta())); // Convertir LocalDate a java.sql.Date
        pstmt.setTime(2, java.sql.Time.valueOf(venta.getHoraVenta())); // Convertir LocalTime a java.sql.Time
        pstmt.setFloat(3, venta.getTotal());
        pstmt.executeUpdate();
        
        // Obtener el ID generado para la venta
        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                idVenta = generatedKeys.getInt(1);
            }
        }
        Object[] options = {"Aceptar"};
        JOptionPane optionPane = new JOptionPane("Venta registrada correctamente.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
        JDialog dialog = optionPane.createDialog("Éxito");
        dialog.setVisible(true);
    } catch (SQLException ex) {
        Object[] options = {"Aceptar"};
        JOptionPane optionPane = new JOptionPane("Error al registrar la venta.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
        JDialog dialog = optionPane.createDialog("Error");
        dialog.setVisible(true);
        ex.printStackTrace();
    }
    
    return idVenta; // Devolver el ID de la venta insertada
}


    public List<Venta> buscarVentas(String textoBusqueda) {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT * FROM venta WHERE (IdVenta LIKE ? OR FechaVenta LIKE ? OR HoraVenta LIKE ? OR Total LIKE ?)";

        try (Connection con = conexion.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            String parametroBusqueda = "%" + textoBusqueda + "%";
            pstmt.setString(1, parametroBusqueda);
            pstmt.setString(2, parametroBusqueda);
            pstmt.setString(3, parametroBusqueda);
            pstmt.setString(4, parametroBusqueda);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Venta venta = new Venta();
                    venta.setIdVenta(rs.getInt("IdVenta"));
                    venta.setFechaVenta(rs.getDate("FechaVenta").toLocalDate()); // Convertir java.sql.Date a LocalDate
                    venta.setHoraVenta(rs.getTime("HoraVenta").toLocalTime()); // Convertir java.sql.Time a LocalTime
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

    
}
