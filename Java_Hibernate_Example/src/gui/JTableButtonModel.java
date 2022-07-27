package gui;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class JTableButtonModel extends AbstractTableModel {

    private String[] columns = {"Nazwa", "Typ", "Rodzaj drewna", "cena","ilość", ""/*guzik*/};



    @Override
    public int getRowCount() {
        return 3;
    }

    @Override
    public String getColumnName(int col) {
        return columns[col];
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(rowIndex == 4) return new JButton("NAZWA");
        return "0";
    }
}
