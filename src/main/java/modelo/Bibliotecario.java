package modelo;

public class Bibliotecario {
    private int id;  // Agregar este campo
    private String dni;
    private String nombre;

    public Bibliotecario(int id, String dni, String nombre) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }
}
