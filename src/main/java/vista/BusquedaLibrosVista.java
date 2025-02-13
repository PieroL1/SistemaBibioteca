package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import controlador.BusquedaLibrosControlador;
import modelo.Session;

public class BusquedaLibrosVista extends JInternalFrame {
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JComboBox<String> cbCategoria;
    private JTextField txtAnio;
    private JTable tblResultados;
    private DefaultTableModel modeloTabla;
    private JButton btnBuscar;
    private JButton btnLimpiar;
    private BusquedaLibrosControlador controlador;
   private int idUsuarioActual = Session.getIdUsuario();


    public BusquedaLibrosVista() {
        super("Búsqueda de Libros", true, true, true, true);
        
        inicializarComponentes();
        configurarVentana();
        
        controlador = new BusquedaLibrosControlador(this);
    }

    private void inicializarComponentes() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelBusqueda = new JPanel(new GridLayout(2, 4, 5, 5));
        panelBusqueda.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        panelBusqueda.add(txtTitulo);

        panelBusqueda.add(new JLabel("Autor:"));
        txtAutor = new JTextField();
        panelBusqueda.add(txtAutor);

        panelBusqueda.add(new JLabel("Categoría:"));
        cbCategoria = new JComboBox<>();
        panelBusqueda.add(cbCategoria);

        panelBusqueda.add(new JLabel("Año:"));
        txtAnio = new JTextField();
        panelBusqueda.add(txtAnio);

        btnBuscar = new JButton("Buscar");
        panelBusqueda.add(btnBuscar);

        btnLimpiar = new JButton("Limpiar");
        panelBusqueda.add(btnLimpiar);

        mainPanel.add(panelBusqueda, BorderLayout.NORTH);

        // Definir modelo de tabla con una columna extra para botones
        String[] columnas = {"ID", "Título", "Autor", "Categoría", "Editorial", "Año", "Stock", "Acción"};
        modeloTabla = new DefaultTableModel(columnas, 0);

        tblResultados = new JTable(modeloTabla);
        tblResultados.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(tblResultados);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        setContentPane(mainPanel);

        // Eventos de botones
        btnBuscar.addActionListener(e -> realizarBusqueda());
        btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    private void realizarBusqueda() {
        if (controlador == null) {
            JOptionPane.showMessageDialog(this, "Error: El controlador no está inicializado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String titulo = txtTitulo.getText().trim();
        String autor = txtAutor.getText().trim();
        String categoria = (String) cbCategoria.getSelectedItem();
        String anio = txtAnio.getText().trim();

        controlador.buscarLibros(titulo, autor, categoria, anio);
    }

    private void limpiarCampos() {
        txtTitulo.setText("");
        txtAutor.setText("");
        cbCategoria.setSelectedIndex(-1);
        txtAnio.setText("");
        modeloTabla.setRowCount(0);
    }

    public void actualizarResultados(Object[][] datos) {
    modeloTabla.setRowCount(0);
    
    for (Object[] fila : datos) {
        modeloTabla.addRow(new Object[]{fila[0], fila[1], fila[2], fila[3], fila[4], fila[5], fila[6], "Solicitar"}); 
    }

    // Agregar un MouseListener para capturar clics en la columna "Acción"
    tblResultados.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int row = tblResultados.rowAtPoint(evt.getPoint());
            int col = tblResultados.columnAtPoint(evt.getPoint());

            if (col == 7) { // Si es la columna "Acción"
                int idLibro = (int) tblResultados.getValueAt(row, 0);
                solicitarPrestamo(idLibro);
            }
        }
    });
}


private void solicitarPrestamo(int idLibro) {
    int idUsuario = Session.getIdUsuario();
    
    if (idUsuario == -1) {
        JOptionPane.showMessageDialog(this, "Error: No hay un usuario autenticado.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (controlador != null) {
        controlador.solicitarPrestamo(idUsuario, idLibro);
    } else {
        JOptionPane.showMessageDialog(this, "Error: El controlador no está inicializado.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    
    private void configurarVentana() {
    setSize(800, 600);
    setMinimumSize(new Dimension(600, 400));
    setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
}


    // Métodos getter
    public JTextField getTxtTitulo() { return txtTitulo; }
    public JTextField getTxtAutor() { return txtAutor; }
    public JComboBox<String> getCbCategoria() { return cbCategoria; }
    public JTextField getTxtAnio() { return txtAnio; }
    public JTable getTblResultados() { return tblResultados; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
}
