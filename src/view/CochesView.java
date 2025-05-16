package view;

import model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class CochesView extends JFrame {

    private Cliente cliente;

    public CochesView(Cliente cliente) {
        this.cliente = cliente;
        setTitle("Alquiler de Coches");
        setSize(1000, 500);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        setContentPane(contenedor);

        // Panel fondo
        JPanel panelFondo = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                URL imageUrl = getClass().getResource("/utils/image/fondo_alquiler.jpg");
                if (imageUrl == null) {
                    System.err.println("⚠ Error: No se encontró la imagen fondo_alquiler.jpg en /utils/image/");
                    return;
                }
                ImageIcon icon = new ImageIcon(imageUrl);
                Image img = icon.getImage();

                int panelW = getWidth();
                int panelH = getHeight();

                double scale = (double) panelH / img.getHeight(null);
                int scaledW = (int) (img.getWidth(null) * scale);
                int x = panelW - scaledW;

                g.drawImage(img, x, 0, scaledW, panelH, this);

                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(0, 0, 0, 150));
                g2d.fillRect(0, 0, panelW, panelH);
                g2d.dispose();
            }
        };
        contenedor.add(panelFondo, BorderLayout.CENTER);
        panelFondo.setLayout(null);

        // Barra superior
        JPanel topBar = new JPanel(null);
        topBar.setBackground(Color.WHITE);
        topBar.setBounds(0, 0, 1000, 40);

        JLabel user = new JLabel("👤 " + cliente.getNombre());
        user.setFont(new Font("Monospaced", Font.BOLD, 13));
        user.setBounds(10, 10, 180, 20);
        topBar.add(user);

        JButton btnMin = crearBotonVentana("—", new Color(166, 203, 226));
        btnMin.setBounds(920, 8, 30, 25);
        btnMin.addActionListener(e -> setState(ICONIFIED));
        topBar.add(btnMin);

        JButton btnCerrar = crearBotonVentana("X", new Color(230, 105, 120));
        btnCerrar.setBounds(960, 8, 30, 25);
        btnCerrar.addActionListener(e -> System.exit(0));
        topBar.add(btnCerrar);

        panelFondo.add(topBar);

        JLabel lblTitulo = new JLabel("Alquiler de Coches");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(30, 50, 400, 40);
        panelFondo.add(lblTitulo);

        JLabel lblSub = new JLabel("Coches Disponibles");
        lblSub.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblSub.setForeground(Color.WHITE);
        lblSub.setBounds(370, 90, 300, 40);
        panelFondo.add(lblSub);

        // Tabla
        JTable tabla = new JTable();
        tabla.setModel(new DefaultTableModel(
            new Object[][] {
                {1, "Toyota", "Corolla", 2020, 40.0, 132, "Sí"},
                {2, "Mazda", "RX-7", 1999, 70.0, 276, "Sí"},
                {3, "Nissan", "Skyline", 2002, 85.0, 280, "Sí"},
            },
            new String[] {"ID", "Marca", "Modelo", "Año", "Precio/día", "Caballos", "Disponible"}
        ));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(100, 140, 800, 150);
        panelFondo.add(scroll);

        // Botones
        JButton btnVolver = crearBoton("Volver");
        btnVolver.setBounds(100, 320, 150, 40);
        btnVolver.addActionListener(e -> {
            new PrincipalView(cliente).setVisible(true);
            dispose();
        });
        panelFondo.add(btnVolver);

        JButton btnAlquilar = crearBoton("Alquilar Coche");
        btnAlquilar.setBounds(330, 320, 200, 40);
        panelFondo.add(btnAlquilar);

        JButton btnDetalle = crearBoton("Ver Detalle");
        btnDetalle.setBounds(600, 320, 200, 40);
        panelFondo.add(btnDetalle);
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(new Color(30, 40, 60));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 15));
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(50, 60, 80));
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(30, 40, 60));
            }
        });

        return btn;
    }

    private JButton crearBotonVentana(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setBorder(null);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static void main(String[] args) {
        Cliente clienteMock = new Cliente(1, "Mario", "Rossi", "mario@email.com", "123456789", "secreta");
        SwingUtilities.invokeLater(() -> new CochesView(clienteMock).setVisible(true));
    }
}
