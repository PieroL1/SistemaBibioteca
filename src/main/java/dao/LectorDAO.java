package dao;

import modelo.Lector;
import java.sql.*;
import db.ConexionBD;

public class LectorDAO {
    private Connection conexion;

    public LectorDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public Lector obtenerLectorPorCarnet(String carnet) {
        String sql = "SELECT * FROM Usuarios WHERE carnet = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, carnet);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Lector(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("carnet")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
