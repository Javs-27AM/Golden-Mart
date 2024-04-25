/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author Javs
 */

import java.util.List;
import javax.swing.JOptionPane;
import model.Producto;

public class ControlCancelarVenta {
    private List<Producto> productosVendidos;

    public ControlCancelarVenta(List<Producto> productosVendidos) {
        this.productosVendidos = productosVendidos;

        try {
            // Verificar si la lista de productos vendidos está vacía
            if (productosVendidos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay productos en el carrito.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Salir del constructor si no hay productos en el carrito
            }

            // Restaurar la cantidad disponible de todos los productos en la lista
            for (Producto producto : productosVendidos) {
                int cantidadInicial = producto.obtenerCantidadDisponibleInicial(producto.getIdProducto());
                if (cantidadInicial != -1) {
                    // Restaurar la cantidad disponible del producto
                    producto.actualizarCantidadDisponibleEnBD(producto.getIdProducto(), cantidadInicial);
                }
            }

            // Vaciar la lista de productos vendidos
            productosVendidos.clear();
            JOptionPane.showMessageDialog(null, "Se ha cancelado la venta y se han restaurado los productos en el stock.", "Venta cancelada", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error al cancelar la venta.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
