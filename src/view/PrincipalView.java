package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class PrincipalView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrincipalView frame = new PrincipalView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PrincipalView() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\javie\\Desktop\\personal\\881783.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnCerrarSesion = new JButton("Cerrar Sesión");
		btnCerrarSesion.setBackground(new Color(128, 255, 255));
		btnCerrarSesion.setBounds(165, 177, 114, 33);
		contentPane.add(btnCerrarSesion);
		
		JButton btnVerCoches = new JButton("Ver Coches");
		btnVerCoches.setBackground(new Color(128, 255, 255));
		btnVerCoches.setBounds(165, 90, 114, 33);
		contentPane.add(btnVerCoches);
		
		JButton btnMisAlquileres = new JButton("Mis Alquileres");
		btnMisAlquileres.setBackground(new Color(128, 255, 255));
		btnMisAlquileres.setBounds(165, 133, 114, 34);
		contentPane.add(btnMisAlquileres);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 128));
		panel.setBounds(0, 10, 436, 61);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblBienvenido = new JLabel("Bienvenido");
		lblBienvenido.setBounds(163, 22, 103, 13);
		panel.add(lblBienvenido);
		lblBienvenido.setForeground(new Color(0, 0, 0));
		lblBienvenido.setBackground(Color.WHITE);
		lblBienvenido.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblFondo = new JLabel("New label");
		lblFondo.setIcon(new ImageIcon("C:\\Users\\javie\\Desktop\\personal\\JuanCachondo.png"));
		lblFondo.setBounds(0, -11, 436, 269);
		panel.add(lblFondo);
	}
}
