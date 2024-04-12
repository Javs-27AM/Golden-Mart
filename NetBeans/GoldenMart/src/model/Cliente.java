/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Javs
 */
public class Cliente {
    private String Nombres;
    private String AP;
    private String AM;
    private String Telefono;
    private String Correo;
    private String Password;
    private String Folio;
    private Connection con;
    Conexion conexion = new Conexion();
    
     public Cliente (String nombre, String ap, String am, String tel, String gmail, String pass){
     this.Nombres = nombre;
     this.AP = ap;
     this.AM = am;
     this.Telefono = tel;
     this.Correo = gmail;
     this.Password = pass;
     }
     public Cliente(){}
    /**
     * @return the Nombres
     */
    public String obtenerNombres() {
        return Nombres;
    }
    /**
     * @return the AP
     */
    public String obtenerAP() {
        return AP;
    }
    /**
     * @return the AM
     */
    public String obtenerAM() {
        return AM;
    }
    /**
     * @return the Telefono
     */
    public String obtenerTelefono() {
        return Telefono;
    }
    /**
     * @return the Correo
     */
    public String obtenerCorreo() {
        return Correo;
    }
    /**
     * @return the Password
     */
    public String obtenergetPassword() {
        return Password;
    }

    /**
     * @param Password the Password to set
     */
    public String ingresarPassword(String Password) {
       return this.Password = Password;
    }

    /**
     * @return the Folio
     */
    public String obtenergetFolio() {
        return Folio;
    }

    /**
     * @param Folio the Folio to set
     */
    public void ingresarFolio(String Folio) {
        this.Folio = Folio;
    }
     public int insertarCliente(String nombre, String apellidoPaterno, String apellidoMaterno,
            String telefono, String correo, String contrasenia) {
    int clienteID = -1;
    String sql = "INSERT INTO cliente (Nombres, ApellidoPaterno, ApellidoMaterno, NumeroTelefono, CorreoElectronico, Contrasenia) VALUES (?, ?, ?, ?, ?, ?)";
    try (Connection con = conexion.getConnection();
         PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        pstmt.setString(1, nombre);
        pstmt.setString(2, apellidoPaterno);
        pstmt.setString(3, apellidoMaterno);
        pstmt.setString(4, telefono);
        pstmt.setString(5, correo);
        pstmt.setString(6, contrasenia);

        
        int rowsAffected = pstmt.executeUpdate();

        if (rowsAffected > 0) {
            
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                clienteID = generatedKeys.getInt(1);
                System.out.println("Cliente insertado correctamente. ClienteID: " + clienteID);
            } else {
                System.out.println("Error al obtener el ID del cliente.");
            }
        } else {
            System.out.println("No se insertaron filas. Verifica tus datos.");
        }
        System.out.println("Registro insertado correctamente.");
    } catch (SQLException ex) {
        ex.printStackTrace();
    }

    return clienteID;
}
}
