package modelo;

public class Lector {
    private int id;
    private String nombre;
    private String apellido;
    private String codigoCarnet;
    private String estado;

    public Lector(int id, String nombre, String apellido, String codigoCarnet, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.codigoCarnet = codigoCarnet;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCodigoCarnet() {
        return codigoCarnet;
    }

    public void setCodigoCarnet(String codigoCarnet) {
        this.codigoCarnet = codigoCarnet;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    
    
}
