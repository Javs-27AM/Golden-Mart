
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Ticket {
    private Connection con;
    Conexion conexion = new Conexion();
    private int idTicket;
    private int idVenta; 
    private LocalDate fechaGeneracion;
    private LocalTime horaGeneracion;
    
    public Ticket() {
    }

    public Ticket(int idVenta, LocalDate fechaGeneracion, LocalTime horaGeneracion) {
        this.idVenta = idVenta;
        this.fechaGeneracion = fechaGeneracion;
        this.horaGeneracion = horaGeneracion;
    }

    public Ticket(int idTicket, int idVenta, LocalDate fechaGeneracion, LocalTime horaGeneracion) {
        this.idTicket = idTicket;
        this.idVenta = idVenta;
        this.fechaGeneracion = fechaGeneracion;
        this.horaGeneracion = horaGeneracion;
    }

    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public LocalDate getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDate fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public LocalTime getHoraGeneracion() {
        return horaGeneracion;
    }

    public void setHoraGeneracion(LocalTime horaGeneracion) {
        this.horaGeneracion = horaGeneracion;
    }

    public void agregarContenido(JTextArea jTicket, int idTicket) {
        StringBuilder contenido = new StringBuilder();

        ImageIcon logo = new ImageIcon("/imagenes/logoticket.png"); // Reemplaza la ruta con la ubicación de tu archivo de imagen
        contenido.append(logo).append("\n\n");

        contenido.append("Ticket ID: ").append(idTicket).append("\n\n");

        contenido.append("Fecha: ").append(getFechaGeneracion()).append("\n");

        contenido.append("Hora: ").append(getHoraGeneracion()).append("\n\n");


        contenido.append("Productos:\n");
        // Agrega aquí la lista de productos

        contenido.append("\nTotal: [Total aquí]\n");

        jTicket.setText(contenido.toString());
    }

    public void crearTicket() {
        String sql = "INSERT INTO Ticket (IdVenta, FechaGeneracion) VALUES (?, ?)";

        try (Connection con = conexion.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
             pstmt.setInt(1, getIdVenta());
             pstmt.setDate(2, java.sql.Date.valueOf(getFechaGeneracion()));
             pstmt.setTime(3, java.sql.Time.valueOf(getHoraGeneracion()));
             pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Ticket generado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al generar el ticket.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
