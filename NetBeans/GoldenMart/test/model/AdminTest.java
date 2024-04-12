/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package model;

import java.sql.SQLException;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Javs
 */
public class AdminTest {
    
    public AdminTest() {
    }

    /**
     * Test of getUsername method, of class Admin.
     */
    @Test
    public void testgetUsername() {
        System.out.println("getUsername");
        Admin instance = new Admin();
        String result = instance.getUsername();
        assertEquals(null, result);
    }

    /**
     * Test of setUsername method, of class Admin.
     */
    @Test
    public void testsetUsername() {
        System.out.println("setUsername");
        String Username = "";
        Admin instance = new Admin();
        instance.setUsername(Username);
    }

    @Test
    public void testGetPassword() {
        System.out.println("obtenerPassword");
        Admin instance = new Admin();
        String result = instance.getPassword();
        assertEquals(null, result); 
    }
    
    // URL de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/goldenmart?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Creamos una tabla de prueba en la base de datos
    @Before
    public void setUp() throws SQLException {
        /*try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE administrador (Username VARCHAR(255), Contrasenia VARCHAR(255))");
            stmt.execute("INSERT INTO administrador (Username, Contrasenia) VALUES ('admin@example.com', 'password123')");
        }*/
    }

    /**
     * Test of setPassword method, of class Admin.
     */
    @Test
    public void testSetPassword() {
        System.out.println("setPassword");
        String Password = "";
        Admin instance = new Admin();
        instance.setPassword(Password);
    }

    /**
     * Test of verificarCredenciales method, of class Admin.
     */
    @Test
    public void testVerificarCredenciales() {
        System.out.println("verificarCredenciales");
        String username = "";
        String contrasenia = "";
        Admin instance = new Admin();
        boolean expResult = false;
        boolean result = instance.verificarCredenciales(username, contrasenia);
        assertEquals(expResult, result);
    }
    
}
