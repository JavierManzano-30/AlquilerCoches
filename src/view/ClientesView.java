package view;

import dao.ClienteDAO;
import model.Cliente;

import javax.swing.*;
import java.awt.*;

public class ClientesView extends JFrame {

    private JTextField txtNombre, txtApellido, txtEmail, txtTelefono, txtPassword;
    private final Cliente cliente;

    public ClientesView(Cliente cliente) {
        this.cliente = cliente;

        setTitle("Perfil - RentJDMCar");
        setSize(1000, 500);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        setContentPane(contenedor);

     // PANEL IMAGEN 70% con recorte vertical y desplazamiento izquierda
        JPanel panelImagen = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(Color.DARK_GRAY);

                ImageIcon icon = new ImageIcon(getClass().getResource("/utils/image/fondo_perfil.jpg"));
                Image img = icon.getImage();

                int panelW = getWidth();
                int panelH = getHeight();

                int imgW = img.getWidth(this);
                int imgH = img.getHeight(this);

                // Escalar imagen al alto del panel
                double scale = (double) panelH / imgH;
                int scaledW = (int) (imgW * scale);
                int scaledH = panelH;

                // Calcular desplazamiento para mostrar la parte DERECHA (el morro)
                int x = panelW - scaledW;
                int y = 0;

                g.drawImage(img, x, y, scaledW, scaledH, this);

                // Atenuar con sombra
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(0, 0, 0, 150));
                g2d.fillRect(0, 0, panelW, panelH);
                g2d.dispose();
            }
        };
        panelImagen.setPreferredSize(new Dimension(700, 500));
        contenedor.add(panelImagen, BorderLayout.CENTER);

        // PANEL FORMULARIO 30%
        JPanel panelForm = new JPanel(null);
        panelForm.setPreferredSize(new Dimension(300, 500));
        panelForm.setBackground(Color.WHITE);
        contenedor.add(panelForm, BorderLayout.EAST);

        // TOP BAR
        JPanel topBar = new JPanel(null);
        topBar.setBounds(0, 0, 300, 40);
        topBar.setBackground(Color.WHITE);

        JLabel lblUser = new JLabel("👤 " + cliente.getNombre());
        lblUser.setBounds(10, 10, 160, 20);
        lblUser.setFont(new Font("Monospaced", Font.BOLD, 13));
        topBar.add(lblUser);

        JButton btnMin = crearBoton("—", new Color(160, 190, 210));
        btnMin.setBounds(230, 10, 25, 25);
        btnMin.addActionListener(e -> setState(ICONIFIED));
        topBar.add(btnMin);

        JButton btnCerrar = crearBoton("X", new Color(230, 105, 120));
        btnCerrar.setBounds(260, 10, 25, 25);
        btnCerrar.addActionListener(e -> System.exit(0));
        topBar.add(btnCerrar);

        panelForm.add(topBar);

        JLabel lblTitulo = new JLabel("EDITAR PERFIL");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitulo.setBounds(70, 50, 200, 30);
        panelForm.add(lblTitulo);

        int y = 100;
        panelForm.add(crearLabel("Nombre:", 30, y));
        txtNombre = crearCampo(cliente.getNombre(), 30, y + 20);
        panelForm.add(txtNombre);

        y += 60;
        panelForm.add(crearLabel("Apellido:", 30, y));
        txtApellido = crearCampo(cliente.getApellido(), 30, y + 20);
        panelForm.add(txtApellido);

        y += 60;
        panelForm.add(crearLabel("Correo:", 30, y));
        txtEmail = crearCampo(cliente.getEmail(), 30, y + 20);
        panelForm.add(txtEmail);

        y += 60;
        panelForm.add(crearLabel("Número:", 30, y));
        txtTelefono = crearCampo(cliente.getTelefono(), 30, y + 20);
        panelForm.add(txtTelefono);

        y += 60;
        panelForm.add(crearLabel("Contraseña:", 30, y));
        txtPassword = crearCampo(cliente.getPassword(), 30, y + 20);
        panelForm.add(txtPassword);

        JButton btnGuardar = new JButton("GUARDAR");
        btnGuardar.setBounds(160, 440, 100, 30);
        btnGuardar.setBackground(new Color(100, 150, 100));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.addActionListener(e -> guardarCambios());
        panelForm.add(btnGuardar);

        JButton btnAtras = new JButton("ATRÁS");
        btnAtras.setBounds(30, 440, 100, 30);
        btnAtras.setBackground(new Color(200, 120, 130));
        btnAtras.setForeground(Color.WHITE);
        btnAtras.setFocusPainted(false);
        btnAtras.addActionListener(e -> {
            new PrincipalView(cliente).setVisible(true);
            dispose();
        });
        panelForm.add(btnAtras);
    }

    private JLabel crearLabel(String texto, int x, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setBounds(x, y, 240, 20);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
        return lbl;
    }

    private JTextField crearCampo(String texto, int x, int y) {
        JTextField txt = new JTextField(texto);
        txt.setBounds(x, y, 240, 25);
        txt.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return txt;
    }

    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setBorder(null);
        return btn;
    }

    private void guardarCambios() {
        cliente.setNombre(txtNombre.getText().trim());
        cliente.setApellido(txtApellido.getText().trim());
        cliente.setEmail(txtEmail.getText().trim());
        cliente.setTelefono(txtTelefono.getText().trim());
        cliente.setPassword(txtPassword.getText().trim());

        boolean ok = new ClienteDAO().actualizarCliente(cliente);
        JOptionPane.showMessageDialog(this,
                ok ? "Datos actualizados" : "Error al guardar cambios",
                "Perfil", ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        Cliente clienteMock = new Cliente(1, "Mario", "Rossi", "mario@email.com", "123456789", "secreta");
        SwingUtilities.invokeLater(() -> new ClientesView(clienteMock).setVisible(true));
    }
}
