/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
/**
 *
 * @author Javs
 */



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.Conexion;
import model.Admin;
import view.LoginAdmin;





public class ControlAdministrador implements ActionListener{
    public LoginAdmin view;
    public Admin admin;
    public Conexion conexion = new Conexion();
    public Connection con = conexion.getConnection();
    

public ControlAdministrador() {
    this.view = new LoginAdmin();
    this.admin = new Admin();
    this.view.jIngresar.addActionListener(this);
    this.view.jRegresar.addActionListener(this);
    this.view.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    this.view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Simulamos el evento de clic en el botón "Regresar"
                actionPerformed(new ActionEvent(view.jRegresar, ActionEvent.ACTION_PERFORMED, "Regresar"));
            }
        });
    this.view.jContrasena.addKeyListener(new KeyListener() {
           
            @Override
            public void keyTyped(KeyEvent e) {
                // Obtiene el carácter ingresado
                char c = e.getKeyChar();
                // Verifica si el carácter es un espacio en blanco
                if (c == ' ') {
                    // Consumir el evento para evitar que se escriba el espacio
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // Llama al método actionPerformed cuando se presiona Enter en el campo de contraseña
                    actionPerformed(new ActionEvent(view.jIngresar, ActionEvent.ACTION_PERFORMED, null));
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
    
    this.view.jUsuario.addKeyListener(new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
            // Obtiene el carácter ingresado
            char c = e.getKeyChar();
            // Verifica si el carácter es un espacio en blanco
            if (c == ' ') {
                // Consumir el evento para evitar que se escriba el espacio
                e.consume();
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                // Pasa al campo de contraseña cuando se presiona Enter en el campo de usuario
                view.jContrasena.requestFocus();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}
    });
}

    @Override
    public void actionPerformed(ActionEvent e) {
    if (e.getSource() ==view.jIngresar) {
        String user = view.jUsuario.getText(); // Obtiene el usuario electrónico del campo de texto
        char[] contraseniaChars = view.jContrasena.getPassword(); // Obtiene la contraseña como una matriz de caracteres
        
        // Convierte la contraseña de matriz de caracteres a cadena
        String contrasenia = new String(contraseniaChars);
        
          //  System.out.println("Contraseña ingresada: " + contrasenia);
         if (user.isEmpty() || contrasenia.isEmpty()) {
         JOptionPane optionPane = new JOptionPane("Por favor ingrese usuario y contraseña", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{ "Aceptar" });
         JDialog dialog = optionPane.createDialog("Error");
         dialog.setVisible(true);       
         
         } else {
                boolean accesoAutorizado = admin.verificarCredenciales(user, contrasenia);
                if (accesoAutorizado) {
                    JOptionPane optionPane = new JOptionPane("Acceso autorizado", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{"Aceptar"});
                    JDialog dialog = optionPane.createDialog("Exíto");
                    dialog.setVisible(true);
                    ControlSesionAdmin controlSesionAdmin = new ControlSesionAdmin();
                    controlSesionAdmin.view.setVisible(true);
                    this.view.dispose();
                } else {
                    // Para el acceso denegado
                    JOptionPane optionPane = new JOptionPane("Acceso denegado", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{"Aceptar"});
                    JDialog dialog = optionPane.createDialog("Error");
                    dialog.setVisible(true);
                }
        }
    }
    if(e.getSource() == view.jRegresar){
        ControlMenu controlMenu = new ControlMenu(); // Crear una instancia del controlador del menú
        controlMenu.view.setVisible(true); 
        this.view.dispose();
    }
}

}