package vista;

import dao.PedidosDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import javax.swing.table.TableColumn;
import modelo.Pedido;
import modelo.Session;

public class EstadoPedidosVista extends JInternalFrame {
    private PedidosDAO pedidosDAO;
    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollPane;

    public EstadoPedidosVista(PedidosDAO pedidosDAO) {
        this.pedidosDAO = pedidosDAO;
        
        setTitle("Estado de Pedidos");
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setIconifiable(true);

        inicializarComponentes();
        configurarTabla();
        obtenerDatosPedidos();
        
        pack();
    }



    private void inicializarComponentes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnas = {"ID Pedido", "Libro", "Fecha Solicitud", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaPedidos = new JTable(modeloTabla);
        tablaPedidos.setAutoCreateRowSorter(true);
        tablaPedidos.setRowHeight(25);
        
        scrollPane = new JScrollPane(tablaPedidos);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        add(panel);
    }

    private void configurarTabla() {
        tablaPedidos.setDefaultRenderer(Object.class, new EstadoPedidoRenderer());
        
        // Configurar columnas
        TableColumn columnaID = tablaPedidos.getColumnModel().getColumn(0);
        columnaID.setPreferredWidth(80);
        columnaID.setMaxWidth(100);

        TableColumn columnaLibro = tablaPedidos.getColumnModel().getColumn(1);
        columnaLibro.setPreferredWidth(250);

        TableColumn columnaFecha = tablaPedidos.getColumnModel().getColumn(2);
        columnaFecha.setPreferredWidth(120);

        TableColumn columnaEstado = tablaPedidos.getColumnModel().getColumn(3);
        columnaEstado.setPreferredWidth(150);
    }

    private void obtenerDatosPedidos() {
        System.out.println("Buscando pedidos para usuario: " + Session.getIdUsuario());
        List<Pedido> listaPedidos = pedidosDAO.obtenerPedidosPorLector(Session.getIdUsuario());
        
        System.out.println("Cantidad de pedidos encontrados: " + listaPedidos.size());
        
        if (listaPedidos.isEmpty()) {
            System.out.println("No se encontraron pedidos para este usuario");
        } else {
            System.out.println("Encontrados " + listaPedidos.size() + " pedidos:");
            for (Pedido p : listaPedidos) {
                System.out.println("ID: " + p.getId() + 
                                 ", Libro: " + p.getLibroTitulo() + 
                                 ", Estado: " + p.getEstado());
            }
        }
        
        modeloTabla.setRowCount(0);
        for (Pedido pedido : listaPedidos) {
            Object[] fila = {
                pedido.getId(),
                pedido.getLibroTitulo(),
                pedido.getFechaPedido(),
                pedido.getEstado()
            };
            modeloTabla.addRow(fila);
        }
    }
}