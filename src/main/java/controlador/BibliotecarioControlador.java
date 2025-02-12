package controlador;

import dao.BibliotecarioDAO;
import db.ConexionBD;
import java.sql.Connection;
import vista.Login;
import javax.swing.SwingUtilities;
import modelo.Bibliotecario;

public class BibliotecarioControlador {
    private Login vista;
    private BibliotecarioDAO dao;

    public BibliotecarioControlador(Login vista) {
        this.vista = vista;
        Connection conexion = ConexionBD.obtenerConexion();
        this.dao = new BibliotecarioDAO(conexion);
    }

    public void validarLogin(String dni) {
        Bibliotecario bibliotecario = dao.obtenerBibliotecarioPorDNI(dni);
        if (bibliotecario != null) {
            vista.mostrarMensaje("Bienvenido, " + bibliotecario.getNombre());
            // Aquí podrías abrir la ventana principal del sistema
        } else {
            vista.mostrarMensaje("DNI incorrecto o bibliotecario no registrado.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login login = new Login();
            login.setVisible(true);
        });
    }
}
