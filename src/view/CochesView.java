package view;

import dao.AlquilerDAO;
import dao.CocheDAO;
import model.Cliente;
import model.Coche;
import model.Alquiler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

/**
 * Vista para que el cliente consulte y alquile coches disponibles.
 */
public class CochesView extends BaseView {

    private JTable tableCoches;
    private JButton btnAlquilar;
    private Cliente cliente;
    private List<Coche> listaCoches;

    public CochesView(Cliente cliente) {
        super("Alquiler de Coches", 600, 400);
        this.cliente = cliente;
    }

    @Override
    protected void inicializarComponentes() {
        panelPrincipal.setLayout(null);

        JLabel lblTitulo = new JLabel("Coches Disponibles");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setBounds(200, 10, 250, 25);
        panelPrincipal.add(lblTitulo);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 50, 550, 200);
        panelPrincipal.add(scrollPane);

        tableCoches = new JTable();
        scrollPane.setViewportView(tableCoches);

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.setColumnIdentifiers(new String[] {
            "ID", "Marca", "Modelo", "Año", "Precio/día", "Caballos", "Cilindrada", "Disponible"
        });

        tableCoches.setModel(model);
        cargarCoches(model);

        tableCoches.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tableCoches.getSelectedRow();
                if (row >= 0) {
                    String disponible = tableCoches.getValueAt(row, 7).toString();
                    btnAlquilar.setEnabled(disponible.equalsIgnoreCase("Sí"));
                }
            }
        });

        JTableHeader header = tableCoches.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        btnAlquilar = new JButton("Alquilar Coche");
        btnAlquilar.setBounds(220, 270, 150, 30);
        btnAlquilar.setEnabled(false);
        panelPrincipal.add(btnAlquilar);
        btnAlquilar.addActionListener(e -> alquilarCoche());

        JButton btnDetalle = new JButton("Ver Detalle");
        btnDetalle.setBounds(390, 270, 150, 30);
        panelPrincipal.add(btnDetalle);
        btnDetalle.addActionListener(e -> mostrarDetalle());

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(20, 320, 100, 25);
        panelPrincipal.add(btnVolver);
        btnVolver.addActionListener(e -> {
            new PrincipalView(cliente).setVisible(true);
            dispose();
        });
    }

    private void cargarCoches(DefaultTableModel model) {
        CocheDAO dao = new CocheDAO();
        listaCoches = dao.listarCoches();

        for (Coche coche : listaCoches) {
            model.addRow(new Object[] {
                coche.getId(),
                coche.getMarca(),
                coche.getModelo(),
                coche.getAnio(),
                coche.getPrecio(),
                coche.getCaballos(),
                coche.getCilindrada(),
                coche.isDisponible() ? "Sí" : "No"
            });
        }
    }

    private void alquilarCoche() {
        int fila = tableCoches.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un coche.");
            return;
        }

        String disponible = tableCoches.getValueAt(fila, 7).toString();
        if (!disponible.equalsIgnoreCase("Sí")) {
            JOptionPane.showMessageDialog(this, "Este coche no está disponible.");
            return;
        }

        String input = JOptionPane.showInputDialog(this, "¿Cuántos días deseas alquilar el coche?");
        if (input == null || input.trim().isEmpty()) return;

        int dias;
        try {
            dias = Integer.parseInt(input.trim());
            if (dias <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Número de días inválido.");
            return;
        }

        int idCoche = (int) tableCoches.getValueAt(fila, 0);
        double precio = Double.parseDouble(tableCoches.getValueAt(fila, 4).toString());
        double total = precio * dias;

        Alquiler alquiler = new Alquiler();
        alquiler.setIdCliente(cliente.getId());
        alquiler.setIdCoche(idCoche);
        alquiler.setFechaInicio(java.time.LocalDate.now().toString());
        alquiler.setFechaFin(java.time.LocalDate.now().plusDays(dias).toString());
        alquiler.setTotal(total);

        AlquilerDAO alquilerDAO = new AlquilerDAO();
        boolean exito = alquilerDAO.crearAlquiler(alquiler);

        if (exito) {
            JOptionPane.showMessageDialog(this, "¡Alquiler registrado correctamente!");
            tableCoches.setValueAt("No", fila, 7);
            btnAlquilar.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar el alquiler.");
        }
    }

    private void mostrarDetalle() {
        int fila = tableCoches.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un coche para ver los detalles.");
            return;
        }

        Coche coche = new Coche(
            (int) tableCoches.getValueAt(fila, 0),
            tableCoches.getValueAt(fila, 1).toString(),
            tableCoches.getValueAt(fila, 2).toString(),
            Integer.parseInt(tableCoches.getValueAt(fila, 3).toString()),
            Double.parseDouble(tableCoches.getValueAt(fila, 4).toString()),
            tableCoches.getValueAt(fila, 7).toString().equalsIgnoreCase("Sí"),
            Integer.parseInt(tableCoches.getValueAt(fila, 5).toString()),
            Integer.parseInt(tableCoches.getValueAt(fila, 6).toString())
        );

        JFrame popup = new JFrame("Detalle del coche");
        popup.setSize(500, 450);
        popup.setLocationRelativeTo(this);
        popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DetalleCocheView detalle = new DetalleCocheView(coche, cliente);
        detalle.inicializarComponentes();
        popup.setContentPane(detalle.panelPrincipal);
        popup.setVisible(true);
    }
}
