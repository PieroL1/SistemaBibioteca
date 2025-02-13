package controlador;

import dao.LibroDAO;
import dao.PedidosDAO;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;
import db.ConexionBD;
import modelo.Libro;
import vista.BusquedaLibrosVista;

public class BusquedaLibrosControlador {
    private BusquedaLibrosVista vista;
    private LibroDAO libroDAO;
    private Connection conexion;

    public BusquedaLibrosControlador(BusquedaLibrosVista vista) {
        this.vista = vista;
        inicializarConexion();
    }

    private void inicializarConexion() {
    try {
        conexion = ConexionBD.obtenerConexion();
        if (conexion == null) {
            throw new Exception("No se pudo establecer la conexión con la base de datos");
        }
        
        libroDAO = new LibroDAO(conexion);
        System.out.println("Conexión y DAO inicializados correctamente.");
        
        cargarCategorias();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(vista,
            "Error al conectar con la base de datos: " + e.getMessage(),
            "Error de Conexión",
            JOptionPane.ERROR_MESSAGE);
    }
}


    private void cargarCategorias() {
        try {
            List<String> categorias = libroDAO.obtenerCategorias();
            vista.getCbCategoria().removeAllItems(); // Limpiar items existentes
            vista.getCbCategoria().addItem("Todas");
            for (String categoria : categorias) {
                vista.getCbCategoria().addItem(categoria);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista,
                "Error al cargar las categorías: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public void buscarLibros(String titulo, String autor, String categoria, String anio) {
    try {
        if (conexion == null || conexion.isClosed()) {
            inicializarConexion();
        }

        if (conexion == null || libroDAO == null) { // Asegurar que el DAO también esté inicializado
            throw new Exception("No hay conexión con la base de datos");
        }

        String categoriaFiltro = "Todas".equals(categoria) ? null : categoria;

        if (anio != null && !anio.trim().isEmpty()) {
            try {
                Integer.parseInt(anio.trim());
            } catch (NumberFormatException e) {
                throw new Exception("El año debe ser un número válido");
            }
        }

        List<Libro> libros = libroDAO.buscarLibros(titulo, autor, categoriaFiltro, anio);
        actualizarTablaResultados(libros);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(vista,
            "Error al buscar libros: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}


    private void actualizarTablaResultados(List<Libro> libros) {
    System.out.println("Libros encontrados: " + libros.size());
    for (Libro libro : libros) {
        System.out.println("Libro: " + libro.getTitulo());
    }

    Object[][] datos = new Object[libros.size()][7];
    for (int i = 0; i < libros.size(); i++) {
        Libro libro = libros.get(i);
        datos[i] = new Object[]{
            libro.getId(),
            libro.getTitulo(),
            libro.getAutor(),
            libro.getCategoria(),
            libro.getEditorial(),
            libro.getAnioEdicion(),
            libro.getStock()
        };
    }
    vista.actualizarResultados(datos);
}


    // Método para cerrar la conexión cuando se cierra la ventana
    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    public void solicitarPrestamo(int idUsuario, int idLibro) {
    try {
        PedidosDAO pedidosDAO = new PedidosDAO(conexion);

        // Verificar si el usuario tiene menos de 3 pedidos pendientes
        if (pedidosDAO.contarPedidosPendientes(idUsuario) >= 3) {
            JOptionPane.showMessageDialog(vista, "No puedes solicitar más de 3 préstamos pendientes.", "Límite alcanzado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Registrar el pedido
        pedidosDAO.registrarPedido(idUsuario, idLibro);
        JOptionPane.showMessageDialog(vista, "Pedido registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(vista, "Error al solicitar préstamo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}


// Simulación de obtener el usuario autenticado (debe integrarse con la autenticación)
private int obtenerUsuarioActual() {
    return 1; // Aquí deberías obtener el ID real del usuario autenticado
}

}