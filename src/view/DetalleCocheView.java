package view;

import com.toedter.calendar.JDateChooser;
import dao.AlquilerDAO;
import model.Alquiler;
import model.Cliente;
import model.Coche;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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
            String ruta = "/utils/image/detalle/" + coche.getMarca().toLowerCase() + "_detalle.jpg";
            ImageIcon icon = new ImageIcon(getClass().getResource(ruta));
            Image scaled = icon.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            lblImagen.setText("[Imagen no disponible]");
        }
        lblImagen.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelIzq.add(lblImagen);
        panelIzq.add(Box.createVerticalStrut(20));

        panel.add(panelIzq, BorderLayout.CENTER);

        // Derecha (detalles)
        JPanel panelDer = new JPanel();
        panelDer.setBackground(Color.WHITE);
        panelDer.setLayout(new BoxLayout(panelDer, BoxLayout.Y_AXIS));
        panelDer.setPreferredSize(new Dimension(300, 500));
        panelDer.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));

        panelDer.add(etiqueta("CABALLOS", coche.getCaballos() + " CV"));
        panelDer.add(etiqueta("CILINDRADA", coche.getCilindrada() + " CC"));
        panelDer.add(etiqueta("TRANSMISIÓN", coche.getTransmision()));
        panelDer.add(Box.createVerticalStrut(40));
        panelDer.add(etiqueta("PRECIO/DÍA", String.format("%.0f€", coche.getPrecio()), true));

        panelDer.add(Box.createVerticalStrut(30));

        // Calendarios
        JLabel lblInicio = new JLabel("Fecha inicio:");
        JDateChooser chooserInicio = new JDateChooser();
        chooserInicio.setDate(new Date());

        JLabel lblFin = new JLabel("Fecha fin:");
        JDateChooser chooserFin = new JDateChooser();

        lblInicio.setAlignmentX(Component.LEFT_ALIGNMENT);
        chooserInicio.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblFin.setAlignmentX(Component.LEFT_ALIGNMENT);
        chooserFin.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelDer.add(lblInicio);
        panelDer.add(chooserInicio);
        panelDer.add(Box.createVerticalStrut(10));
        panelDer.add(lblFin);
        panelDer.add(chooserFin);

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
        btnAlquilar.addActionListener(e -> {
            Date fechaInicioDate = chooserInicio.getDate();
            Date fechaFinDate = chooserFin.getDate();

            if (fechaInicioDate == null || fechaFinDate == null) {
                JOptionPane.showMessageDialog(this, "Debes seleccionar ambas fechas.");
                return;
            }

            LocalDate inicio = fechaInicioDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate fin = fechaFinDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (!fin.isAfter(inicio)) {
                JOptionPane.showMessageDialog(this, "La fecha de fin debe ser posterior a la de inicio.");
                return;
            }

            int dias = (int) (fin.toEpochDay() - inicio.toEpochDay());
            double total = dias * coche.getPrecio();

            Alquiler alquiler = new Alquiler(cliente.getId(), coche.getId(), inicio, fin, total);

            if (new AlquilerDAO().crearAlquiler(alquiler)) {
                JOptionPane.showMessageDialog(this, "¡Alquiler registrado!");
                new AlquileresView(cliente).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar el alquiler.");
            }
        });

        panelBotones.add(btnVolver);
        panelBotones.add(btnAlquilar);

        panelDer.add(Box.createVerticalGlue());
        panelDer.add(panelBotones);

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
}
