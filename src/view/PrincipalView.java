package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import model.Cliente;

public class PrincipalView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private final Color COLOR_BASE = new Color(137, 161, 251);
    private final Color COLOR_HOVER = new Color(107, 131, 221);
    private final ImageIcon iconoFondo = new ImageIcon(getClass().getResource("/utils/image/LoginImage.jpg"));
    private final ImageIcon Fondo = new ImageIcon(getClass().getResource("/utils/image/PrincipalImage.jpg"));
    private Cliente clienteLogueado;

    private void initUI() {
        setTitle("Alquiler de Coches");
        setIconImage(iconoFondo.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setMinimumSize(new Dimension(400, 300));
        setLocationRelativeTo(null);

        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(Fondo.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        setContentPane(contentPane);

        contentPane.add(Box.createVerticalStrut(40));

        JLabel lblBienvenido = new JLabel("Bienvenido " + clienteLogueado.getNombre());
        lblBienvenido.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblBienvenido.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblBienvenido.setForeground(Color.BLACK);
        contentPane.add(lblBienvenido);

        contentPane.add(Box.createVerticalStrut(30));
        contentPane.add(crearBoton("Ver Coches"));
        contentPane.add(Box.createVerticalStrut(10));
        contentPane.add(crearBoton("Mis Alquileres"));
        contentPane.add(Box.createVerticalStrut(10));
        contentPane.add(crearBoton("Cerrar Sesión"));
    }

    public PrincipalView(Cliente clienteLogueado) {
        this.clienteLogueado = clienteLogueado;
        initUI(); // método para construir la interfaz
    }

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
                setMaximumSize(new Dimension(200, 40));

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

                // 💡 Acciones para cada botón
                addActionListener(e -> {
                    switch (texto) {
                        case "Ver Coches":
                            new CochesView(clienteLogueado).setVisible(true);
                            dispose();
                            break;
                        case "Mis Alquileres":
                            new AlquileresView(clienteLogueado).setVisible(true);
                            dispose();
                            break;
                        case "Cerrar Sesión":
                            new LoginView().setVisible(true);
                            dispose();
                            break;
                    }
                });

            }
        };

        return boton;
    }

}