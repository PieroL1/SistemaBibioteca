package controlador;

import dao.PedidosDAO;
import javax.swing.JOptionPane;
import java.sql.Connection;
import db.ConexionBD;

public class PedidosControlador {
    private PedidosDAO pedidosDAO;
    private Connection conexion;

    public PedidosControlador() {
        try {
            conexion = ConexionBD.obtenerConexion();
            if (conexion == null) {
                throw new Exception("No se pudo establecer la conexión con la base de datos");
            }
            this.pedidosDAO = new PedidosDAO(conexion);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void solicitarPrestamo(int idUsuario, int idLibro) {
        try {
            if (pedidosDAO.contarPedidosPendientes(idUsuario) >= 3) {
                JOptionPane.showMessageDialog(null, "No puedes solicitar más de 3 préstamos pendientes.", "Límite alcanzado", JOptionPane.WARNING_MESSAGE);
                return;
            }

            pedidosDAO.registrarPedido(idUsuario, idLibro);
            JOptionPane.showMessageDialog(null, "Pedido registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al solicitar préstamo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
