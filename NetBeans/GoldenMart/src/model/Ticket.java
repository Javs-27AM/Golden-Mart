
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Ticket {
    protected int FolioTicket; 
    private Date FechaEntrada; 
    private Time HoraEntrada; 
    private int Piso;
    
    public void obtenerTicket(){
       // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();
        this.FechaEntrada = java.sql.Date.valueOf(fechaActual); // Asignar la fecha actual
        
        // Obtener la hora actual
        Time horaActual = new Time(System.currentTimeMillis());
        this.HoraEntrada = horaActual; //Asignar la hora actual
        
        int pisoAsignado = asignarPiso();
        this.Piso = pisoAsignado; // Asignar el piso asignado
        
        if (!verificarLugaresDisponibles()) {
            System.out.println("No hay lugares disponibles en ningún piso. No voy a insertar");
        } else {
            insertarTicketEnBD(fechaActual, horaActual, pisoAsignado);
        }
        
        System.out.println("Fecha de Entrada: " + this.FechaEntrada);
        System.out.println("Hora de Entrada: " + this.HoraEntrada);
        System.out.println("Piso: " + this.Piso);
    
    }
    
    public boolean verificarLugaresDisponibles() {
        Conexion conexion = new Conexion();
        try (Connection conn = conexion.getConnection()) {
            String select = "SELECT CuposDisponiblesClientes FROM Cupos";

            try (PreparedStatement stmt = conn.prepareStatement(select);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int lugaresDisponiblesCliente = rs.getInt("CuposDisponiblesClientes");
                    if (lugaresDisponiblesCliente > 0) {
                        return true; // Si hay al menos un lugar disponible, retornar true
                    }
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al conectarse a la base de datos: " + ex.getMessage());
            ex.printStackTrace();
        }

        return false; // Si no hay lugares disponibles, retornar false
    }
    
    public int asignarPiso() {
        int pisoAsignado = -1; // Inicializar el piso asignado con un valor predeterminado
    
        if (!verificarLugaresDisponibles()) {
            System.out.println("No hay lugares disponibles en ningún piso.");
            return pisoAsignado; // Devolver -1 indicando que no hay lugares disponibles
        }
    
        Connection conn = null;
    
        try {
            Conexion conexion = new Conexion();
            conn = conexion.getConnection();

            String select = "SELECT CuposDisponiblesClientes FROM Cupos WHERE Piso = ?";

            int[] numerosDePisos = {1, 2, 3};

            for (int numeroDePiso : numerosDePisos) {
                try (PreparedStatement stmt = conn.prepareStatement(select)) {
                    stmt.setInt(1, numeroDePiso);

                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            int lugaresDisponiblesCliente = rs.getInt("CuposDisponiblesClientes");

                            if (lugaresDisponiblesCliente > 0) {
                                System.out.println("Hay " + lugaresDisponiblesCliente + " lugares disponibles para clientes en el piso " + numeroDePiso);
                                pisoAsignado = numeroDePiso;

                                String update = "UPDATE Cupos SET CuposDisponiblesClientes = ? WHERE Piso = ?";
                                try (PreparedStatement updateStmt = conn.prepareStatement(update)) {
                                    updateStmt.setInt(1, lugaresDisponiblesCliente - 1); // Restar uno al número de lugares disponibles
                                    updateStmt.setInt(2, numeroDePiso);
                                    updateStmt.executeUpdate(); // Ejecutar la actualización
                                }catch (SQLException ex) {
                                    System.err.println("Error al ejecutar la actualización: " + ex.getMessage());
                                    ex.printStackTrace();
                                }

                                break;
                            } else {
                                System.out.println("No hay lugares disponibles en el piso " + numeroDePiso);
                            }
                        } else {
                            System.out.println("No hay lugares disponibles en el piso " + numeroDePiso);
                        }
                    }
                }
            }

            if (pisoAsignado == -1) {
                System.out.println("No hay lugares disponibles en ningún piso.");
            } else {
                System.out.println("Se ha asignado el piso " + pisoAsignado + ".");
            }

        } catch (SQLException ex) {
            System.err.println("Error al conectarse a la base de datos: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    
        return pisoAsignado; // Devolver el piso asignado
    }
    
    private void insertarTicketEnBD(LocalDate fechaActual, Time horaActual, int piso) {
        Conexion Conexion = new Conexion();
        Connection conexion = Conexion.getConnection();
        if (conexion == null) {
            System.out.println("No se ha establecido la conexión a la base de datos.");
            return;
        }
        try {
            String sql = "INSERT INTO Ticket(FechaEntrada, HoraEntrada, PisoAsignado) VALUES (?, ?,?)";
            
            // Preparar la consulta SQL con los datos del ticket
            try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                pstmt.setDate(1, java.sql.Date.valueOf(fechaActual));
                pstmt.setTime(2, horaActual);
                pstmt.setInt(3,piso);

                // Ejecutar la consulta SQL
                pstmt.executeUpdate();
                System.out.println("Ticket insertado en la base de datos.");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar el ticket en la base de datos: " + e.getMessage());
        }
    }
    
    public String obtenerTicketDesdeBD() {
        
        Conexion Conexion = new Conexion();
        Connection conexion = Conexion.getConnection();
        
    if (conexion == null) {
        System.out.println("No se ha establecido la conexión a la base de datos.");
        return null;
    }
    
    try {
        // Consulta para obtener el último FolioTicket ingresado
        String sqlUltimoFolio = "SELECT MAX(FolioTicket) AS UltimoFolio FROM Ticket";
        
        int ultimoFolio = -1;
        
        // Ejecutar la consulta para obtener el último FolioTicket
        try (Statement stmtUltimoFolio = conexion.createStatement();
             ResultSet rsUltimoFolio = stmtUltimoFolio.executeQuery(sqlUltimoFolio)) {
            if (rsUltimoFolio.next()) {
                ultimoFolio = rsUltimoFolio.getInt("UltimoFolio");
            }
        }
        
        if (ultimoFolio == -1) {
            System.out.println("No se encontró ningún registro en la tabla Ticket.");
            return null;
        }
        
        // Consulta para obtener los detalles del ticket con el último FolioTicket
        String sqlDetalleTicket = "SELECT FechaEntrada, HoraEntrada, PisoAsignado FROM Ticket WHERE FolioTicket = ?";
        
        // Ejecutar la consulta para obtener los detalles del ticket
        try (PreparedStatement stmtDetalleTicket = conexion.prepareStatement(sqlDetalleTicket)) {
            stmtDetalleTicket.setInt(1, ultimoFolio);
            
            try (ResultSet rsDetalleTicket = stmtDetalleTicket.executeQuery()) {
                if (rsDetalleTicket.next()) {
                    // Obtener los valores del ticket
                    LocalDate fechaEntrada = rsDetalleTicket.getDate("FechaEntrada").toLocalDate();
                    Time horaEntrada = rsDetalleTicket.getTime("HoraEntrada");
                    int pisoAsignado = rsDetalleTicket.getInt("PisoAsignado");

                    // Devolver los valores como una cadena
                    return "FolioTicket: " + ultimoFolio + "\n\nFecha de Entrada: " + fechaEntrada + "\n\nHora de Entrada:    " + horaEntrada + "\n\n ---------------" + "\n\nPiso Asignado: " + pisoAsignado;
                }
            }
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener el ticket desde la base de datos: " + e.getMessage());
    }
    return null;
}
}
