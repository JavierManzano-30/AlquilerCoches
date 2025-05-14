package view;

import dao.UsuarioDAO;
import dao.ClienteDAO;
import model.Usuario;
import model.Cliente;

import javax.swing.*;
import java.awt.*;

/**
 * Vista de login del sistema.
 * Hereda de BaseView para estructura unificada.
 */
public class LoginView extends BaseView {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JLabel lblError;

    public LoginView() {
        super("Login de Usuario", 450, 300);
    }

    @Override
    protected void inicializarComponentes() {
        panelPrincipal.setLayout(null);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(50, 50, 100, 25);
        panelPrincipal.add(lblUsuario);

        txtUsername = new JTextField();
        txtUsername.setBounds(150, 50, 200, 25);
        panelPrincipal.add(txtUsername);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(50, 90, 100, 25);
        panelPrincipal.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 90, 200, 25);
        panelPrincipal.add(txtPassword);

        JButton btnLogin = new JButton("Iniciar sesión");
        btnLogin.setBounds(150, 130, 200, 30);
        panelPrincipal.add(btnLogin);

        JButton btnRegistrar = new JButton("Crear cuenta");
        btnRegistrar.setBounds(150, 170, 200, 25);
        panelPrincipal.add(btnRegistrar);

        lblError = new JLabel("");
        lblError.setForeground(Color.RED);
        lblError.setBounds(50, 210, 350, 25);
        panelPrincipal.add(lblError);

        // Acción de login
        btnLogin.addActionListener(e -> autenticar());

        // Acción de registro
        btnRegistrar.addActionListener(e -> {
            new RegistroView().setVisible(true);
            dispose();
        });
    }

    /**
     * Intenta autenticar el usuario ingresado.
     */
    private void autenticar() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            mostrarError("Completa todos los campos.");
            return;
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario user = usuarioDAO.login(username, password);

        if (user == null) {
            mostrarError("Usuario o contraseña incorrectos.");
            return;
        }

        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.obtenerPorEmail(user.getUsername());

        if (cliente != null) {
            JOptionPane.showMessageDialog(this, "Bienvenido, " + cliente.getNombre() + "!");
            new PrincipalView(cliente).mostrar();
            dispose();
        } else {
            mostrarError("No se encontró un cliente asociado a este usuario.");
        }
    }

    /**
     * Muestra un mensaje de error debajo del formulario.
     */
    private void mostrarError(String mensaje) {
        lblError.setText(mensaje);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().mostrar());
    }
}
