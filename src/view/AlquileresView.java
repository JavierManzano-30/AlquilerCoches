package view;

import dao.AlquilerDAO;
import model.Cliente;
import model.DetalleAlquiler;
import utils.FileManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Vista para que el cliente visualice sus alquileres y genere factura.
 */
public class AlquileresView extends BaseView {

    private JTable tableAlquileres;
    private Cliente cliente;
    private List<DetalleAlquiler> detalles;

    public AlquileresView(Cliente cliente) {
        super("Mis Alquileres", 600, 400);
        this.cliente = cliente;
    }

    @Override
    protected void inicializarComponentes() {
        panelPrincipal.setLayout(null);

        JLabel lblTitulo = new JLabel("Alquileres de " + cliente.getNombre());
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setBounds(180, 10, 300, 30);
        panelPrincipal.add(lblTitulo);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 50, 530, 200);
        panelPrincipal.add(scrollPane);

        tableAlquileres = new JTable();
        scrollPane.setViewportView(tableAlquileres);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(30, 270, 100, 25);
        panelPrincipal.add(btnVolver);
        btnVolver.addActionListener(e -> {
            new PrincipalView(cliente).setVisible(true);
            dispose();
        });

        JButton btnFactura = new JButton("Generar Factura");
        btnFactura.setBounds(150, 270, 150, 25);
        panelPrincipal.add(btnFactura);
        btnFactura.addActionListener(e -> generarFactura());

        JButton btnVerFactura = new JButton("Ver Factura TXT");
        btnVerFactura.setBounds(320, 270, 150, 25);
        panelPrincipal.add(btnVerFactura);
        btnVerFactura.addActionListener(e -> verFactura());

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

    public static void main(String[] args) {
        Cliente clienteMock = new Cliente(1, "Ana", "López", "ana@email.com", "000000000", "abc123");
        SwingUtilities.invokeLater(() -> new AlquileresView(clienteMock).mostrar());
    }
}
