package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import model.Cliente;
import model.Coche;
import dao.CocheDAO;
import controller.ConexionBD;

public class CochesView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tableCoches;
    private Cliente cliente;
    private JButton btnAlquilar;

    public CochesView(Cliente cliente) {
        this.cliente = cliente;
        initUI();
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400);
        setLocationRelativeTo(null);

        JPanel fondoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon fondo = new ImageIcon("src/utils/image/CochesImage.jpg");
                Image img = fondo.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        fondoPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        fondoPanel.setLayout(null);

        this.contentPane = fondoPanel;
        setContentPane(contentPane);

        JLabel lblTitulo = new JLabel("Coches Disponibles");
        lblTitulo.setBounds(150, 10, 200, 30);
        contentPane.add(lblTitulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 50, 460, 180);
        contentPane.add(scrollPane);

        tableCoches = new JTable();
        scrollPane.setViewportView(tableCoches);

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.setColumnIdentifiers(new String[] {
            "ID", "Marca", "Modelo", "Año", "Precio por día (€)", "Caballos", "Cilindrada", "Disponible"
        });
        tableCoches.setModel(model);

        try {
            Connection conexion = ConexionBD.conectar();
            CocheDAO dao = new CocheDAO(conexion);
            List<Coche> coches = dao.obtenerTodosLosCoches();

            for (Coche coche : coches) {
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

            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableCoches.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tableCoches.getSelectedRow();
                if (row >= 0) {
                    String disponible = tableCoches.getValueAt(row, 7).toString();
                    btnAlquilar.setEnabled(disponible.equalsIgnoreCase("Sí"));

                    int idCoche = (int) tableCoches.getValueAt(row, 0);
                    new DetalleCocheView(idCoche, cliente, CochesView.this);
                }
            }
        });

        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        tableCoches.setOpaque(false);
        tableCoches.setBackground(new Color(255, 255, 255, 100));
        tableCoches.setForeground(Color.BLACK);
        tableCoches.setGridColor(new Color(255, 255, 255, 60));

        JTableHeader header = tableCoches.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(255, 255, 255, 120));
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Segoe UI", Font.BOLD, 8));

        btnAlquilar = new JButton("Alquilar Coche");
        btnAlquilar.setBounds(180, 250, 130, 25);
        btnAlquilar.setEnabled(false);
        contentPane.add(btnAlquilar);

        btnAlquilar.addActionListener(e -> {
            int fila = tableCoches.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona un coche.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String input = JOptionPane.showInputDialog(this, "¿Cuántos días deseas alquilar el coche?");
            if (input == null || input.trim().isEmpty()) {
                return; // Cancelado
            }

            int dias;
            try {
                dias = Integer.parseInt(input.trim());
                if (dias <= 0) {
                    JOptionPane.showMessageDialog(this, "Debe ingresar un número positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Entrada inválida. Introduce un número.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idCoche = Integer.parseInt(tableCoches.getModel().getValueAt(fila, 0).toString());
            double precio = Double.parseDouble(tableCoches.getModel().getValueAt(fila, 4).toString());
            double total = precio * dias;

            try (Connection conn = ConexionBD.conectar()) {
                String sql = """
                    INSERT INTO alquileres (id_cliente, id_coche, fecha_inicio, dias, total)
                    VALUES (?, ?, CURDATE(), ?, ?)
                """;

                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, cliente.getId());
                stmt.setInt(2, idCoche);
                stmt.setInt(3, dias);
                stmt.setDouble(4, total);

                int result = stmt.executeUpdate();
                if (result > 0) {
                    String sqlUpdate = "UPDATE coches SET disponible = 0 WHERE id = ?";
                    PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate);
                    stmtUpdate.setInt(1, idCoche);
                    stmtUpdate.executeUpdate();

                    tableCoches.setValueAt("No", fila, 7); // columna 7 = Disponible
                    btnAlquilar.setEnabled(false);

                    JOptionPane.showMessageDialog(this, "¡Alquiler registrado correctamente por " + dias + " día(s)!");
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al registrar el alquiler.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnVolver = new JButton("Volver Atrás");
        btnVolver.setBounds(180, 290, 130, 25);
        btnVolver.setBackground(new Color(30, 30, 30));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.setFont(new Font("Tahoma", Font.BOLD, 12));
        contentPane.add(btnVolver);

        btnVolver.addActionListener(e -> {
            new PrincipalView(cliente).setVisible(true);
            dispose();
        });
    }
}