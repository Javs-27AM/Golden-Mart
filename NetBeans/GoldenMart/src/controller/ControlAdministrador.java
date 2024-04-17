/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import model.Conexion;
import model.Admin;
import view.LoginAdmin;




/**
 *
 * @author Javs
 */
public class ControlAdministrador implements ActionListener{
    public LoginAdmin view;
    private Admin admin;
    private Conexion conexion = new Conexion();
    private Connection con = conexion.getConnection();
    

public ControlAdministrador() {
    this.view = new LoginAdmin();
    this.admin = new Admin();
    
    this.view.jIngresar.addActionListener(this);
    this.view.jRegresar.addActionListener(this);
    this.view.jContrasena.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

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
}

public void actionPerformed(ActionEvent e) {
    if (e.getSource() ==view.jIngresar) {
        String user = view.jUsuario.getText(); // Obtiene el usuario electrónico del campo de texto
        String contrasenia = view.jContrasena.getText(); // Obtiene la contraseña del campo de texto
        System.out.println("Usuario ingresado: " + user);
            System.out.println("Contraseña ingresada: " + contrasenia);
         if (user.isEmpty() || contrasenia.isEmpty()) {
         JOptionPane optionPane = new JOptionPane("Por favor ingrese usuario y contraseña", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{ "Aceptar" });
         JDialog dialog = optionPane.createDialog("Error");
         dialog.setVisible(true);       
         
         } else {
                boolean accesoAutorizado = admin.verificarCredenciales(user, contrasenia);
                if (accesoAutorizado) {
                    JOptionPane optionPane = new JOptionPane("Acceso autorizado", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{"Aceptar"});
                    JDialog dialog = optionPane.createDialog("");
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