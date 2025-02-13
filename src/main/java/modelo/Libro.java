package modelo;
public class Libro {
    private int id;
    private String titulo;
    private String autor;
    private String categoria;
    private String editorial;
    private int anioEdicion;
    private int stock;

    // Constructores
    public Libro() {}

    public Libro(int id, String titulo, String autor, String categoria, 
                String editorial, int anioEdicion, int stock) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.editorial = editorial;
        this.anioEdicion = anioEdicion;
        this.stock = stock;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }

    public int getAnioEdicion() { return anioEdicion; }
    public void setAnioEdicion(int anioEdicion) { this.anioEdicion = anioEdicion; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}