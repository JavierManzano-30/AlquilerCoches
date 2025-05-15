package view;

import model.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class PrincipalView extends JFrame {

    public PrincipalView(Cliente cliente) {
        setTitle("RentJDMCars - Principal");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        setContentPane(contenedor);

        // Fondo con imagen atenuada
        JPanel panelFondo = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    URL location = getClass().getResource("/utils/image/fondo_principal.jpg");
                    if (location != null) {
                        ImageIcon fondo = new ImageIcon(location);
                        g.drawImage(fondo.getImage(), 0, 0, getWidth(), getHeight(), this);
                        g.setColor(new Color(0, 0, 0, 150)); // Atenuar fondo
                        g.fillRect(0, 0, getWidth(), getHeight());
                    } else {
                        g.setColor(Color.DARK_GRAY);
                        g.fillRect(0, 0, getWidth(), getHeight());
                    }
                } catch (Exception e) {
                    g.setColor(Color.BLACK);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        contenedor.add(panelFondo, BorderLayout.CENTER);

        // Controles ventana
        JButton btnMin = crearControlVentana("—", new Color(127, 140, 141));
        btnMin.setBounds(920, 10, 30, 25);
        btnMin.addActionListener(e -> setState(Frame.ICONIFIED));
        panelFondo.add(btnMin);

        JButton btnClose = crearControlVentana("X", new Color(192, 57, 43));
        btnClose.setBounds(960, 10, 30, 25);
        btnClose.addActionListener(e -> System.exit(0));
        panelFondo.add(btnClose);

        // Etiqueta usuario arriba derecha
        JLabel lblUsuario = new JLabel("  " + cliente.getNombre());
        lblUsuario.setBounds(720, 10, 190, 25);
        lblUsuario.setOpaque(true);
        lblUsuario.setBackground(new Color(255, 255, 255, 180));
        lblUsuario.setFont(new Font("Monospaced", Font.BOLD, 13));
        lblUsuario.setIcon(new ImageIcon(getClass().getResource("/utils/image/user_icon.jpg")));
        panelFondo.add(lblUsuario);

        // Título
        JLabel lblTitulo = new JLabel("RENTJDMCAR");
        lblTitulo.setBounds(380, 40, 400, 40);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 32));
        lblTitulo.setForeground(Color.WHITE);
        panelFondo.add(lblTitulo);

        JLabel lblBienvenido = new JLabel("Bienvenido " + cliente.getNombre());
        lblBienvenido.setBounds(420, 85, 300, 30);
        lblBienvenido.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblBienvenido.setForeground(Color.WHITE);
        panelFondo.add(lblBienvenido);

        // Botones principales
        JButton btnPerfil = crearBoton("MI PERFIL");
        btnPerfil.setBounds(200, 180, 160, 160);
        panelFondo.add(btnPerfil);
        btnPerfil.addActionListener(e -> {
            new ClientesView(cliente).setVisible(true);
            dispose();
        });

        JButton btnCoches = crearBoton("VER COCHES");
        btnCoches.setBounds(420, 180, 160, 160);
        panelFondo.add(btnCoches);
        btnCoches.addActionListener(e -> {
            new CochesView(cliente).setVisible(true);
            dispose();
        });

        JButton btnAlquilados = crearBoton("ALQUILADOS");
        btnAlquilados.setBounds(640, 180, 160, 160);
        panelFondo.add(btnAlquilados);
        btnAlquilados.addActionListener(e -> {
            new AlquileresView(cliente).setVisible(true);
            dispose();
        });

        // Cerrar sesión
        JButton btnCerrarSesion = new JButton("CERRAR SESIÓN");
        btnCerrarSesion.setBounds(780, 430, 180, 30);
        btnCerrarSesion.setBackground(new Color(231, 76, 60));
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setFont(new Font("Monospaced", Font.BOLD, 14));
        btnCerrarSesion.setFocusPainted(false);
        panelFondo.add(btnCerrarSesion);
        btnCerrarSesion.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });
    }

    private JButton crearControlVentana(String texto, Color fondo) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);
        btn.setMargin(new Insets(0, 0, 0, 0));
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(fondo);
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(fondo.darker());
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(fondo);
            }
        });

        return btn;
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Monospaced", Font.BOLD, 16));
        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    public static void main(String[] args) {
        Cliente mock = new Cliente(1, "NombreDeUsuario", "Apellido", "correo@correo.com", "123", "123");
        SwingUtilities.invokeLater(() -> new PrincipalView(mock).setVisible(true));
    }
}
