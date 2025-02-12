package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PrestamoVista extends JFrame {
    private JTextField txtIdUsuario, txtIdCarnet, txtIdLibro, txtIdBibliotecario, txtFechaDevolucion;
    private JButton btnRegistrarPrestamo, btnActualizarEstado;
    private JTable tablaPrestamos;

    public PrestamoVista() {
        setTitle("Gestión de Préstamos");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));
        
        panel.add(new JLabel("ID Usuario:"));
        txtIdUsuario = new JTextField();
        panel.add(txtIdUsuario);
        
        panel.add(new JLabel("ID Carnet:"));
        txtIdCarnet = new JTextField();
        panel.add(txtIdCarnet);
        
        panel.add(new JLabel("ID Libro:"));
        txtIdLibro = new JTextField();
        panel.add(txtIdLibro);
        
        panel.add(new JLabel("ID Bibliotecario:"));
        txtIdBibliotecario = new JTextField();
        panel.add(txtIdBibliotecario);
        
        panel.add(new JLabel("Fecha Devolución (YYYY-MM-DD):"));
        txtFechaDevolucion = new JTextField();
        panel.add(txtFechaDevolucion);
        
        btnRegistrarPrestamo = new JButton("Registrar Préstamo");
        panel.add(btnRegistrarPrestamo);
        
        btnActualizarEstado = new JButton("Actualizar Estado");
        panel.add(btnActualizarEstado);
        
        add(panel, BorderLayout.NORTH);
        
        tablaPrestamos = new JTable();
        add(new JScrollPane(tablaPrestamos), BorderLayout.CENTER);
    }
    
    public void setRegistrarPrestamoListener(ActionListener listener) {
        btnRegistrarPrestamo.addActionListener(listener);
    }
    
    public void setActualizarEstadoListener(ActionListener listener) {
        btnActualizarEstado.addActionListener(listener);
    }
    
    public String getIdUsuario() {
        return txtIdUsuario.getText().trim();
    }
    
    public String getIdCarnet() {
        return txtIdCarnet.getText().trim();
    }
    
    public String getIdLibro() {
        return txtIdLibro.getText().trim();
    }
    
    public String getIdBibliotecario() {
        return txtIdBibliotecario.getText().trim();
    }
    
    public String getFechaDevolucion() {
        return txtFechaDevolucion.getText().trim();
    }
    
    public JTable getTablaPrestamos() {
        return tablaPrestamos;
    }
}
