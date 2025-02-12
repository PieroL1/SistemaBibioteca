package vista;

import controlador.BibliotecarioControlador;
import javax.swing.*;

public class Login extends JFrame {
    private JTextField txtDni;
    private JButton btnIngresar;
    private BibliotecarioControlador controlador;

    public Login() {
        setTitle("Login - Biblioteca El Sabio");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        controlador = new BibliotecarioControlador(this);
        
        JPanel panel = new JPanel();
        panel.add(new JLabel("DNI:"));
        txtDni = new JTextField(10);
        panel.add(txtDni);
        
        btnIngresar = new JButton("Ingresar");
        btnIngresar.addActionListener(e -> controlador.validarLogin(txtDni.getText()));
        panel.add(btnIngresar);
        
        add(panel);
    }
    
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    
    public String getDni() {
        return txtDni.getText().trim();
    }
    
}
