package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame {
    private JButton btnGestionPrestamos;
    private JButton btnBuscarLibros;
    private JButton btnSalir;

    public Dashboard(String nombreUsuario, boolean esBibliotecario) {
        setTitle("Dashboard - Biblioteca El Sabio");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        
        JLabel lblBienvenida = new JLabel("Bienvenido, " + nombreUsuario, SwingConstants.CENTER);
        panel.add(lblBienvenida);
        
        if (esBibliotecario) {
            btnGestionPrestamos = new JButton("Gestión de Préstamos");
            btnGestionPrestamos.addActionListener(e -> abrirPrestamoVista());
            panel.add(btnGestionPrestamos);
        } else {
            btnBuscarLibros = new JButton("Buscar Libros");
            btnBuscarLibros.addActionListener(e -> abrirBusquedaLibrosVista());
            panel.add(btnBuscarLibros);
        }
        
        btnSalir = new JButton("Salir");
        panel.add(btnSalir);
        
        add(panel);
    }

    private void abrirPrestamoVista() {
        SwingUtilities.invokeLater(() -> {
            PrestamoVista prestamoVista = new PrestamoVista();
            prestamoVista.setVisible(true);
        });
    }
    
    private void abrirBusquedaLibrosVista() {
        SwingUtilities.invokeLater(() -> {
            BusquedaLibrosVista busquedaVista = new BusquedaLibrosVista();
            busquedaVista.setVisible(true);
        });
    }
    
    public void setSalirListener(ActionListener listener) {
        btnSalir.addActionListener(listener);
    }
}