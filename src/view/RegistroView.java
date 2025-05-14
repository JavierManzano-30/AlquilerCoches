package view;

import dao.ClienteDAO;
import dao.UsuarioDAO;
import model.Cliente;
import model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegistroView extends JFrame {

    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEmail;
    private JTextField txtTelefono;
    private JPasswordField txtPassword;
    private JLabel lblError;

    public RegistroView() {
        setTitle("RentJMDCars - Registro");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        setContentPane(contenedor);

        // Panel izquierdo con imagen
        JPanel panelImagen = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon fondo = new ImageIcon(getClass().getResource("/utils/image/fondo_login.jpg"));
                g.drawImage(fondo.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelImagen.setPreferredSize(new Dimension(700, 500));
        contenedor.add(panelImagen, BorderLayout.CENTER);

        // Panel derecho con formulario
        JPanel panelForm = new JPanel(null);
        panelForm.setPreferredSize(new Dimension(300, 500));
        panelForm.setBackground(Color.WHITE);
        contenedor.add(panelForm, BorderLayout.EAST);

        // Controles personalizados
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

        // Formulario
        JLabel lblTitulo = new JLabel("Crear Cuenta");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitulo.setBounds(80, 50, 200, 30);
        panelForm.add(lblTitulo);

        int y = 100;
        int spacing = 50;

        txtNombre = agregarCampo(panelForm, "Nombre:", y);
        txtApellido = agregarCampo(panelForm, "Apellido:", y += spacing);
        txtEmail = agregarCampo(panelForm, "Email:", y += spacing);
        txtTelefono = agregarCampo(panelForm, "Teléfono:", y += spacing);
        txtPassword = new JPasswordField();
        agregarLabel(panelForm, "Contraseña:", y += spacing);
        txtPassword.setBounds(30, y + 20, 240, 25);
        txtPassword.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txtPassword.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelForm.add(txtPassword);

        JButton btnRegistrar = new JButton("REGISTRAR");
        btnRegistrar.setBounds(30, 380, 110, 30);
        btnRegistrar.setBackground(new Color(76, 175, 80));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);
        panelForm.add(btnRegistrar);

        JButton btnVolver = new JButton("VOLVER");
        btnVolver.setBounds(160, 380, 110, 30);
        btnVolver.setBackground(new Color(121, 134, 203));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        panelForm.add(btnVolver);

        lblError = new JLabel("");
        lblError.setBounds(30, 430, 250, 25);
        lblError.setForeground(Color.RED);
        lblError.setFont(new Font("SansSerif", Font.PLAIN, 11));
        panelForm.add(lblError);

        // Acciones
        btnRegistrar.addActionListener(e -> registrarCliente());
        btnVolver.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });
    }

    private JTextField agregarCampo(JPanel panel, String etiqueta, int y) {
        agregarLabel(panel, etiqueta, y);
        JTextField campo = new JTextField();
        campo.setBounds(30, y + 20, 240, 25);
        campo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        campo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(campo);
        return campo;
    }

    private void agregarLabel(JPanel panel, String texto, int y) {
        JLabel label = new JLabel(texto);
        label.setBounds(30, y, 240, 20);
        label.setFont(new Font("SansSerif", Font.PLAIN, 13));
        panel.add(label);
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

    private void registrarCliente() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || password.isEmpty()) {
            lblError.setText("Completa todos los campos.");
            return;
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        if (usuarioDAO.existeUsuario(email)) {
            lblError.setText("Ese email ya está en uso.");
            return;
        }

        Usuario usuario = new Usuario(email, password, "cliente");
        Cliente cliente = new Cliente(0, nombre, apellido, email, telefono, password);

        boolean okUser = usuarioDAO.registrarUsuario(usuario);
        boolean okCliente = new ClienteDAO().registrarCliente(cliente);

        if (okUser && okCliente) {
            JOptionPane.showMessageDialog(this, "Registro exitoso.");
            new LoginView().setVisible(true);
            dispose();
        } else {
            lblError.setText("Error al registrar.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistroView().setVisible(true));
    }
}
