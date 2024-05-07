/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package GoldenMart;
import controller.*;
import java.sql.Connection;
import model.Conexion;

/**
 *
 * @author Javs
 */
public class GoldenMart {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Conexion Conexion = new Conexion();
        Connection conexion = Conexion.getConnection();
        ControlMenu controlMenu = new ControlMenu();
    }
    
}
