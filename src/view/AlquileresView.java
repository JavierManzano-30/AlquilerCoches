package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import controller.ConexionBD;
import dao.AlquilerDAO;
import model.Cliente;
import utils.FileManager;
import model.DetalleAlquiler;
import view.PrincipalView;

import java.util.List;

public class AlquileresView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tableAlquileres;
    private Cliente cliente;

    public AlquileresView(Cliente cliente) {
        this.cliente = cliente;
        initUI();
        cargarAlquileresCliente();
    }

    private void initUI() {
        setTitle("Mis Alquileres");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400);
        setLocationRelativeTo(null);

        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon fondo = new ImageIcon("src/utils/image/AlquileresImage.jpg");
                Image img = fondo.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        contentPane.setLayout(null);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel lblTitulo = new JLabel("Alquileres de " + cliente.getNombre());
        lblTitulo.setBounds(150, 10, 250, 30);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        contentPane.add(lblTitulo);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 50, 420, 150);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        contentPane.add(scrollPane);

        tableAlquileres = new JTable();
        tableAlquileres.setOpaque(false);
        tableAlquileres.setBackground(new Color(255, 255, 255, 100));
        tableAlquileres.setForeground(Color.BLACK);
        tableAlquileres.setShowGrid(false);
        tableAlquileres.setFillsViewportHeight(true);

        JTableHeader header = tableAlquileres.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(255, 255, 255, 180));
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Tahoma", Font.BOLD, 12));
        scrollPane.setViewportView(tableAlquileres);

        JButton btnVolver = new JButton("Volver Atrás");
        btnVolver.setBounds(30, 215, 140, 25);
        btnVolver.setBackground(new Color(30, 30, 30));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.setFont(new Font("Tahoma", Font.BOLD, 12));
        contentPane.add(btnVolver);

        btnVolver.addActionListener(e -> {
            new PrincipalView(cliente).setVisible(true);
            dispose();
        });

        JButton btnCancelar = new JButton("Cancelar alquiler");
        btnCancelar.setBounds(180, 215, 140, 25);
        btnCancelar.setBackground(new Color(150, 0, 0));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 12));
        contentPane.add(btnCancelar);

        btnCancelar.addActionListener(e -> {
            int fila = tableAlquileres.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un alquiler para cancelar.");
                return;
            }

            int idAlquiler = Integer.parseInt(tableAlquileres.getModel().getValueAt(fila, 0).toString());
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de cancelar el alquiler #" + idAlquiler + "?",
                "Confirmar cancelación", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try (Connection conn = ConexionBD.conectar()) {
                    String sql = "DELETE FROM alquileres WHERE id = ?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1, idAlquiler);
                    int rows = ps.executeUpdate();

                    if (rows > 0) {
                        JOptionPane.showMessageDialog(this, "Alquiler cancelado correctamente.");
                        cargarAlquileresCliente();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error al cancelar alquiler.");
                }
            }
        });

        JButton btnFactura = new JButton("Generar Factura");
        btnFactura.setBounds(30, 255, 140, 25);
        btnFactura.setBackground(new Color(0, 120, 0));
        btnFactura.setForeground(Color.WHITE);
        btnFactura.setFocusPainted(false);
        btnFactura.setFont(new Font("Tahoma", Font.BOLD, 12));
        contentPane.add(btnFactura);

        btnFactura.addActionListener(e -> {
            List<DetalleAlquiler> detalles = AlquilerDAO.obtener(cliente.getId());
            if (detalles.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El cliente no tiene alquileres.");
                return;
            }
            try {
                Path txt = Path.of("factura_" + cliente.getId() + ".txt");
                FileManager.generarFacturaTxt(cliente, detalles, txt);
                JOptionPane.showMessageDialog(this, "Factura TXT generada en: \n" + txt.toAbsolutePath());
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al generar factura.");
            }
        });

        JButton btnLeerFactura = new JButton("Ver Factura TXT");
        btnLeerFactura.setBounds(180, 255, 140, 25);
        btnLeerFactura.setBackground(new Color(0, 80, 160));
        btnLeerFactura.setForeground(Color.WHITE);
        btnLeerFactura.setFocusPainted(false);
        btnLeerFactura.setFont(new Font("Tahoma", Font.BOLD, 12));
        contentPane.add(btnLeerFactura);

        btnLeerFactura.addActionListener(e -> {
            Path txt = Path.of("factura_" + cliente.getId() + ".txt");
            if (!Files.exists(txt)) {
                JOptionPane.showMessageDialog(this, "No se encontró ninguna factura.");
                return;
            }
            try {
                String contenido = Files.readString(txt);
                JTextArea area = new JTextArea(contenido);
                area.setEditable(false);
                JScrollPane scroll = new JScrollPane(area);
                scroll.setPreferredSize(new java.awt.Dimension(400, 300));
                JOptionPane.showMessageDialog(this, scroll, "Factura", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al leer factura.");
            }
        });
    }

    private void cargarAlquileresCliente() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[] {
            "ID", "Marca", "Modelo", "Fecha Alquiler", "Días", "Total (€)"
        });

        try (Connection conn = ConexionBD.conectar()) {
            String sql = """
                SELECT a.id, ma.nombre AS marca, mo.nombre AS modelo, 
                       a.fecha_inicio, a.dias, a.total
                FROM alquileres a
                JOIN coches c ON a.id_coche = c.id
                JOIN modelo mo ON c.id_modelo = mo.id
                JOIN marca ma ON mo.id_marca = ma.id
                WHERE a.id_cliente = ?
            """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, cliente.getId());

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[] {
                    rs.getInt("id"),
                    rs.getString("marca"),
                    rs.getString("modelo"),
                    rs.getDate("fecha_inicio"),
                    rs.getInt("dias"),
                    rs.getDouble("total")
                });
            }

            tableAlquileres.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
