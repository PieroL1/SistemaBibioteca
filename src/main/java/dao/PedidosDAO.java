package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Pedido;

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
    
    
    public List<Pedido> obtenerPedidosPorLector(int idUsuario) {
    String sql = """
        SELECT p.id_pedido, l.titulo AS libro_titulo,
               p.fecha_pedido, p.estado,
               l.id_libro AS id_libro
        FROM pedidos p
        JOIN libros l ON p.id_libro = l.id_libro
        WHERE p.id_usuario = ?
        ORDER BY p.fecha_pedido DESC
        """;

    List<Pedido> pedidos = new ArrayList<>();
    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setInt(1, idUsuario);
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            Pedido pedido = new Pedido(
                rs.getInt("id_pedido"),
                idUsuario,
                rs.getInt("id_libro"),
                rs.getDate("fecha_pedido"),
                rs.getString("estado"),
                    rs.getString("libro_titulo")
            );
            pedidos.add(pedido);
        }
    } catch (SQLException e) {
        System.out.println("Error en la consulta: " + e.getMessage());
        e.printStackTrace();
    }
    return pedidos;
}

    public boolean actualizarEstadoPedido(int idPedido, String nuevoEstado) {
        String sql = "UPDATE pedidos SET estado = ? WHERE id_pedido = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, idPedido);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
