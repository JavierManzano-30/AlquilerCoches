package view;

import dao.AlquilerDAO;
import dao.CocheDAO;
import model.Cliente;
import model.DetalleAlquiler;
import utils.FileManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class AlquileresView extends JFrame {

    private JTable tableAlquileres;
    private Cliente cliente;
    private List<DetalleAlquiler> detalles;

    public AlquileresView(Cliente cliente) {
        this.cliente = cliente;

        setTitle("Mis Alquileres");
        setSize(1000, 600);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        setContentPane(contenedor);

        // Barra superior
        JPanel barra = new JPanel(null);
        barra.setPreferredSize(new Dimension(1000, 40));
        barra.setBackground(Color.WHITE);
        contenedor.add(barra, BorderLayout.NORTH);

        JLabel lblUsuario = new JLabel("👤 " + cliente.getNombre());
        lblUsuario.setBounds(10, 10, 200, 20);
        lblUsuario.setFont(new Font("Monospaced", Font.BOLD, 13));
        barra.add(lblUsuario);

        JButton btnMin = crearBotonVentana("—", new Color(166, 203, 226));
        btnMin.setBounds(920, 7, 30, 25);
        btnMin.addActionListener(e -> setState(ICONIFIED));
        barra.add(btnMin);

        JButton btnCerrar = crearBotonVentana("X", new Color(230, 105, 120));
        btnCerrar.setBounds(960, 7, 30, 25);
        btnCerrar.addActionListener(e -> System.exit(0));
        barra.add(btnCerrar);

        // Tabla
        JPanel panelCentro = new JPanel();
        panelCentro.setOpaque(false);
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        contenedor.add(panelCentro, BorderLayout.CENTER);

        JLabel lblTitulo = new JLabel("Tus Alquileres");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panelCentro.add(lblTitulo);

        tableAlquileres = new JTable();
        JScrollPane scroll = new JScrollPane(tableAlquileres);
        scroll.setMaximumSize(new Dimension(900, 200));
        panelCentro.add(scroll);

        // Botones
        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JButton btnVolver = crearBotonAzul("Volver");
        btnVolver.addActionListener(e -> {
            new PrincipalView(cliente).setVisible(true);
            dispose();
        });

        JButton btnFactura = crearBotonAzul("Generar Factura");
        btnFactura.addActionListener(e -> generarFactura());

        JButton btnVer = crearBotonAzul("Ver Factura");
        btnVer.addActionListener(e -> verFactura());

        JButton btnCancelar = crearBotonAzul("Cancelar Reserva");
        btnCancelar.addActionListener(e -> cancelarReserva());

        panelBotones.add(btnVolver);
        panelBotones.add(Box.createHorizontalStrut(10));
        panelBotones.add(btnFactura);
        panelBotones.add(Box.createHorizontalStrut(10));
        panelBotones.add(btnVer);
        panelBotones.add(Box.createHorizontalStrut(10));
        panelBotones.add(btnCancelar);

        panelCentro.add(panelBotones);

        cargarAlquileres();
    }

    private void cargarAlquileres() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
            "Marca", "Modelo", "Año", "Días", "Precio/Día", "Total (€)"
        });

        AlquilerDAO dao = new AlquilerDAO();
        detalles = dao.detallesCliente(cliente.getId());

        for (DetalleAlquiler detalle : detalles) {
            model.addRow(new Object[]{
                detalle.getCoche().getMarca(),
                detalle.getCoche().getModelo(),
                detalle.getCoche().getAnio(),
                detalle.getDias(),
                detalle.getCoche().getPrecio(),
                detalle.getTotal()
            });
        }

        tableAlquileres.setModel(model);
    }

    private void cancelarReserva() {
        int fila = tableAlquileres.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un alquiler para cancelar.");
            return;
        }

        DetalleAlquiler detalle = detalles.get(fila);
        int cocheId = detalle.getCoche().getId();

        int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro que deseas cancelar la reserva?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        boolean eliminado = new AlquilerDAO().eliminarAlquilerPorCocheYCliente(cocheId, cliente.getId());
        boolean disponible = new CocheDAO().marcarComoDisponible(cocheId);

        if (eliminado && disponible) {
            JOptionPane.showMessageDialog(this, "Reserva cancelada correctamente.");
            cargarAlquileres();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo cancelar la reserva.");
        }
    }

    private void generarFactura() {
        if (detalles == null || detalles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay alquileres para facturar.");
            return;
        }

        Path archivo = Path.of("factura_" + cliente.getId() + ".txt");
        try {
            FileManager.generarFacturaTxt(cliente, detalles, archivo);
            JOptionPane.showMessageDialog(this, "Factura generada: " + archivo.toAbsolutePath());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al generar factura.");
        }
    }

    private void verFactura() {
        Path archivo = Path.of("factura_" + cliente.getId() + ".txt");
        if (!Files.exists(archivo)) {
            JOptionPane.showMessageDialog(this, "No se ha generado ninguna factura.");
            return;
        }

        try {
            String contenido = Files.readString(archivo);
            JTextArea area = new JTextArea(contenido);
            area.setEditable(false);
            JScrollPane scroll = new JScrollPane(area);
            scroll.setPreferredSize(new Dimension(500, 300));
            JOptionPane.showMessageDialog(this, scroll, "Factura", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al leer la factura.");
        }
    }

    private JButton crearBotonAzul(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("SansSerif", Font.BOLD, 15));
        btn.setBackground(new Color(30, 40, 70));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(160, 40));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(50, 60, 100));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(30, 40, 70));
            }
        });
        return btn;
    }

    private JButton crearBotonVentana(String texto, Color fondo) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(fondo);
        btn.setBorder(null);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static void main(String[] args) {
        Cliente clienteMock = new Cliente(1, "Ana", "López", "ana@email.com", "000000000", "abc123");
        SwingUtilities.invokeLater(() -> new AlquileresView(clienteMock).setVisible(true));
    }
}
