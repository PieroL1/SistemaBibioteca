package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame {
    private JButton btnBuscarLibros;
    private JButton btnEstadoPedidos;
    private JButton btnSalir;

    public Dashboard(String nombreUsuario, boolean esBibliotecario) {
        setTitle("Dashboard - Biblioteca El Sabio");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        
        JLabel lblBienvenida = new JLabel("Bienvenido, " + nombreUsuario, SwingConstants.CENTER);
        panel.add(lblBienvenida);
        
        if (!esBibliotecario) {
            btnBuscarLibros = new JButton("Buscar Libros");
            btnBuscarLibros.addActionListener(e -> abrirBusquedaLibrosVista());
            panel.add(btnBuscarLibros);

            btnEstadoPedidos = new JButton("Ver Estado de Pedidos");
            btnEstadoPedidos.addActionListener(e -> abrirEstadoPedidosVista());
            panel.add(btnEstadoPedidos);
        }
        
        btnSalir = new JButton("Salir");
        panel.add(btnSalir);
        
        add(panel);
    }

    private void abrirBusquedaLibrosVista() {
        SwingUtilities.invokeLater(() -> {
            BusquedaLibrosVista busquedaVista = new BusquedaLibrosVista();
            busquedaVista.setVisible(true);
        });
    }

    private void abrirEstadoPedidosVista() {
        SwingUtilities.invokeLater(() -> {
            EstadoPedidosVista estadoVista = new EstadoPedidosVista();
            estadoVista.setVisible(true);
        });
    }

    public void setSalirListener(ActionListener listener) {
        btnSalir.addActionListener(listener);
    }
}
