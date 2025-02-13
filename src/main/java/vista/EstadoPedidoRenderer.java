
package vista;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class EstadoPedidoRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                 boolean isSelected, boolean hasFocus,
                                                 int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
                                          row, column);
        
        if (value instanceof String && column == 3) {
            String estado = (String) value;
            switch (estado.toUpperCase()) {
                case "PENDIENTE":
                    setBackground(Color.decode("#FFE6CC"));
                    break;
                case "APROBADO":
                    setBackground(Color.decode("#D5E8D4"));
                    break;
                case "RECHAZADO":
                    setBackground(Color.decode("#F2DEDE"));
                    break;
            }
        }
        
        return this;
    }
}