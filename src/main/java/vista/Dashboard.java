package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame {
    private JButton btnBuscarLibros;
    private JButton btnEstadoPedidos;
    private JButton btnSalir;
    private JDesktopPane desktopPane;

    public Dashboard(String nombreUsuario, boolean esBibliotecario) {
        setTitle("Dashboard - Biblioteca El Sabio");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Crear el layout principal
        setLayout(new BorderLayout());
        
        // Panel superior para la bienvenida y botones
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        
        JLabel lblBienvenida = new JLabel("Bienvenido, " + nombreUsuario);
        topPanel.add(lblBienvenida);
        
        if (!esBibliotecario) {
            btnBuscarLibros = new JButton("Buscar Libros");
            btnBuscarLibros.addActionListener(e -> abrirBusquedaLibrosVista());
            topPanel.add(btnBuscarLibros);

            btnEstadoPedidos = new JButton("Ver Estado de Pedidos");
            btnEstadoPedidos.addActionListener(e -> abrirEstadoPedidosVista());
            topPanel.add(btnEstadoPedidos);
        }
        
        btnSalir = new JButton("Salir");
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
       /* // Verificar si ya hay una ventana de estado de pedidos abierta
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof EstadoPedidosVista) {
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
                EstadoPedidosVista estadoVista = new EstadoPedidosVista();
                desktopPane.add(estadoVista);
                estadoVista.setVisible(true);
                
                // Centrar la ventana interna
                Dimension desktopSize = desktopPane.getSize();
                Dimension frameSize = estadoVista.getSize();
                estadoVista.setLocation(
                    (desktopSize.width - frameSize.width) / 2,
                    (desktopSize.height - frameSize.height) / 2
                );
                
                estadoVista.setSelected(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this,
                    "Error al abrir la ventana de estado de pedidos: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });*/
    }

    public void setSalirListener(ActionListener listener) {
        btnSalir.addActionListener(listener);
    }
    
    // Método para acceder al desktopPane desde otras clases si es necesario
    public JDesktopPane getDesktopPane() {
        return desktopPane;
    }
}