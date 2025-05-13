package view;

import javax.swing.*;
import java.awt.*;
import model.Cliente;
import dao.ClienteDAO;

public class RegistroView extends JFrame {

    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEmail;
    private JTextField txtTelefono;
    private JPasswordField txtPassword;

    public RegistroView() {
        setTitle("Registro de Cliente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setLayout(new GridLayout(7, 2, 10, 10));

        panel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);

        panel.add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        panel.add(txtApellido);

        panel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panel.add(txtEmail);

        panel.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panel.add(txtTelefono);

        panel.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        JButton btnRegistrar = new JButton("Registrarse");
        JButton btnCancelar = new JButton("Cancelar");

        btnRegistrar.addActionListener(e -> registrarCliente());
        btnCancelar.addActionListener(e -> {
            new LoginView(); // Vuelve a la pantalla de login
            dispose();
        });

        panel.add(btnRegistrar);
        panel.add(btnCancelar);

        add(panel);
        setVisible(true);
    }

    private void registrarCliente() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos obligatorios.");
            return;
        }

        Cliente nuevo = new Cliente();
        nuevo.setNombre(nombre);
        nuevo.setApellido(apellido);
        nuevo.setEmail(email);
        nuevo.setTelefono(telefono);
        nuevo.setPassword(password);

        ClienteDAO dao = new ClienteDAO();
        boolean exito = dao.registrarCliente(nuevo);

        if (exito) {
            JOptionPane.showMessageDialog(this, "¡Registro exitoso! Ahora puedes iniciar sesión.");
            LoginView login = new LoginView();
            login.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar. Verifica que el email no esté en uso.");
        }
    }
}
