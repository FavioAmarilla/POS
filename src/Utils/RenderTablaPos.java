package Utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class RenderTablaPos extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        //Alinear a la derecha datos de la columna 2
        if (column == 2) {
            setHorizontalAlignment(RIGHT);
        }
        
        //Alinear a la derecha datos de la columna 3
        if (column == 3) {
            setHorizontalAlignment(RIGHT);
        }
        
        //Poner en negrita, color rojo, y alinear a la derecha los datos de
        //la columna 4
        if (column == 4) {
            setFont(new Font("DIALOG", Font.BOLD, 14));
            setForeground(Color.RED);
            setHorizontalAlignment(RIGHT);
        }
        return this;
    }
}
