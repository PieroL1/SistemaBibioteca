package controlador;

import dao.BibliotecarioDAO;
import dao.LectorDAO;
import modelo.Bibliotecario;
import modelo.Lector;
import vista.Login;
import vista.Dashboard;
import javax.swing.SwingUtilities;
import java.sql.Connection;
import db.ConexionBD;

public class BibliotecarioControlador {
    private Login vista;
    private BibliotecarioDAO bibliotecarioDAO;
    private LectorDAO lectorDAO;

    public BibliotecarioControlador(Login vista) {
        this.vista = vista;
        Connection conexion = ConexionBD.obtenerConexion();
        this.bibliotecarioDAO = new BibliotecarioDAO(conexion);
        this.lectorDAO = new LectorDAO(conexion);
    }

    public void validarLogin(String identificacion, String tipoUsuario) {
        if (tipoUsuario.equals("Bibliotecario")) {
            Bibliotecario bibliotecario = bibliotecarioDAO.obtenerBibliotecarioPorDNI(identificacion);
            if (bibliotecario != null) {
                vista.mostrarMensaje( bibliotecario.getNombre());
                abrirDashboard(bibliotecario.getNombre(), true);
                vista.dispose();
            } else {
                vista.mostrarMensaje("DNI incorrecto o bibliotecario no registrado.");
            }
        } else if (tipoUsuario.equals("Lector")) {
            Lector lector = lectorDAO.obtenerLectorPorCarnet(identificacion);
            if (lector != null) {
                vista.mostrarMensaje("Bienvenido, " + lector.getNombre());
                abrirDashboard(lector.getNombre(), false);
                vista.dispose();
            } else {
                vista.mostrarMensaje("Carnet incorrecto o lector no registrado. ");
            }
        }
    }

    private void abrirDashboard(String nombreUsuario, boolean esBibliotecario) {
        SwingUtilities.invokeLater(() -> {
            Dashboard dashboard = new Dashboard(nombreUsuario, esBibliotecario);
            dashboard.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login login = new Login();
            login.setVisible(true);
        });
    }
}
