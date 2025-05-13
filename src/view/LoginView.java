package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.EmptyBorder;

import dao.ClienteDAO;
import model.Cliente;

public class LoginView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JLabel lblError;
    private final ImageIcon Fondo = new ImageIcon(getClass().getResource("/utils/image/PrincipalImage.jpg"));

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LoginView frame = new LoginView();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public LoginView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(Fondo.getImage(), 0, 0, getWidth(), getHeight(), this);
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
        txtEmail.setBounds(145, 19, 200, 19);
        contentPane.add(txtEmail);
        txtEmail.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Contraseña");
        lblNewLabel_1.setBounds(10, 52, 87, 13);
        contentPane.add(lblNewLabel_1);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(145, 52, 200, 19);
        contentPane.add(txtPassword);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(145, 81, 96, 21);
        contentPane.add(btnLogin);

        lblError = new JLabel("");
        lblError.setForeground(new Color(255, 0, 0));
        lblError.setBounds(145, 110, 250, 19);
        lblError.setVisible(false);
        contentPane.add(lblError);
        
        JButton btnRegistrar = new JButton("Crear cuenta");
        btnRegistrar.addActionListener(e -> {
            new RegistroView(); // Lanza la ventana de registro
            dispose(); // Opcional: cerrar la ventana de login
        });
        btnRegistrar.setBounds(250, 82, 120, 20); // Posición y tamaño
        contentPane.add(btnRegistrar); // Agrega el botón al panel

        // Acción del botón de login
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = txtEmail.getText().trim();
                String password = new String(txtPassword.getPassword()).trim();

                if (email.isEmpty() || password.isEmpty()) {
                    lblError.setText("Por favor, complete todos los campos.");
                    lblError.setVisible(true);
                    return;
                }

                ClienteDAO dao = new ClienteDAO();
                Cliente cliente = dao.validarLogin(email, password);

                if (cliente != null) {
                    PrincipalView principal = new PrincipalView(cliente);
                    principal.setVisible(true);
                    dispose();
                } else {
                    lblError.setText("Credenciales incorrectas.");
                    lblError.setVisible(true);
                }
            }
        });
    }
}