package view;

import dao.UsuarioDAO;
import dao.ClienteDAO;
import model.Usuario;
import model.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * LoginView estilizado con imagen, borde, controles, y campos ajustados.
 */
public class LoginView extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JLabel lblError;

    public LoginView() {
        setTitle("RentJMDCars - Login");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        setContentPane(contenedor);

        // Imagen 70%
        JPanel panelImagen = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon fondo = new ImageIcon(getClass().getResource("/utils/image/fondo_login.jpg"));
                g.drawImage(fondo.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelImagen.setPreferredSize(new Dimension(700, 500));
        contenedor.add(panelImagen, BorderLayout.CENTER);

        // Formulario 30%
        JPanel panelForm = new JPanel(null);
        panelForm.setPreferredSize(new Dimension(300, 500));
        panelForm.setBackground(Color.WHITE);
        contenedor.add(panelForm, BorderLayout.EAST);

        // Controles de ventana
        JPanel panelControles = new JPanel(null);
        panelControles.setBounds(0, 0, 300, 40);
        panelControles.setBackground(Color.WHITE);

        JButton btnMinimizar = crearControlVentana("—");
        btnMinimizar.setBounds(220, 10, 30, 25);
        btnMinimizar.addActionListener(e -> setState(Frame.ICONIFIED));

        JButton btnCerrar = crearControlVentana("X");
        btnCerrar.setBounds(260, 10, 30, 25);
        btnCerrar.addActionListener(e -> System.exit(0));

        panelControles.add(btnMinimizar);
        panelControles.add(btnCerrar);
        panelForm.add(panelControles);

        JLabel lblTitulo = new JLabel("RentJDMCAR");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitulo.setBounds(85, 60, 200, 30);
        panelForm.add(lblTitulo);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(30, 120, 240, 20);
        panelForm.add(lblUsuario);

        txtUsername = new JTextField();
        txtUsername.setBounds(30, 140, 240, 25);
        txtUsername.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txtUsername.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panelForm.add(txtUsername);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(30, 180, 240, 20);
        panelForm.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(30, 200, 240, 25);
        txtPassword.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txtPassword.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panelForm.add(txtPassword);

        JButton btnLogin = new JButton("LOGIN");
        btnLogin.setBounds(30, 250, 100, 30);
        btnLogin.setBackground(new Color(76, 175, 80));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        panelForm.add(btnLogin);

        JButton btnRegistrar = new JButton("REGISTRAR");
        btnRegistrar.setBounds(160, 250, 110, 30);
        btnRegistrar.setBackground(new Color(121, 134, 203));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);
        panelForm.add(btnRegistrar);

        lblError = new JLabel("");
        lblError.setBounds(30, 300, 250, 25);
        lblError.setForeground(Color.RED);
        lblError.setFont(new Font("SansSerif", Font.PLAIN, 11));
        panelForm.add(lblError);

        // Acciones
        btnLogin.addActionListener(e -> autenticar());
        btnRegistrar.addActionListener(e -> {
            new RegistroView().setVisible(true);
            dispose();
        });
    }

    private JButton crearControlVentana(String texto) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);
        btn.setMargin(new Insets(0, 0, 0, 0));
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setForeground(Color.BLACK);
        btn.setBackground(new Color(230, 230, 230));
        btn.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(210, 210, 210));
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(230, 230, 230));
            }
        });

        return btn;
    }

    private void autenticar() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            lblError.setText("Completa todos los campos");
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();
        Usuario user = dao.login(username, password);

        if (user == null) {
            lblError.setText("Credenciales incorrectas");
            return;
        }

        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.obtenerPorEmail(user.getUsername());

        if (cliente != null) {
            new PrincipalView(cliente).setVisible(true);
            dispose();
        } else {
            lblError.setText("Cliente no encontrado");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}
