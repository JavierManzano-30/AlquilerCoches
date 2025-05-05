package view;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PrincipalView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private final Color COLOR_BASE = new Color(137, 161, 251);
	private final Color COLOR_HOVER = new Color(107, 131, 221);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				PrincipalView frame = new PrincipalView();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PrincipalView() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\javie\\Documents\\GitHub\\AlquilerCoches\\src\\utils\\PrincipalImage.jpg"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		// Panel personalizado con fondo pintado
		contentPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon fondo = new ImageIcon("C:\\Users\\javie\\Documents\\GitHub\\AlquilerCoches\\src\\utils\\PrincipalImage.jpg");
				Image img = fondo.getImage();
				g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
			}
		};

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		contentPane.add(crearBotonRedondeado("Ver Coches", 297, 61, 114, 33));
		contentPane.add(crearBotonRedondeado("Mis Alquileres", 297, 104, 114, 34));
		contentPane.add(crearBotonRedondeado("Cerrar Sesión", 297, 148, 114, 33));

		JPanel panel = new JPanel();
		panel.setOpaque(false); // Para que se vea el fondo
		panel.setBounds(0, 10, 436, 61);
		panel.setLayout(null);
		contentPane.add(panel);

		JLabel lblBienvenido = new JLabel("Bienvenido");
		lblBienvenido.setBounds(163, 22, 103, 13);
		lblBienvenido.setForeground(Color.BLACK);
		lblBienvenido.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblBienvenido);
	}

	// Método para crear un botón redondeado con efecto hover
	private JButton crearBotonRedondeado(String texto, int x, int y, int ancho, int alto) {
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
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(Color.WHITE);
				g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
				g2.dispose();
			}

			@Override
			public void setForeground(Color fg) {
				super.setForeground(Color.WHITE);
			}

			{
				setForeground(Color.WHITE);
				setOpaque(false);
				setContentAreaFilled(false);
				setBorderPainted(false);
				setFocusPainted(false);

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
			}
		};

		boton.setBounds(x, y, ancho, alto);
		return boton;
	}
}
