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
    MenuPago view;
    private float totalVenta;

    public ControlMenuPago(float totalVenta) {
        this.view = new MenuPago();
        this.view.button_efectivo.addActionListener(this);
        this.view.button_tarjeta.addActionListener(this);
        this.view.button_cancelarPago.addActionListener(this);
        this.totalVenta = totalVenta;
    }

    public void actionPerformed(ActionEvent e) {
        

        if (e.getSource() == view.button_efectivo) {
            ControlPagoEfectivo controlPagoEfectivo = new ControlPagoEfectivo(totalVenta);
            controlPagoEfectivo.view.setVisible(true);
            this.view.dispose();
            System.out.println("Total de la venta (efectivo): $" + totalVenta);
                
           // realizarPagoEfectivo();
        } else if (e.getSource() == view.button_tarjeta) {
            ControlPagoTarjeta controlPagoTarjeta = new ControlPagoTarjeta(totalVenta);
            controlPagoTarjeta.view.setVisible(true);
            System.out.println("Total de la venta (efectivo): $" + totalVenta);
                
            //realizarPagoTarjeta();
        } else if (e.getSource() == view.button_cancelarPago) {
            // Aquí irá la lógica para cancelar el pago
            //cancelarPago();
        }
    }
}
