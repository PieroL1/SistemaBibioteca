package dao;

import modelo.Prestamo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import db.ConexionBD;

public class PrestamoDAO {
    private Connection conexion;

    public PrestamoDAO() {
        this.conexion = ConexionBD.obtenerConexion();
    }

    public boolean registrarPrestamo(Prestamo prestamo) {
        String sql = "INSERT INTO Prestamos (id_usuario, id_carnet, id_libro, id_bibliotecario, fecha_prestamo, fecha_devolucion, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, prestamo.getIdUsuario());
            ps.setInt(2, prestamo.getIdCarnet());
            ps.setInt(3, prestamo.getIdLibro());
            ps.setInt(4, prestamo.getIdBibliotecario());
            ps.setDate(5, new java.sql.Date(prestamo.getFechaPrestamo().getTime()));
            ps.setDate(6, new java.sql.Date(prestamo.getFechaDevolucion().getTime()));
            ps.setString(7, prestamo.getEstado());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Prestamo> obtenerPrestamos() {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM Prestamos";
        try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Prestamo prestamo = new Prestamo(
                    rs.getInt("id_prestamo"),
                    rs.getInt("id_usuario"),
                    rs.getInt("id_carnet"),
                    rs.getInt("id_libro"),
                    rs.getInt("id_bibliotecario"),
                    rs.getDate("fecha_prestamo"),
                    rs.getDate("fecha_devolucion"),
                    rs.getString("estado")
                );
                prestamos.add(prestamo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
    }

    public boolean actualizarEstadoPrestamo(int idPrestamo, String nuevoEstado) {
        String sql = "UPDATE Prestamos SET estado = ? WHERE id_prestamo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idPrestamo);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
