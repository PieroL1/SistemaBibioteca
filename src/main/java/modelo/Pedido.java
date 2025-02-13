
package modelo;

import java.util.Date;

public class Pedido {
    private int id;
    private int idUsuario;
    private int idLibro;
    private Date fechaPedido;
    private String estado;
    private String libroTitulo;

    public Pedido(int id, int idUsuario, int idLibro, Date fechaPedido, String estado,String libroTitulo) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idLibro = idLibro;
        this.fechaPedido = fechaPedido;
        this.estado = estado;
        this.libroTitulo = libroTitulo;
    }

    // Getters
    public int getId() { return id; }
    public int getIdUsuario() { return idUsuario; }
    public int getIdLibro() { return idLibro; }
    public Date getFechaPedido() { return fechaPedido; }
    public String getEstado() { return estado; }
    public String getLibroTitulo() {
    return libroTitulo;  // Debes agregar este campo a la clase
}
}