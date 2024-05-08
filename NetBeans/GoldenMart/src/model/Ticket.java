
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Ticket {
    private Connection con;
    Conexion conexion = new Conexion();
    private int idTicket;
    private int idVenta;
    
    public Ticket() {
    }

    public Ticket(int idTicket,int idVenta) {
        this.idTicket = idTicket;
        this.idVenta = idVenta;
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

 

    public void crearTicket() {
        String sql = "INSERT INTO Ticket (IdVenta) VALUES (?, ?)";

        try (Connection con = conexion.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
             pstmt.setInt(1, getIdVenta());
             pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Ticket generado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al generar el ticket.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
