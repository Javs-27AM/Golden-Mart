package controller;


/**
 *
 * @author Javs
 */

import java.awt.event.*;
import java.sql.Connection;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import model.Conexion;
import model.Categoria;
import view.RegistrarCategoria;


public class ControlRegistroCategoria implements ActionListener {
    public RegistrarCategoria view;
    public Categoria categoria;
    public Connection con;
    Conexion conexion = new Conexion();

    public ControlRegistroCategoria() {
        this.view = new RegistrarCategoria();
        this.categoria = new Categoria();
        
        this.view.jAgregar.addActionListener(this);
        this.view.jCancelar.addActionListener(this);
        this.view.jRegresar.addActionListener(this);
        this.view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Simular el evento de clic en el botón "Regresar"
                actionPerformed(new ActionEvent(view.jRegresar, ActionEvent.ACTION_PERFORMED, "Regresar"));
            }
        });
    }

    public void iniciar() {
        view.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.jAgregar) {
            // Obtener los valores de los campos del formulario
            String nombreCategoria = view.jnombreCategoria.getText();
            
            // Mensaje personalizado para el botón de aceptar
            Object[] options = {"Aceptar"};
            
            // Validar que el campo de nombre no esté vacío
            if (nombreCategoria.isEmpty()) {
                JOptionPane optionPane = new JOptionPane("El campo Nombre Categoria es obligatorio.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
                JDialog dialog = optionPane.createDialog("Error");
                dialog.setVisible(true);
                return;
            }

            

                // Crear una nueva instancia de Producto con los valores obtenidos
                categoria = new Categoria(nombreCategoria);
                // Insertar el nuevo producto en la base de datos
                categoria.insertarCategoria();
                 // Reiniciar el formulario limpiando los campos
                view.jnombreCategoria.setText("");
                
        } else if (e.getSource() == view.jCancelar) {
            // Limpiar los campos del formulario
            view.jnombreCategoria.setText("");
            
        } else if (e.getSource() == view.jRegresar) {
            // Mostrar el frame SesionAdmin y eliminar el frame RegistrarProducto
            ControlSesionAdmin controlSesionAdmin = new ControlSesionAdmin();
            controlSesionAdmin.view.setVisible(true);
            view.dispose();
        }
    }
}
