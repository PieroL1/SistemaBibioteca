package dao;

import modelo.Bibliotecario;
import java.sql.*;

public class BibliotecarioDAO {
    private Connection conexion;

    public BibliotecarioDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public Bibliotecario obtenerBibliotecarioPorDNI(String dni) {
        String sql = "SELECT * FROM Bibliotecarios WHERE dni = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Bibliotecario(rs.getString("dni"), rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
