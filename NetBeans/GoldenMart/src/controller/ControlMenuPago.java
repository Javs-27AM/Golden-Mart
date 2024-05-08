/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import view.MenuPago;
import java.awt.event.*;
/**
 *
 * @author Javs
 */
public class ControlMenuPago implements ActionListener {
    public MenuPago view;
    public float totalVenta;
    public ControlRealizarVenta controlRealizarVenta;

    /**
     *
     * @param totalVenta
     * @param controlRealizarVenta
     */
    public ControlMenuPago(float totalVenta, ControlRealizarVenta controlRealizarVenta) {
        this.view = new MenuPago();
        this.view.button_efectivo.addActionListener(this);
        this.view.button_tarjeta.addActionListener(this);
        this.view.button_cancelarPago.addActionListener(this);
        this.totalVenta = totalVenta;
        this.controlRealizarVenta = controlRealizarVenta;
        
    }

    public void actionPerformed(ActionEvent e) {
        

        if (e.getSource() == view.button_efectivo) {
            ControlPagoEfectivo controlPagoEfectivo = new ControlPagoEfectivo(totalVenta, controlRealizarVenta);
            controlPagoEfectivo.view.setVisible(true);
            this.view.dispose();
        } else if (e.getSource() == view.button_tarjeta) {
            ControlPagoTarjeta controlPagoTarjeta = new ControlPagoTarjeta(totalVenta, controlRealizarVenta);
            controlPagoTarjeta.view.setVisible(true);
            this.view.dispose();
        } else if (e.getSource() == view.button_cancelarPago) {
            view.dispose();
        }
    }
    
    public void cerrarVentana() {
        view.dispose();
    }
}
