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
    // Panel principal con diseño BorderLayout y bordes decorativos
    JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
    panelPrincipal.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(Color.decode("#4a90e2"), 1),
        BorderFactory.createEmptyBorder(15, 15, 15, 15)
    ));

    // Panel de título con fondo azul claro
    JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panelTitulo.setBackground(Color.decode("#e6f3ff"));
    
    JLabel lblBienvenida = new JLabel("¡Bienvenido!");
    lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 16));
    lblBienvenida.setForeground(Color.decode("#4a90e2"));
    panelTitulo.add(lblBienvenida);

    // Panel de botones con fondo gris claro
    JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
    panelBotones.setBackground(Color.decode("#f5f5f5"));
    panelBotones.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#e0e0e0")));
    
    btnBuscar = new JButton("Buscar");
    btnBuscar.setBackground(Color.decode("#4a90e2"));
    btnBuscar.setForeground(Color.WHITE);
    btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 12));
    btnBuscar.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    
    btnLimpiar = new JButton("Limpiar");
    btnLimpiar.setBackground(Color.decode("#ff6b6b"));
    btnLimpiar.setForeground(Color.WHITE);
    btnLimpiar.setFont(new Font("Segoe UI", Font.BOLD, 12));
    btnLimpiar.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    
    panelBotones.add(btnLimpiar);
    panelBotones.add(btnBuscar);

    // Panel de campos de búsqueda con diseño GridBagLayout
    JPanel panelBusqueda = new JPanel(new GridBagLayout());
    panelBusqueda.setBackground(Color.WHITE);
    panelBusqueda.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createEmptyBorder(15, 15, 15, 15),
        BorderFactory.createLineBorder(Color.decode("#e0e0e0"))
    ));
    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1;

    // Campos de búsqueda organizados en filas
    JLabel lblTitulo = new JLabel("Título:");
    lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    gbc.gridx = 0; gbc.gridy = 0;
    panelBusqueda.add(lblTitulo, gbc);
    
    txtTitulo = new JTextField(20);
    txtTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    txtTitulo.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(Color.decode("#e0e0e0")),
        BorderFactory.createEmptyBorder(5, 8, 5, 8)
    ));
    gbc.gridx = 1; gbc.gridy = 0;
    panelBusqueda.add(txtTitulo, gbc);

    JLabel lblAutor = new JLabel("Autor:");
    lblAutor.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    gbc.gridx = 0; gbc.gridy = 1;
    panelBusqueda.add(lblAutor, gbc);
    
    txtAutor = new JTextField(20);
    txtAutor.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    txtAutor.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(Color.decode("#e0e0e0")),
        BorderFactory.createEmptyBorder(5, 8, 5, 8)
    ));
    gbc.gridx = 1; gbc.gridy = 1;
    panelBusqueda.add(txtAutor, gbc);

    JLabel lblCategoria = new JLabel("Categoría:");
    lblCategoria.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    gbc.gridx = 0; gbc.gridy = 2;
    panelBusqueda.add(lblCategoria, gbc);
    
    cbCategoria = new JComboBox<>();
    cbCategoria.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    cbCategoria.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(Color.decode("#e0e0e0")),
        BorderFactory.createEmptyBorder(5, 8, 5, 8)
    ));
    gbc.gridx = 1; gbc.gridy = 2;
    panelBusqueda.add(cbCategoria, gbc);

    JLabel lblAnio = new JLabel("Año:");
    lblAnio.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    gbc.gridx = 0; gbc.gridy = 3;
    panelBusqueda.add(lblAnio, gbc);
    
    txtAnio = new JTextField(10);
    txtAnio.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    txtAnio.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(Color.decode("#e0e0e0")),
        BorderFactory.createEmptyBorder(5, 8, 5, 8)
    ));
    gbc.gridx = 1; gbc.gridy = 3;
    panelBusqueda.add(txtAnio, gbc);

    // Panel de resultados con scroll
    String[] columnas = {"ID", "Título", "Autor", "Categoría", "Editorial", "Año", "Stock", "Acción"};
    modeloTabla = new DefaultTableModel(columnas, 0);
    tblResultados = new JTable(modeloTabla) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    tblResultados.setRowHeight(30);
    tblResultados.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    tblResultados.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
    tblResultados.getTableHeader().setBackground(Color.decode("#4a90e2"));
    tblResultados.getTableHeader().setForeground(Color.WHITE);
    
    JScrollPane scrollPane = new JScrollPane(tblResultados);
    scrollPane.setBorder(BorderFactory.createEmptyBorder());
    scrollPane.getViewport().setBackground(Color.WHITE);

    // Organización final del panel principal
    JPanel panelSuperior = new JPanel(new BorderLayout(0, 10));
    panelSuperior.add(panelTitulo, BorderLayout.NORTH);
    panelSuperior.add(panelBotones, BorderLayout.SOUTH);
    
    JPanel panelCentral = new JPanel(new BorderLayout(0, 15));
    panelCentral.add(panelBusqueda, BorderLayout.NORTH);
    panelCentral.add(scrollPane, BorderLayout.CENTER);

    panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
    panelPrincipal.add(panelCentral, BorderLayout.CENTER);

    setContentPane(panelPrincipal);

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
