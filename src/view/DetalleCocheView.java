package view;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

import dao.CocheDAO;
import model.Coche;
import utils.Conexion;

public class DetalleCocheView extends JFrame {
    private Coche coche;

    public DetalleCocheView(int idCoche) {
        setTitle("Detalle del Coche");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Cargar el coche desde la base de datos
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

        // Panel con la información
        JPanel panelDatos = new JPanel();
        panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.Y_AXIS));
        panelDatos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Cargar imagen según marca (ej: utils/image/toyota.jpg)
        JLabel lblImagen = new JLabel();
        try {
            String rutaImagen = "/utils/image/" + coche.getMarca().toLowerCase() + ".jpg";
            ImageIcon icon = new ImageIcon(getClass().getResource(rutaImagen));
            lblImagen.setIcon(new ImageIcon(icon.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH)));
        } catch (Exception e) {
            lblImagen.setText("[Imagen no disponible]");
        }
        lblImagen.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDatos.add(lblImagen);

        // Info del coche
        panelDatos.add(Box.createRigidArea(new Dimension(0, 10)));
        panelDatos.add(new JLabel("Marca: " + coche.getMarca()));
        panelDatos.add(new JLabel("Modelo: " + coche.getModelo()));
        panelDatos.add(new JLabel("Año: " + coche.getAnio()));
        panelDatos.add(new JLabel("Cilindrada: " + coche.getCilindrada() + " cc"));
        panelDatos.add(new JLabel("Caballos: " + coche.getCaballos() + " HP"));
        panelDatos.add(new JLabel("Precio por día: $" + coche.getPrecio()));

        // Botones
        JPanel panelBotones = new JPanel();
        JButton btnAlquilar = new JButton("Alquilar");
        JButton btnVolver = new JButton("Volver");

        btnAlquilar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Simulación: alquiler de " + coche.getModelo() + " registrado.");
            dispose();
        });

        btnVolver.addActionListener(e -> dispose());

        panelBotones.add(btnAlquilar);
        panelBotones.add(btnVolver);

        add(panelDatos, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }
}
