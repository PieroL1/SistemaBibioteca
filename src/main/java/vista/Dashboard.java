package vista;

import dao.PedidosDAO;
import db.ConexionBD;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import modelo.Session;

public class Dashboard extends JFrame {
    private JButton btnBuscarLibros;
    private JButton btnEstadoPedidos;
    private JButton btnSalir;
    private JDesktopPane desktopPane;
    private int nombreUsuarioActual;

    public Dashboard(String nombreUsuario, boolean esBibliotecario) {
    setTitle("Dashboard - Biblioteca El Sabio");
    setSize(800, 600);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    
    // Verificar que hay un usuario autenticado
        if (Session.getIdUsuario() == -1) {
            System.out.println("No hay usuario autenticado");
            return;
        }
    
    // Crear el layout principal
    setLayout(new BorderLayout());
    
    // Panel superior para la bienvenida y botones
    JPanel topPanel = new JPanel();
    topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
    topPanel.setBackground(Color.WHITE);
    topPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#e0e0e0")),
        BorderFactory.createEmptyBorder(10, 15, 10, 15)
    ));
    
    JLabel lblBienvenida = new JLabel("Bienvenido, " + nombreUsuario);
    lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 14));
    topPanel.add(lblBienvenida);
    
    if (!esBibliotecario) {
        btnBuscarLibros = crearBotonDecorado("Buscar Libros", 
            Color.decode("#4a90e2"), Color.WHITE);
        btnBuscarLibros.addActionListener(e -> abrirBusquedaLibrosVista());
        topPanel.add(btnBuscarLibros);

        btnEstadoPedidos = crearBotonDecorado("Ver Estado de Pedidos", 
            Color.decode("#2ecc71"), Color.WHITE);
        btnEstadoPedidos.addActionListener(e -> abrirEstadoPedidosVista());
        topPanel.add(btnEstadoPedidos);
    }
    
    // En el constructor de Dashboard
btnSalir = crearBotonDecorado("Salir", 
    Color.decode("#e74c3c"), Color.WHITE);
btnSalir.addActionListener(e -> {
    int opcion = JOptionPane.showConfirmDialog(
        this,
        "¿Está seguro que desea salir?",
        "Confirmar Salida",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE
    );
    
    if (opcion == JOptionPane.YES_OPTION) {
        this.dispose();
        new Login().setVisible(true);
    }
});
topPanel.add(btnSalir);
    
    // Agregar el panel superior
    add(topPanel, BorderLayout.NORTH);
    
    // Crear y agregar el JDesktopPane
    desktopPane = new JDesktopPane();
    desktopPane.setBackground(new Color(240, 240, 240));
    add(desktopPane, BorderLayout.CENTER);
    
    // Barra de estado en la parte inferior
    JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
    statusBar.setBorder(BorderFactory.createEtchedBorder());
    JLabel statusLabel = new JLabel("Biblioteca El Sabio - Sistema de Gestión");
    statusBar.add(statusLabel);
    add(statusBar, BorderLayout.SOUTH);
}

private JButton crearBotonDecorado(String texto, Color colorFondo, Color colorTexto) {
    JButton boton = new JButton(texto);
    boton.setBackground(colorFondo);
    boton.setForeground(colorTexto);
    boton.setFont(new Font("Segoe UI", Font.BOLD, 12));
    boton.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(Color.WHITE, 1),
        BorderFactory.createEmptyBorder(8, 15, 8, 15)
    ));
    boton.setFocusPainted(false);
    boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    boton.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            boton.setBackground(colorFondo.darker());
        }
        @Override
        public void mouseExited(java.awt.event.MouseEvent evt) {
            boton.setBackground(colorFondo);
        }
    });
    return boton;
}

    private void abrirBusquedaLibrosVista() {
        // Verificar si ya hay una ventana de búsqueda abierta
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof BusquedaLibrosVista) {
                try {
                    frame.setSelected(true);
                    return;
                } catch (java.beans.PropertyVetoException e) {
                    e.printStackTrace();
                }
            }
        }

        // Si no hay una ventana abierta, crear una nueva
        SwingUtilities.invokeLater(() -> {
            try {
                BusquedaLibrosVista busquedaVista = new BusquedaLibrosVista();
                desktopPane.add(busquedaVista);
                busquedaVista.setVisible(true);
                
                // Centrar la ventana interna
                Dimension desktopSize = desktopPane.getSize();
                Dimension frameSize = busquedaVista.getSize();
                busquedaVista.setLocation(
                    (desktopSize.width - frameSize.width) / 2,
                    (desktopSize.height - frameSize.height) / 2
                );
                
                busquedaVista.setSelected(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this,
                    "Error al abrir la ventana de búsqueda: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }

   private void abrirEstadoPedidosVista() {
    // Verificar si ya hay una ventana abierta
    for (JInternalFrame frame : desktopPane.getAllFrames()) {
        if (frame instanceof EstadoPedidosVista) {
            try {
                frame.setSelected(true);
                return;
            } catch (PropertyVetoException e) {
                e.printStackTrace();
            }
        }
    }

    SwingUtilities.invokeLater(() -> {
        try {
            Connection conexion = ConexionBD.obtenerConexion();
            if (conexion != null) {
                EstadoPedidosVista vista = new EstadoPedidosVista(
                    new PedidosDAO(conexion)
                );
                desktopPane.add(vista);
                vista.setVisible(true);
                
                Dimension desktopSize = desktopPane.getSize();
                Dimension frameSize = vista.getSize();
                vista.setLocation(
                    (desktopSize.width - frameSize.width) / 2,
                    (desktopSize.height - frameSize.height) / 2
                );
                
                vista.setSelected(true);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al conectar con la base de datos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error al abrir la ventana de pedidos: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    });
}
    public void setSalirListener(ActionListener listener) {
        btnSalir.addActionListener(listener);
    }
    
    // Método para acceder al desktopPane desde otras clases si es necesario
    public JDesktopPane getDesktopPane() {
        return desktopPane;
    }
}