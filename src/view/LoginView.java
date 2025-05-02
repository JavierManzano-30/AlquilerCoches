package view;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.ImageIcon;

public class LoginView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtEmail;
	private JPasswordField txtPassword;
	private JLabel lblError;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView frame = new LoginView();
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
	public LoginView() {
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setBounds(100, 100, 450, 300);
	    contentPane = new JPanel() {
	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            ImageIcon imagen = new ImageIcon("C:\\Users\\javie\\Documents\\GitHub\\AlquilerCoches\\src\\utils\\LoginImage.jpg");
	            g.drawImage(imagen.getImage(), 0, 0, getWidth(), getHeight(), this);
	        }
	    };
	    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	    setContentPane(contentPane);
	    contentPane.setLayout(null);

	    // COMPONENTES DE LOGIN
	    JLabel lblNewLabel = new JLabel("Correo Electrónico");
	    lblNewLabel.setBounds(10, 22, 125, 13);
	    contentPane.add(lblNewLabel);

	    txtEmail = new JTextField();
	    txtEmail.setBounds(145, 19, 96, 19);
	    contentPane.add(txtEmail);
	    txtEmail.setColumns(10);

	    JLabel lblNewLabel_1 = new JLabel("Contraseña");
	    lblNewLabel_1.setBounds(10, 52, 87, 13);
	    contentPane.add(lblNewLabel_1);

	    txtPassword = new JPasswordField();
	    txtPassword.setBounds(145, 52, 96, 19);
	    contentPane.add(txtPassword);

	    JButton btnLogin = new JButton("Login");
	    btnLogin.setBounds(145, 81, 96, 21);
	    contentPane.add(btnLogin);

	    lblError = new JLabel("");
	    lblError.setForeground(new Color(255, 0, 0));
	    lblError.setBounds(154, 197, 200, 19);
	    lblError.setVisible(false);
	    contentPane.add(lblError);
	}
}
