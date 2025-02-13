package modelo;

public class Session {
    private static int idUsuario = -1;  // -1 indica que no hay usuario autenticado

    public static void setIdUsuario(int id) {
        idUsuario = id;
    }

    public static int getIdUsuario() {
        return idUsuario;
    }
}
