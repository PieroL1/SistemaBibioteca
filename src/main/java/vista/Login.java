package vista;

import controlador.BibliotecarioControlador;
import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    private JTextField txtIdentificacion;
    private JButton btnIngresar;
    private JComboBox<String> cmbTipoUsuario;
    private BibliotecarioControlador controlador;
    private JPanel mainPanel;

    public Login() {
        setTitle("Biblioteca El Sabio - Acceso al Sistema");
        setSize(500, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        try {
            Image icon = new ImageIcon(getClass().getResource("/library-icon.png")).getImage();
            setIconImage(icon);
        } catch (Exception e) {
            System.err.println("No se pudo cargar el icono: " + e.getMessage());
        }

        controlador = new BibliotecarioControlador(this);
        
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(66, 139, 202);
                Color color2 = new Color(219, 238, 244);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setLayout(null);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setBounds(75, 60, 350, 460);
        contentPanel.setBackground(new Color(255, 255, 255, 220));

        JLabel iconLabel = new JLabel();
        ImageIcon libraryIcon = new ImageIcon(getClass().getResource("/library-large.png"));
        Image img = libraryIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        iconLabel.setIcon(new ImageIcon(img));
        iconLabel.setBounds(115, 30, 120, 120);
        contentPanel.add(iconLabel);

        JLabel titleLabel = new JLabel("Biblioteca El Sabio");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 21));
        titleLabel.setBounds(75, 160, 200, 40);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(titleLabel);

        JLabel tipoUsuarioLabel = new JLabel("Tipo de Usuario:");
        tipoUsuarioLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        tipoUsuarioLabel.setBounds(75, 200, 200, 25);
        contentPanel.add(tipoUsuarioLabel);

        cmbTipoUsuario = new JComboBox<>(new String[]{"Bibliotecario", "Lector"});
        cmbTipoUsuario.setBounds(75, 230, 200, 35);
        contentPanel.add(cmbTipoUsuario);

        JLabel identificacionLabel = new JLabel("Identificación:");
        identificacionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        identificacionLabel.setBounds(75, 270, 200, 25);
        contentPanel.add(identificacionLabel);

        txtIdentificacion = new JTextField();
        txtIdentificacion.setBounds(75, 300, 200, 35);
        txtIdentificacion.setFont(new Font("Arial", Font.PLAIN, 16));
        contentPanel.add(txtIdentificacion);

        btnIngresar = new JButton("Ingresar al Sistema");
        btnIngresar.setBounds(75, 350, 200, 40);
        btnIngresar.setBackground(new Color(66, 139, 202));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 16));
        btnIngresar.setFocusPainted(false);
        btnIngresar.addActionListener(e -> controlador.validarLogin(txtIdentificacion.getText(), (String) cmbTipoUsuario.getSelectedItem()));

        btnIngresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnIngresar.setBackground(new Color(51, 122, 183));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnIngresar.setBackground(new Color(66, 139, 202));
            }
        });
        
        contentPanel.add(btnIngresar);
        mainPanel.add(contentPanel);
        add(mainPanel);
        setResizable(false);
    }
    
    public void mostrarMensaje(String nombreUsuario) {
    MensajeBienvenida mensajePanel = new MensajeBienvenida(nombreUsuario);
    JOptionPane.showMessageDialog(this, mensajePanel, "Acceso Exitoso", JOptionPane.PLAIN_MESSAGE);
}

    
    public String getIdentificacion() {
        return txtIdentificacion.getText().trim();
    }
    
    public String getTipoUsuario() {
        return (String) cmbTipoUsuario.getSelectedItem();
    }
}