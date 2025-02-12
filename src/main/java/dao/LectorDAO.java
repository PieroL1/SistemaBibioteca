package dao;

import modelo.Lector;
import java.sql.*;
import db.ConexionBD;

public class LectorDAO {
    private Connection conexion;

    public LectorDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public Lector obtenerLectorPorCarnet(String codigoCarnet) {
        String sql = "SELECT u.id_usuario, u.nombre, u.apellido, c.codigo_carnet, c.estado " +
                     "FROM Usuarios u " +
                     "JOIN carnets c ON u.id_usuario = c.id_usuario " +
                     "WHERE c.codigo_carnet = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, codigoCarnet);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Lector(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("codigo_carnet"),
                    rs.getString("estado")  // Agregado para validar si está activo
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
