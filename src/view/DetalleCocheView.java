package view;

import dao.AlquilerDAO;
import dao.CocheDAO;
import model.Alquiler;
import model.Cliente;
import model.Coche;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class DetalleCocheView extends JFrame {

    private final Cliente cliente;
    private final Coche coche;

    public DetalleCocheView(Coche coche, Cliente cliente) {
        this.coche = coche;
        this.cliente = cliente;

        setTitle("Detalle del Coche");
        setSize(900, 500);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel background = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon(getClass().getResource("/utils/image/fondo_detalle.jpg"));
                if (icon != null) {
                    g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setColor(new Color(0, 0, 0, 160));
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                    g2d.dispose();
                }
            }
        };
        background.setLayout(null);
        setContentPane(background);

        // Barra superior
        JPanel topBar = new JPanel(null);
        topBar.setBounds(0, 0, 900, 40);
        topBar.setBackground(Color.WHITE);
        background.add(topBar);

        JLabel lblUsuario = new JLabel("👤 " + cliente.getNombre());
        lblUsuario.setFont(new Font("Monospaced", Font.BOLD, 13));
        lblUsuario.setBounds(10, 10, 200, 20);
        topBar.add(lblUsuario);

        JButton btnMin = crearBotonVentana("—", new Color(166, 203, 226));
        btnMin.setBounds(820, 7, 30, 25);
        btnMin.addActionListener(e -> setState(ICONIFIED));
        topBar.add(btnMin);

        JButton btnCerrar = crearBotonVentana("X", new Color(230, 105, 120));
        btnCerrar.setBounds(860, 7, 30, 25);
        btnCerrar.addActionListener(e -> System.exit(0));
        topBar.add(btnCerrar);

        // Título
        JLabel lblTitulo = new JLabel(coche.getModelo().toUpperCase(), SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(new Color(120, 120, 200));
        lblTitulo.setBounds(330, 60, 300, 45);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        background.add(lblTitulo);

        // Imagen del coche
        JLabel lblImagen = new JLabel();
        lblImagen.setBounds(60, 120, 320, 200);
        lblImagen.setOpaque(true);
        lblImagen.setBackground(new Color(130, 130, 200));
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen.setText("imagen del coche");
        background.add(lblImagen);

        // Info del coche
        int y = 120;
        int spacing = 50;
        background.add(crearEtiqueta("Marca: " + coche.getMarca(), 430, y));
        background.add(crearEtiqueta("Caballos: " + coche.getCaballos(), 430, y += spacing));
        background.add(crearEtiqueta("Cilindrada: " + coche.getCilindrada() + "cc", 430, y += spacing));
        background.add(crearEtiqueta("Precio/día: " + coche.getPrecio() + "€", 430, y += spacing));

        // Botones
        JButton btnVolver = crearBoton("Volver");
        btnVolver.setBounds(180, 380, 130, 40);
        btnVolver.addActionListener(e -> {
            new CochesView(cliente).setVisible(true);
            dispose();
        });

        JButton btnAlquilar = crearBoton("Alquilar");
        btnAlquilar.setBounds(600, 380, 130, 40);
        btnAlquilar.addActionListener(e -> alquilarCoche());

        background.add(btnVolver);
        background.add(btnAlquilar);
    }

    private JLabel crearEtiqueta(String texto, int x, int y) {
        JLabel label = new JLabel(texto + "  ➝");
        label.setBounds(x, y, 400, 40);
        label.setForeground(Color.WHITE);
        label.setBackground(new Color(130, 130, 200));
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        label.setOpaque(true);
        label.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        return label;
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(new Color(130, 130, 200));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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

    private void alquilarCoche() {
        String input = JOptionPane.showInputDialog(this, "¿Cuántos días deseas alquilar el coche?");
        if (input == null || input.trim().isEmpty()) return;

        try {
            int dias = Integer.parseInt(input.trim());
            if (dias <= 0) throw new NumberFormatException();

            double total = dias * coche.getPrecio();
            LocalDate inicio = LocalDate.now();
            LocalDate fin = inicio.plusDays(dias);

            Alquiler alquiler = new Alquiler();
            alquiler.setIdCliente(cliente.getId());
            alquiler.setIdCoche(coche.getId());
            alquiler.setFechaInicio(inicio.toString());
            alquiler.setFechaFin(fin.toString());
            alquiler.setTotal(total);

            boolean ok = new AlquilerDAO().crearAlquiler(alquiler);
            if (ok) {
                new CocheDAO().marcarComoNoDisponible(coche.getId());
                JOptionPane.showMessageDialog(this, "Alquiler realizado correctamente.");
                new CochesView(cliente).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar alquiler.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Número de días inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        Cliente cliente = new Cliente(1, "Mario", "Rossi", "mario@email.com", "00000000", "secreta");
        Coche coche = new Coche(3, "Toyota", "Supra", 2021, 85.0, true, 340, 3000);
        SwingUtilities.invokeLater(() -> new DetalleCocheView(coche, cliente).setVisible(true));
    }
}
