package view;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

import dao.CocheDAO;
import model.Coche;
import model.Cliente;
import utils.Conexion;

public class DetalleCocheView extends JFrame {
    private Coche coche;
    private Cliente cliente;
    private JFrame ventanaAnterior;

    public DetalleCocheView(int idCoche, Cliente cliente, JFrame ventanaAnterior) {
        this.cliente = cliente;
        this.ventanaAnterior = ventanaAnterior;

        setTitle("Detalle del Coche");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        try (Connection conn = Conexion.getConexion()) {
            CocheDAO dao = new CocheDAO(conn);
            this.coche = dao.obtenerCochePorId(idCoche);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos.");
            dispose();
            return;
        }

        if (this.coche == null) {
            JOptionPane.showMessageDialog(this, "No se encontró el coche con ID: " + idCoche);
            dispose();
            return;
        }

        JPanel panelDatos = new JPanel();
        panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.Y_AXIS));
        panelDatos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblImagen = new JLabel();
        try {
            String rutaImagen = "/utils/image/" + coche.getMarca().toLowerCase() + "_detalle.jpg";
            ImageIcon icon = new ImageIcon(getClass().getResource(rutaImagen));
            Image imgEscalada = icon.getImage().getScaledInstance(350, 160, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(imgEscalada));

            lblImagen.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelDatos.add(lblImagen);

            JButton btnVerGrande = new JButton("Ver imagen grande");
            btnVerGrande.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnVerGrande.addActionListener(ev -> {
                try {
                    ImageIcon originalIcon = new ImageIcon(getClass().getResource(rutaImagen));
                    JLabel label = new JLabel(originalIcon);

                    JFrame viewer = new JFrame("Vista de imagen original");
                    viewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    viewer.getContentPane().add(new JScrollPane(label));
                    viewer.pack();
                    viewer.setLocationRelativeTo(null);
                    viewer.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "No se pudo cargar la imagen completa.");
                }
            });
            panelDatos.add(btnVerGrande);

        } catch (Exception e) {
            lblImagen.setText("[Imagen no disponible]");
            panelDatos.add(lblImagen);
        }

        panelDatos.add(Box.createRigidArea(new Dimension(0, 10)));
        panelDatos.add(new JLabel("Marca: " + coche.getMarca()));
        panelDatos.add(new JLabel("Modelo: " + coche.getModelo()));
        panelDatos.add(new JLabel("Año: " + coche.getAnio()));
        panelDatos.add(new JLabel("Cilindrada: " + coche.getCilindrada() + " cc"));
        panelDatos.add(new JLabel("Caballos: " + coche.getCaballos() + " HP"));
        panelDatos.add(new JLabel("Precio por día: $" + coche.getPrecio()));

        JPanel panelBotones = new JPanel();
        JButton btnAlquilar = new JButton("Alquilar");
        JButton btnVolver = new JButton("Volver");

        btnAlquilar.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "¿Cuántos días deseas alquilar el coche?");
            if (input == null || input.trim().isEmpty()) return;

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

            double total = coche.getPrecio() * dias;

            try (Connection conn = Conexion.getConexion()) {
                String sqlAlquiler = """
                    INSERT INTO alquileres (id_cliente, id_coche, fecha_inicio, dias, total)
                    VALUES (?, ?, CURDATE(), ?, ?)
                """;
                PreparedStatement stmt = conn.prepareStatement(sqlAlquiler);
                stmt.setInt(1, cliente.getId());
                stmt.setInt(2, coche.getId());
                stmt.setInt(3, dias);
                stmt.setDouble(4, total);
                stmt.executeUpdate();

                String sqlUpdate = "UPDATE coches SET disponible = 0 WHERE id = ?";
                PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate);
                stmtUpdate.setInt(1, coche.getId());
                stmtUpdate.executeUpdate();

                JOptionPane.showMessageDialog(this, "¡Alquiler realizado por " + dias + " día(s)!");
                new AlquileresView(cliente).setVisible(true);
                dispose();
                ventanaAnterior.dispose();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al registrar el alquiler.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnVolver.addActionListener(e -> dispose());

        panelBotones.add(btnAlquilar);
        panelBotones.add(btnVolver);

        add(panelDatos, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }
}