package modelo;

public class Bibliotecario {
    private String dni;
    private String nombre;

    public Bibliotecario(String dni, String nombre) {
        this.dni = dni;
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }
}
