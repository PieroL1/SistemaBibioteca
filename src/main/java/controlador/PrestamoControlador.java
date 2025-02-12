package controlador;

import dao.PrestamoDAO;
import modelo.Prestamo;
import java.util.Date;
import java.util.List;

public class PrestamoControlador {
    private PrestamoDAO prestamoDAO;

    public PrestamoControlador() {
        this.prestamoDAO = new PrestamoDAO();
    }

    public boolean registrarPrestamo(int idUsuario, int idCarnet, int idLibro, int idBibliotecario, Date fechaDevolucion) {
        Date fechaPrestamo = new Date(); // Fecha actual
        Prestamo prestamo = new Prestamo(0, idUsuario, idCarnet, idLibro, idBibliotecario, fechaPrestamo, fechaDevolucion, "Prestado");
        return prestamoDAO.registrarPrestamo(prestamo);
    }

    public List<Prestamo> obtenerTodosLosPrestamos() {
        return prestamoDAO.obtenerPrestamos();
    }

    public boolean actualizarEstadoPrestamo(int idPrestamo, String nuevoEstado) {
        return prestamoDAO.actualizarEstadoPrestamo(idPrestamo, nuevoEstado);
    }
}