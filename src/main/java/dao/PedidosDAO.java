package dao;

import java.sql.*;

public class PedidosDAO {
    private Connection conexion;

    public PedidosDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // Método para registrar un nuevo pedido de préstamo
    public boolean registrarPedido(int idUsuario, int idLibro) {
        String sql = "INSERT INTO pedidos (id_usuario, id_libro, fecha_pedido, estado) VALUES (?, ?, CURDATE(), 'Pendiente')";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idLibro);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para contar los pedidos pendientes de un usuario
    public int contarPedidosPendientes(int idUsuario) {
        String sql = "SELECT COUNT(*) FROM pedidos WHERE id_usuario = ? AND estado = 'Pendiente'";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
