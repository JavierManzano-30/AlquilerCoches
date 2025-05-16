package view;

import dao.AlquilerDAO;
import model.Alquiler;
import model.Cliente;
import model.Coche;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class DetalleCocheView extends JFrame {

    private final Coche coche;
    private final Cliente cliente;

    public DetalleCocheView(Coche coche, Cliente cliente) {
        this.coche = coche;
        this.cliente = cliente;

        setTitle("Detalle del Coche");
        setSize(1100, 650);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Barra superior
        JPanel barra = new JPanel(null);
        barra.setBackground(Color.WHITE);
        barra.setPreferredSize(new Dimension(1100, 40));

        JLabel userLabel = new JLabel("👤 " + cliente.getNombre());
        userLabel.setFont(new Font("Monospaced", Font.BOLD, 13));
        userLabel.setBounds(10, 10, 200, 20);
        barra.add(userLabel);

        JButton btnMin = crearBotonVentana("—", new Color(166, 203, 226));
        btnMin.setBounds(1020, 7, 30, 25);
        btnMin.addActionListener(e -> setState(ICONIFIED));
        barra.add(btnMin);

        JButton btnCerrar = crearBotonVentana("X", new Color(230, 105, 120));
        btnCerrar.setBounds(1060, 7, 30, 25);
        btnCerrar.addActionListener(e -> System.exit(0));
        barra.add(btnCerrar);

        add(barra, BorderLayout.NORTH);

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        add(panel, BorderLayout.CENTER);

        // Izquierda (imagen y título)
        JPanel panelIzq = new JPanel();
        panelIzq.setBackground(Color.WHITE);
        panelIzq.setLayout(new BoxLayout(panelIzq, BoxLayout.Y_AXIS));
        panelIzq.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel lblTitulo = new JLabel(coche.getMarca().toUpperCase() + " - " + coche.getModelo().toUpperCase());
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelIzq.add(lblTitulo);
        panelIzq.add(Box.createVerticalStrut(20));

        JLabel lblImagen = new JLabel();
        try {
            String ruta = "/utils/image/detalle" + coche.getMarca().toLowerCase() + "_detalle.jpg";
            ImageIcon icon = new ImageIcon(getClass().getResource(ruta));
            Image scaled = icon.getImage().getScaledInstance(700, 300, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            lblImagen.setText("[Imagen no disponible]");
        }
        lblImagen.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelIzq.add(lblImagen);
        panelIzq.add(Box.createVerticalStrut(20));

        JButton btnImagen = new JButton("VER IMAGEN COMPLETA");
        btnImagen.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnImagen.setFocusPainted(false);
        btnImagen.setBackground(new Color(100, 100, 100));
        btnImagen.setForeground(Color.WHITE);
        btnImagen.addActionListener(ev -> {
            try {
                String ruta = "/utils/image" + coche.getMarca().toLowerCase() + "_detalle.jpg";
                ImageIcon original = new ImageIcon(getClass().getResource(ruta));
                JLabel label = new JLabel(original);
                JFrame viewer = new JFrame("Imagen completa");
                viewer.getContentPane().add(new JScrollPane(label));
                viewer.pack();
                viewer.setLocationRelativeTo(null);
                viewer.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "No se pudo cargar la imagen completa.");
            }
        });
        panelIzq.add(btnImagen);

        // Derecha (detalles)
        JPanel panelDer = new JPanel();
        panelDer.setBackground(Color.WHITE);
        panelDer.setLayout(new BoxLayout(panelDer, BoxLayout.Y_AXIS));
        panelDer.setPreferredSize(new Dimension(300, 500));
        panelDer.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));

        JLabel lblDetalles = new JLabel("Detalles");
        lblDetalles.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblDetalles.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDer.add(lblDetalles);
        panelDer.add(Box.createVerticalStrut(20));

        panelDer.add(etiqueta("CABALLOS", coche.getCaballos() + " CV"));
        panelDer.add(etiqueta("CILINDRADA", coche.getCilindrada() + " CC"));
        panelDer.add(etiqueta("ECO", "Sí")); // Placeholder, reemplazar luego si se añade
        panelDer.add(Box.createVerticalStrut(40));
        panelDer.add(etiqueta("PRECIO/DÍA", String.format("%.0f€", coche.getPrecio()), true));

        // Botones
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Color.WHITE);
        panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 20));

        JButton btnVolver = new JButton("ATRÁS");
        btnVolver.setBackground(new Color(200, 120, 130));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setPreferredSize(new Dimension(120, 40));
        btnVolver.setFocusPainted(false);
        btnVolver.addActionListener(e -> {
            new CochesView(cliente).setVisible(true);
            dispose();
        });

        JButton btnAlquilar = new JButton("ALQUILAR");
        btnAlquilar.setBackground(new Color(110, 180, 230));
        btnAlquilar.setForeground(Color.WHITE);
        btnAlquilar.setPreferredSize(new Dimension(120, 40));
        btnAlquilar.setFocusPainted(false);
        btnAlquilar.addActionListener(e -> alquilarCoche());

        panelBotones.add(btnVolver);
        panelBotones.add(btnAlquilar);

        panelDer.add(Box.createVerticalGlue());
        panelDer.add(panelBotones);

        panel.add(panelIzq, BorderLayout.CENTER);
        panel.add(panelDer, BorderLayout.EAST);
    }

    private JPanel etiqueta(String titulo, String valor) {
        return etiqueta(titulo, valor, false);
    }

    private JPanel etiqueta(String titulo, String valor, boolean grande) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("SansSerif", Font.PLAIN, grande ? 16 : 14));
        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Monospaced", grande ? Font.BOLD : Font.PLAIN, grande ? 22 : 14));
        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(lblValor, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        return panel;
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

    private void alquilarCoche() {
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

        double total = dias * coche.getPrecio();
        Alquiler alquiler = new Alquiler();
        alquiler.setIdCliente(cliente.getId());
        alquiler.setIdCoche(coche.getId());
        alquiler.setFechaInicio(LocalDate.now().toString());
        alquiler.setFechaFin(LocalDate.now().plusDays(dias).toString());
        alquiler.setTotal(total);

        boolean exito = new AlquilerDAO().crearAlquiler(alquiler);
        if (exito) {
            JOptionPane.showMessageDialog(this, "¡Alquiler realizado correctamente!");
            new AlquileresView(cliente).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar el alquiler.");
        }
    }

    public static void main(String[] args) {
        Cliente clienteMock = new Cliente(1, "Mario", "Rossi", "mario@email.com", "123456789", "secreta");
        Coche cocheMock = new Coche(1, "Toyota", "Supra", 2020, 85.0, true, 340, 3000);
        SwingUtilities.invokeLater(() -> new DetalleCocheView(cocheMock, clienteMock).setVisible(true));
    }
}
