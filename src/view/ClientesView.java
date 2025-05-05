package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ClientesView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEmail;
    private JTextField txtTelefono;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ClientesView frame = new ClientesView();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ClientesView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 300);

        // Panel con fondo personalizado
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon fondo = new ImageIcon("src/utils/ClientesImage.jpg");
                Image img = fondo.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };

        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel lblTitulo = new JLabel("Mi Perfil");
        lblTitulo.setBounds(160, 10, 100, 25);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        contentPane.add(lblTitulo);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(50, 50, 80, 20);
        lblNombre.setForeground(Color.WHITE);
        contentPane.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(150, 50, 180, 20);
        contentPane.add(txtNombre);

        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setBounds(50, 80, 80, 20);
        lblApellido.setForeground(Color.WHITE);
        contentPane.add(lblApellido);

        txtApellido = new JTextField();
        txtApellido.setBounds(150, 80, 180, 20);
        contentPane.add(txtApellido);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(50, 110, 80, 20);
        lblEmail.setForeground(Color.WHITE);
        contentPane.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(150, 110, 180, 20);
        contentPane.add(txtEmail);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(50, 140, 80, 20);
        lblTelefono.setForeground(Color.WHITE);
        contentPane.add(lblTelefono);

        txtTelefono = new JTextField();
        txtTelefono.setBounds(150, 140, 180, 20);
        contentPane.add(txtTelefono);

        JButton btnActualizar = new JButton("Actualizar datos");
        btnActualizar.setBounds(125, 190, 150, 25);
        btnActualizar.setBackground(new Color(30, 30, 30));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFocusPainted(false);
        btnActualizar.setFont(new Font("Tahoma", Font.BOLD, 12));
        contentPane.add(btnActualizar);
    }
}
