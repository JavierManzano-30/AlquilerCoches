package view;

import dao.AlquilerDAO;
import dao.CocheDAO;
import model.Alquiler;
import model.Coche;
import model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.toedter.calendar.JDateChooser;
import java.util.Date;
import java.util.HashSet;
import java.time.ZoneId;

public class CochesView extends JFrame {

    private final Cliente cliente;
    private JTable tabla;
    private DefaultTableModel modelo;
    private Image fondoEscalado;

    public CochesView(Cliente cliente) {
        this.cliente = cliente;

        setTitle("Coches Disponibles");
        setSize(1000, 600);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Fondo optimizado
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (fondoEscalado == null) {
                    try {
                        ImageIcon icon = new ImageIcon(getClass().getResource("/utils/image/fondo_alquiler.jpg"));
                        fondoEscalado = icon.getImage().getScaledInstance(1000, 600, Image.SCALE_SMOOTH);
                    } catch (Exception e) {
                        System.err.println("Error al cargar fondo: " + e.getMessage());
                    }
                }
                if (fondoEscalado != null) {
                    g.drawImage(fondoEscalado, 0, 0, this);
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setColor(new Color(0, 0, 0, 150));
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                    g2d.dispose();
                }
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);

        // Top bar
        JPanel topBar = new JPanel(null);
        topBar.setPreferredSize(new Dimension(1000, 40));
        topBar.setBackground(Color.WHITE);
        backgroundPanel.add(topBar, BorderLayout.NORTH);

        JLabel lblUsuario = new JLabel("👤 " + cliente.getNombre());
        lblUsuario.setFont(new Font("Monospaced", Font.BOLD, 13));
        lblUsuario.setBounds(10, 10, 200, 20);
        topBar.add(lblUsuario);

        JButton btnMin = crearBotonVentana("—", new Color(166, 203, 226));
        btnMin.setBounds(920, 7, 30, 25);
        btnMin.addActionListener(e -> setState(ICONIFIED));
        topBar.add(btnMin);

        JButton btnCerrar = crearBotonVentana("X", new Color(230, 105, 120));
        btnCerrar.setBounds(960, 7, 30, 25);
        btnCerrar.addActionListener(e -> System.exit(0));
        topBar.add(btnCerrar);

        // Centro
        JPanel panelCentro = new JPanel();
        panelCentro.setOpaque(false);
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        backgroundPanel.add(panelCentro, BorderLayout.CENTER);

        JLabel lblTitulo = new JLabel("Coches Disponibles");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panelCentro.add(lblTitulo);

        modelo = new DefaultTableModel(new String[]{"ID", "Marca", "Modelo", "Año", "Precio/día", "Caballos", "Disponible"}, 0);
        tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setMaximumSize(new Dimension(900, 200));
        panelCentro.add(scroll);

        cargarCoches();

        // Botones
        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JButton btnVolver = crearBotonAzul("Volver");
        btnVolver.addActionListener(e -> {
            new PrincipalView(cliente).setVisible(true);
            dispose();
        });

        JButton btnAlquilar = crearBotonAzul("Alquilar Coche");
        btnAlquilar.addActionListener(e -> alquilarCoche());

        JButton btnDetalle = crearBotonAzul("Ver Detalle");
        btnDetalle.addActionListener(e -> verDetalle());

        panelBotones.add(btnVolver);
        panelBotones.add(Box.createHorizontalStrut(20));
        panelBotones.add(btnAlquilar);
        panelBotones.add(Box.createHorizontalStrut(20));
        panelBotones.add(btnDetalle);

        panelCentro.add(panelBotones);
    }

    private void cargarCoches() {
        modelo.setRowCount(0);
        CocheDAO dao = new CocheDAO();
        List<Coche> coches = dao.listarCoches();

        for (Coche c : coches) {
            if (c.isDisponible()) {
                modelo.addRow(new Object[]{
                        c.getId(), c.getMarca(), c.getModelo(), c.getAnio(), c.getPrecio(), c.getCaballos(), "Sí"
                });
            }
        }
    }

    private void alquilarCoche() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un coche para alquilar", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCoche = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
        double precio = Double.parseDouble(modelo.getValueAt(fila, 4).toString());

        // Obtener fechas bloqueadas
        AlquilerDAO alquilerDAO = new AlquilerDAO();
        List<Alquiler> alquileresCoche = alquilerDAO.obtenerAlquileresCoche(idCoche);
        Set<LocalDate> fechasBloqueadas = calcularFechasBloqueadas(alquileresCoche);

        // Crear JDateChoosers
        JDateChooser fechaInicioPicker = new JDateChooser();
        JDateChooser fechaFinPicker = new JDateChooser();
        fechaInicioPicker.setDateFormatString("yyyy-MM-dd");
        fechaFinPicker.setDateFormatString("yyyy-MM-dd");

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Fecha inicio:"));
        panel.add(fechaInicioPicker);
        panel.add(new JLabel("Fecha fin:"));
        panel.add(fechaFinPicker);

        int opcion = JOptionPane.showConfirmDialog(this, panel, "Selecciona fechas de alquiler", JOptionPane.OK_CANCEL_OPTION);
        if (opcion != JOptionPane.OK_OPTION) return;

        try {
            LocalDate inicio = fechaInicioPicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate fin = fechaFinPicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (inicio.isAfter(fin)) {
                JOptionPane.showMessageDialog(this, "La fecha de fin no puede ser anterior a la de inicio.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Comprobar conflicto con fechas bloqueadas
            LocalDate tmp = inicio;
            while (!tmp.isAfter(fin)) {
                if (fechasBloqueadas.contains(tmp)) {
                    JOptionPane.showMessageDialog(this, "El coche no está disponible en las fechas seleccionadas.", "Ocupado", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                tmp = tmp.plusDays(1);
            }

            long dias = java.time.temporal.ChronoUnit.DAYS.between(inicio, fin) + 1;
            double total = dias * precio;

            Alquiler alquiler = new Alquiler(cliente.getId(), idCoche, inicio, fin, total);
            boolean ok = alquilerDAO.crearAlquiler(alquiler);

            if (ok) {
                new CocheDAO().marcarComoNoDisponible(idCoche); // opcional, si quieres
                JOptionPane.showMessageDialog(this, "Coche alquilado correctamente.");
                cargarCoches();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar alquiler.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al interpretar las fechas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verDetalle() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un coche para ver el detalle", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCoche = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
        Coche coche = new CocheDAO().listarCoches().stream()
                .filter(c -> c.getId() == idCoche)
                .findFirst().orElse(null);

        if (coche != null) {
            new DetalleCocheView(coche, cliente).setVisible(true);
            dispose();
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
    
    private Set<LocalDate> calcularFechasBloqueadas(List<Alquiler> alquileres) {
        Set<LocalDate> fechas = new HashSet<>();
        for (Alquiler a : alquileres) {
            LocalDate inicio = a.getFechaInicio();
            LocalDate fin = a.getFechaFin();

            // Añadir días del alquiler
            while (!inicio.isAfter(fin)) {
                fechas.add(inicio);
                inicio = inicio.plusDays(1);
            }

            // Añadir día de limpieza
            LocalDate limpieza = fin.plusDays(1);
            fechas.add(limpieza);

            // Si limpieza o siguiente cae en fin de semana, bloquear hasta el lunes
            while (limpieza.getDayOfWeek().getValue() >= 6) {
                limpieza = limpieza.plusDays(1);
                fechas.add(limpieza);
            }
        }
        return fechas;
    }

}
