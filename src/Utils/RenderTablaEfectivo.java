package Utils;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class RenderTablaEfectivo extends DefaultTableCellRenderer {
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        //Filas pares color gris
        //Filas impares color blanco
        if (row % 2 == 0) {
            setBackground(new Color(200, 200, 200));
        } else {
            setBackground(new Color(255, 255, 255));
        }

        //Pintar color azul la fila selecciona
        if (isSelected) {
            setBackground(new Color(65, 105, 255));
            setForeground(Color.WHITE);
        } else {
            setForeground(Color.BLACK);
        }

        //letras de columna 4 color rojo
        if (column == 4) {
            setForeground(Color.RED);
        }

        //Pintar negro con letras amarillas celda seleccionada
        if (hasFocus) {
            setBackground(Color.BLACK);
            setForeground(Color.YELLOW);
        }
        
        return this;
    }
}
