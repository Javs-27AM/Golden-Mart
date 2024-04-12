/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Javs
 */
public class Admin {
    private String Username;
    private String Password;
    private Connection con;
    Conexion conexion = new Conexion();
   
    public Admin (String user, String pass){
     this.Username = user;
     this.Password = pass;
     }
    public Admin (){
     }
      public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }
    
   public boolean verificarCredenciales(String user, String contrasenia) {
    try {
        con = conexion.getConnection(); // Obtener la conexión desde la clase de conexión
        String sql = "SELECT COUNT(*) FROM administrador WHERE BINARY Username = ? AND BINARY Contraseña = ?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, user); // Usar el parámetro correo
            statement.setString(2, contrasenia); // Usar el parámetro contrasenia
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count == 1;
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (con != null) {
                con.close(); // Cerrar la conexión
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return false;
}

}
