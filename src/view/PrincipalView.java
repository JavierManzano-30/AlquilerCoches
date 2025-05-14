package view;

import model.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Vista principal que actúa como menú de navegación para el cliente logueado.
 */
public class PrincipalView extends BaseView {

    private final Color COLOR_BASE = new Color(137, 161, 251);
    private final Color COLOR_HOVER = new Color(107, 131, 221);
    private final Cliente clienteLogueado;

    public PrincipalView(Cliente clienteLogueado) {
        super("Alquiler de Coches - Inicio", 600, 400);
        this.clienteLogueado = clienteLogueado;
    }

    @Override
    protected void inicializarComponentes() {
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        JLabel lblBienvenido = new JLabel("Bienvenido " + clienteLogueado.getNombre());
        lblBienvenido.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblBienvenido.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblBienvenido.setForeground(Color.DARK_GRAY);
        panelPrincipal.add(lblBienvenido);

        panelPrincipal.add(Box.createVerticalStrut(30));
        panelPrincipal.add(crearBoton("Ver Coches"));
        panelPrincipal.add(Box.createVerticalStrut(10));
        panelPrincipal.add(crearBoton("Mis Alquileres"));
        panelPrincipal.add(Box.createVerticalStrut(10));
        panelPrincipal.add(crearBoton("Mi Perfil"));
        panelPrincipal.add(Box.createVerticalStrut(10));
        panelPrincipal.add(crearBoton("Cerrar Sesión"));
    }

    /**
     * Crea un botón estilizado con funcionalidad específica según su texto.
     */
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto) {
            private Color currentColor = COLOR_BASE;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(currentColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(Color.WHITE);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();
            }

            {
                setForeground(Color.WHITE);
                setOpaque(false);
                setContentAreaFilled(false);
                setBorderPainted(false);
                setFocusPainted(false);
                setAlignmentX(Component.CENTER_ALIGNMENT);
                setMaximumSize(new Dimension(250, 40));

                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        currentColor = COLOR_HOVER;
                        repaint();
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        currentColor = COLOR_BASE;
                        repaint();
                    }
                });

                addActionListener(e -> manejarAccion(texto));
            }
        };

        return boton;
    }

    /**
     * Define qué hacer según el botón presionado.
     */
    private void manejarAccion(String texto) {
        switch (texto) {
            case "Ver Coches":
                new CochesView(clienteLogueado).mostrar();
                break;
            case "Mis Alquileres":
                new AlquileresView(clienteLogueado).mostrar();
                break;
            case "Mi Perfil":
                new ClientesView(clienteLogueado).mostrar();
                break;
            case "Cerrar Sesión":
                new LoginView().mostrar();
                break;
        }
        dispose();
    }

    public static void main(String[] args) {
        Cliente clienteMock = new Cliente(1, "Carlos", "García", "carlos@email.com", "000000000", "pass123");
        SwingUtilities.invokeLater(() -> new PrincipalView(clienteMock).mostrar());
    }
}
