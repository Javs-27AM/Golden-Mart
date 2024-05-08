/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author Javs
 */
import javax.swing.table.DefaultTableModel;


public class NonEditableTableModel extends DefaultTableModel {
    public int[] nonEditableColumns;

    public NonEditableTableModel(Object[][] data, Object[] columnNames, int[] nonEditableColumns) {
        super(data, columnNames);
        this.nonEditableColumns = nonEditableColumns;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        for (int nonEditableColumn : nonEditableColumns) {
            if (column == nonEditableColumn) {
                return false;
            }
        }
        return true;
    }
}
