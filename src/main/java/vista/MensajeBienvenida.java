package vista;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.AbstractBorder;

public class MensajeBienvenida extends JPanel {
    public MensajeBienvenida(String nombreUsuario) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(400, 180));
        setBackground(new Color(248, 249, 250));

        // Panel superior con título
        JPanel headerPanel = createHeaderPanel();
        
        // Panel del mensaje estilizado con el nombre del usuario
        JPanel messagePanel = createMessagePanel("Use con propiedad el sistema", nombreUsuario);
        
        // Añadir componentes con espaciado
        add(Box.createVerticalStrut(10));
        add(headerPanel);
        add(Box.createVerticalStrut(15));
        add(messagePanel);
        add(Box.createVerticalStrut(10));
        
        // Borde redondeado general
        setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(12, new Color(222, 226, 230)),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(248, 249, 250));
        headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel("¡Bienvenido!");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(33, 37, 41));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(titleLabel);
        
        return headerPanel;
    }
    
    private JPanel createMessagePanel(String mensaje, String nombreUsuario) {
        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.setBackground(Color.WHITE);
        messagePanel.setBorder(BorderFactory.createCompoundBorder(
            new ShadowBorder(),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        JLabel mensajeLabel = new JLabel("<html><div style='text-align: center; width: 250px;'>"
                + "<span style='font-size: 14px; color: #495057;'>" + mensaje + "</span><br>"
                + "<span style='font-size: 14px; font-weight: bold; color: #343a40;'> " + nombreUsuario + "</span></div></html>");
        mensajeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        mensajeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mensajeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        messagePanel.add(mensajeLabel, BorderLayout.CENTER);
        
        return messagePanel;
    }
}

class RoundedBorder extends AbstractBorder {
    private final int radius;
    private final Color color;

    public RoundedBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        g2d.dispose();
    }
}

class ShadowBorder extends AbstractBorder {
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(0, 0, 0, 30));

        for (int i = 0; i < 3; i++) {
            g2d.drawRoundRect(x + i, y + i, width - (2 * i) - 1, height - (2 * i) - 1, 10, 10);
        }
        g2d.dispose();
    }
}
