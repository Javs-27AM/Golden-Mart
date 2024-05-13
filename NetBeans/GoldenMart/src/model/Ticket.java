
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Ticket {
    public Connection con;
    Conexion conexion = new Conexion();
    public int idTicket;
    public int idVenta;
    
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

 

    public void crearTicket(int idVenta) {
        
        String sql = "INSERT INTO Ticket (IdVenta) VALUES (?)";

        try (Connection con = conexion.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
             pstmt.setInt(1, idVenta); // Usar el ID de la venta obtenido
             pstmt.executeUpdate();
            /*Object[] options = {"Aceptar"};
            JOptionPane optionPane = new JOptionPane("Ticket generado correctamente.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
            JDialog dialog = optionPane.createDialog("Éxito");
            dialog.setVisible(true);*/
        
        } catch (SQLException ex) {
            Object[] options = {"Aceptar"};
            JOptionPane optionPane = new JOptionPane("Error al generar el ticket.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
            JDialog dialog = optionPane.createDialog("Error");
            dialog.setVisible(true);
            
            ex.printStackTrace();
        }
    }
    
    
}
