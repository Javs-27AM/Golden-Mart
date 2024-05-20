package controller;

import java.awt.event.*;
import java.sql.Connection;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import model.Conexion;
import model.Producto;
import view.ModificarProducto;

/**
 *
 * @author Javs
 */
    // Suponiendo que el ID del producto a modificar se obtiene como parámetro del constructor
    public class ControlModificar implements ActionListener {
    public ModificarProducto view;
    private Producto producto;
    public int idProductoBuscado;
    private Connection con;
    Conexion conexion = new Conexion();

    // Constructor que recibe el ID del producto a modificar
    public ControlModificar(int idProductoBuscado) {
        this.idProductoBuscado = idProductoBuscado; // Asignar el ID del producto a la variable de instancia
        this.view = new ModificarProducto();
        this.producto = new Producto();

        // Intentar obtener el producto por su ID
        Producto productoAModificar = producto.obtenerProductoPorId(idProductoBuscado);
        if (productoAModificar != null) {
            // Si se encuentra el producto, cargar los campos del formulario
            view.jIdProducto.setText(String.valueOf(productoAModificar.getIdProducto()));
            view.jNombre.setText(productoAModificar.getNombre());
            view.jMarca.setText(productoAModificar.getMarca());
            view.jContenido.setText(productoAModificar.getContenidoNeto());
            view.jCategoria.setSelectedItem(productoAModificar.getCategoria());
            view.jPrecio.setText(String.valueOf(productoAModificar.getPrecio()));
            view.jImagen.setText(productoAModificar.getImagen());
            view.jCantidad.setText(String.valueOf(productoAModificar.getCantidadDisponible()));
            view.jDescripcion.setText(productoAModificar.getDescripcion());
        } else {
            // Si no se encuentra el producto, mostrar un mensaje de error y salir
            JOptionPane.showMessageDialog(null, "No se encontró ningún producto con el ID especificado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Asignar los listeners de los botones
        this.view.jModificar.addActionListener(this);
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

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.jModificar) {
            // Obtener los valores de los campos del formulario
            String nombre = view.jNombre.getText();
            String marca = view.jMarca.getText();
            String contenidoNeto = view.jContenido.getText(); // Obtener el contenido neto del campo de texto
            String categoria = (String) view.jCategoria.getSelectedItem(); // Obtener la categoría seleccionada del combo box
            String precioText = view.jPrecio.getText();
            String imagen = view.jImagen.getText();
            String cantidadText = view.jCantidad.getText();
            String descripcion = view.jDescripcion.getText();
            // Mensaje personalizado para el botón de aceptar
            Object[] options = {"Aceptar"};
            String rutaImagenError = "ruta/a/la/imagen_de_error.png"; 

            // Cargar la imagen de error
            ImageIcon imagenError = new ImageIcon(rutaImagenError);
            
            // Validar que el campo de marca no esté vacío
            if (marca.isEmpty()) {
                JOptionPane optionPane = new JOptionPane("El campo Marca es obligatorio.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
                JDialog dialog = optionPane.createDialog("Error");
                dialog.setVisible(true);
                return;
            }

            // Validar que el campo de contenido neto no esté vacío
            if (contenidoNeto.isEmpty()) {
                JOptionPane optionPane = new JOptionPane("El campo Contenido Neto es obligatorio.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
                JDialog dialog = optionPane.createDialog("Error");
                dialog.setVisible(true);
                return;
            }

            // Validar que el campo de categoría no esté vacío
            if (categoria.isEmpty()) {
                JOptionPane optionPane = new JOptionPane("Debe seleccionar una categoría.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
                JDialog dialog = optionPane.createDialog("Error");
                dialog.setVisible(true);
                return;
            }

            // Validar que el campo de precio no esté vacío y sea un número válido
            if (precioText.isEmpty()) {
                JOptionPane optionPane = new JOptionPane("El campo Precio es obligatorio.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
                JDialog dialog = optionPane.createDialog("Error");
                dialog.setVisible(true);
                return;
            }

            try {
                float precio = Float.parseFloat(precioText);
                // Validar que el precio sea mayor o igual a cero
                if (precio < 0) {
                    JOptionPane optionPane = new JOptionPane("El precio debe ser mayor o igual a cero.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
                    JDialog dialog = optionPane.createDialog("Error");
                    dialog.setVisible(true);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane optionPane = new JOptionPane("El precio debe ser un número válido.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
                JDialog dialog = optionPane.createDialog("Error");
                dialog.setVisible(true);
                return;
            }
            if (imagen.isEmpty()) {
                JOptionPane optionPane = new JOptionPane("Debe seleccionar una imagen.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, imagenError, options, options[0]);
                JDialog dialog = optionPane.createDialog("Error");
                dialog.setVisible(true);
                return;
            }

            // Validar que el campo de cantidad no esté vacío y sea un número válido
            if (cantidadText.isEmpty()) {
                JOptionPane optionPane = new JOptionPane("El campo Cantidad es obligatorio.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
                JDialog dialog = optionPane.createDialog("Error");
                dialog.setVisible(true);
                return;
            }

            try {
                int cantidad = Integer.parseInt(cantidadText);
                // Validar que la cantidad sea mayor o igual a cero
                if (cantidad < 0) {
                    JOptionPane optionPane = new JOptionPane("La cantidad debe ser mayor o igual a cero.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
                    JDialog dialog = optionPane.createDialog("Error");
                    dialog.setVisible(true);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane optionPane = new JOptionPane("La cantidad debe ser un número válido.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
                JDialog dialog = optionPane.createDialog("Error");
                dialog.setVisible(true);
                return;
            }

            // Validar que el campo de descripción no esté vacío
            if (descripcion.isEmpty()) {
                JOptionPane optionPane = new JOptionPane("El campo Descripción es obligatorio.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
                JDialog dialog = optionPane.createDialog("Error");
                dialog.setVisible(true);
                return;
            }
            
            // Validar que los campos obligatorios no estén vacíos
            if (nombre.isEmpty() || marca.isEmpty() || contenidoNeto.isEmpty() || categoria.isEmpty() || precioText.isEmpty() || cantidadText.isEmpty() || descripcion.isEmpty()) {
                JOptionPane optionPane = new JOptionPane("Todos los campos son obligatorios.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
                JDialog dialog = optionPane.createDialog("Error");
                dialog.setVisible(true);
                return;
            }

            // ... Validación de los demás campos aquí ...

            try {
                float precio = Float.parseFloat(precioText);
                int cantidad = Integer.parseInt(cantidadText);
               
                // Obtener el ID del producto que se va a modificar (Suponiendo que ya está disponible)
                producto.obtenerProductoPorId(idProductoBuscado);
                
                // Modificar el producto con los nuevos valores
                producto.modificarProducto(idProductoBuscado, nombre, marca, contenidoNeto, categoria, precio, imagen, cantidad, descripcion);
                
                // Reiniciar el formulario limpiando los campos
                view.jIdProducto.setText(""); 
                view.jNombre.setText("");   
                view.jMarca.setText("");
                view.jContenido.setText("");
                view.jCategoria.setSelectedIndex(0);
                view.jPrecio.setText("");
                view.jImagen.setText("");
                view.jCantidad.setText("");
                view.jDescripcion.setText("");
                ControlGestionarInventario controlGestionarInventario = new ControlGestionarInventario();
                controlGestionarInventario.view.setVisible(true);
                view.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Por favor ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == view.jCancelar) {
            // Limpiar los campos del formulario
            ControlGestionarInventario controlGestionarInventario = new ControlGestionarInventario();
            controlGestionarInventario.view.setVisible(true);
            view.jIdProducto.setText(""); 
            view.jNombre.setText("");
            view.jMarca.setText("");
            view.jContenido.setText("");
            view.jCategoria.setSelectedIndex(0);
            view.jPrecio.setText("");
            view.jImagen.setText("");
            view.jCantidad.setText("");
            view.jDescripcion.setText("");
        } else if (e.getSource() == view.jRegresar) {
            // Mostrar el frame SesionAdmin y eliminar el frame RegistrarProducto
            ControlGestionarInventario controlGestionarInventario = new ControlGestionarInventario();
            controlGestionarInventario.view.setVisible(true);
            view.dispose();
        }
    }
}

