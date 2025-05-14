package view;

import dao.ClienteDAO;
import dao.UsuarioDAO;
import model.Cliente;
import model.Usuario;

import javax.swing.*;
import java.awt.*;

/**
 * Vista para registrar un nuevo cliente y su usuario.
 */
public class RegistroView extends BaseView {

    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEmail;
    private JTextField txtTelefono;
    private JPasswordField txtPassword;
    private JLabel lblError;

    public RegistroView() {
        super("Registro de Cliente", 450, 400);
    }

    @Override
    protected void inicializarComponentes() {
        panelPrincipal.setLayout(null);

        JLabel lblTitulo = new JLabel("Crear nueva cuenta");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setBounds(130, 10, 250, 30);
        panelPrincipal.add(lblTitulo);

        crearLabel("Nombre:", 60);
        txtNombre = crearCampoTexto(60);

        crearLabel("Apellido:", 100);
        txtApellido = crearCampoTexto(100);

        crearLabel("Email:", 140);
        txtEmail = crearCampoTexto(140);

        crearLabel("Teléfono:", 180);
        txtTelefono = crearCampoTexto(180);

        crearLabel("Contraseña:", 220);
        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 220, 200, 25);
        panelPrincipal.add(txtPassword);

        JButton btnRegistrar = new JButton("Registrarse");
        btnRegistrar.setBounds(150, 270, 200, 30);
        panelPrincipal.add(btnRegistrar);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(150, 310, 200, 25);
        panelPrincipal.add(btnVolver);

        lblError = new JLabel("");
        lblError.setForeground(Color.RED);
        lblError.setBounds(50, 340, 350, 25);
        panelPrincipal.add(lblError);

        btnRegistrar.addActionListener(e -> registrarCliente());
        btnVolver.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });
    }

    private void crearLabel(String texto, int y) {
        JLabel label = new JLabel(texto);
        label.setBounds(50, y, 100, 25);
        panelPrincipal.add(label);
    }

    private JTextField crearCampoTexto(int y) {
        JTextField campo = new JTextField();
        campo.setBounds(150, y, 200, 25);
        panelPrincipal.add(campo);
        return campo;
    }

    private void registrarCliente() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || password.isEmpty()) {
            lblError.setText("Por favor, completa todos los campos obligatorios.");
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {
            lblError.setText("Email no válido.");
            return;
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        if (usuarioDAO.existeUsuario(email)) {
            lblError.setText("Ya existe una cuenta con este email.");
            return;
        }

        Usuario usuario = new Usuario(email, password, "cliente");
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setEmail(email);
        cliente.setTelefono(telefono);
        cliente.setPassword(password);

        boolean creadoUsuario = usuarioDAO.registrarUsuario(usuario);
        boolean creadoCliente = new ClienteDAO().registrarCliente(cliente);

        if (creadoCliente && creadoUsuario) {
            JOptionPane.showMessageDialog(this, "Cuenta creada con éxito. Puedes iniciar sesión.");
            new LoginView().setVisible(true);
            dispose();
        } else {
            lblError.setText("Error al crear cuenta. Intenta de nuevo.");
        }
    }
}
