package model;

/**
 *
 * @author Javs
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexion {
    String hostname = "localhost";
    String username = "root";
    String password = "";
    String database = "goldenmart";
    String port = "3306";
    
    public Connection getConnection(){
        Connection conn = null;
        String jdbcUrl = "jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database;
        //System.out.println(jdbcUrl);
        
        try {
            conn = DriverManager.getConnection(jdbcUrl, this.username, this.password);
            //System.out.println("Conexion Exitosa");
        } catch(SQLException e) {
           // System.out.println("Error: " + e.getMessage());
        } 
        return conn;
    }
}
