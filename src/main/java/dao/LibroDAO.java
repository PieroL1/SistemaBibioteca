package dao;

import modelo.Libro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {
    private Connection conexion;

    public LibroDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public List<Libro> buscarLibros(String titulo, String autor, String categoria, String anio) {
        List<Libro> resultados = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT l.id_libro, l.titulo, a.nombre as autor, c.nombre as categoria, " +
            "e.nombre as editorial, l.anio_edicion, l.stock " +
            "FROM libros l " +
            "JOIN autores a ON l.id_autor = a.id_autor " +
            "JOIN categorias c ON l.id_categoria = c.id_categoria " +
            "JOIN editoriales e ON l.id_editorial = e.id_editorial " +
            "WHERE 1=1"
        );

        List<Object> parametros = new ArrayList<>();

        if (titulo != null && !titulo.isEmpty()) {
            sql.append(" AND l.titulo LIKE ?");
            parametros.add("%" + titulo + "%");
        }

        if (autor != null && !autor.isEmpty()) {
            sql.append(" AND a.nombre LIKE ?");
            parametros.add("%" + autor + "%");
        }

        if (categoria != null && !categoria.isEmpty() && !categoria.equals("Todas")) {
            sql.append(" AND c.nombre = ?");
            parametros.add(categoria);
        }

        if (anio != null && !anio.isEmpty()) {
            sql.append(" AND l.anio_edicion = ?");
            parametros.add(Integer.parseInt(anio));
        }

        try (PreparedStatement stmt = conexion.prepareStatement(sql.toString())) {
            // Establecer parámetros
            for (int i = 0; i < parametros.size(); i++) {
                stmt.setObject(i + 1, parametros.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Libro libro = new Libro();
                libro.setId(rs.getInt("id_libro"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setCategoria(rs.getString("categoria"));
                libro.setEditorial(rs.getString("editorial"));
                libro.setAnioEdicion(rs.getInt("anio_edicion"));
                libro.setStock(rs.getInt("stock"));
                resultados.add(libro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al buscar libros: " + e.getMessage());
        }

        return resultados;
    }

    public List<String> obtenerCategorias() {
        List<String> categorias = new ArrayList<>();
        String sql = "SELECT nombre FROM categorias ORDER BY nombre";
        
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                categorias.add(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener categorías: " + e.getMessage());
        }
        
        return categorias;
    }
}