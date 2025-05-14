package view;

import dao.ClienteDAO;
import model.Cliente;

import javax.swing.*;
import java.awt.*;

/**
 * Vista para mostrar y actualizar los datos del cliente logueado.
 */
public class ClientesView extends BaseView {

    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEmail;
    private JTextField txtTelefono;
    private JLabel lblMensaje;

    private final Cliente clienteActual;

    public ClientesView(Cliente cliente) {
        super("Mi Perfil", 450, 380);
        this.clienteActual = cliente;
    }

    @Override
    protected void inicializarComponentes() {
        panelPrincipal.setLayout(null);

        JLabel lblTitulo = new JLabel("Mi Perfil");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitulo.setBounds(180, 10, 200, 30);
        panelPrincipal.add(lblTitulo);

        crearLabel("Nombre:", 60);
        txtNombre = crearCampoTexto(clienteActual.getNombre(), 60);

        crearLabel("Apellido:", 100);
        txtApellido = crearCampoTexto(clienteActual.getApellido(), 100);

        crearLabel("Email:", 140);
        txtEmail = crearCampoTexto(clienteActual.getEmail(), 140);

        crearLabel("Teléfono:", 180);
        txtTelefono = crearCampoTexto(clienteActual.getTelefono(), 180);

        JButton btnActualizar = new JButton("Actualizar datos");
        btnActualizar.setBounds(150, 230, 200, 30);
        panelPrincipal.add(btnActualizar);
        btnActualizar.addActionListener(e -> actualizarDatos());

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(150, 270, 200, 25);
        panelPrincipal.add(btnVolver);
        btnVolver.addActionListener(e -> {
            new PrincipalView(clienteActual).mostrar();
            dispose();
        });

        lblMensaje = new JLabel("");
        lblMensaje.setBounds(50, 310, 350, 25);
        lblMensaje.setForeground(Color.RED);
        panelPrincipal.add(lblMensaje);
    }

    private void crearLabel(String texto, int y) {
        JLabel label = new JLabel(texto);
        label.setBounds(50, y, 80, 20);
        panelPrincipal.add(label);
    }

    private JTextField crearCampoTexto(String valor, int y) {
        JTextField campo = new JTextField(valor);
        campo.setBounds(150, y, 200, 25);
        panelPrincipal.add(campo);
        return campo;
    }

    private void actualizarDatos() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty()) {
            mostrarMensaje("Los campos obligatorios no pueden estar vacíos.", Color.RED);
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {
            mostrarMensaje("El email no tiene un formato válido.", Color.RED);
            return;
        }

        clienteActual.setNombre(nombre);
        clienteActual.setApellido(apellido);
        clienteActual.setEmail(email);
        clienteActual.setTelefono(telefono);

        ClienteDAO dao = new ClienteDAO();
        boolean actualizado = dao.actualizarCliente(clienteActual);

        if (actualizado) {
            mostrarMensaje("Datos actualizados correctamente.", Color.GREEN);
        } else {
            mostrarMensaje("Error al actualizar los datos.", Color.RED);
        }
    }

    private void mostrarMensaje(String texto, Color color) {
        lblMensaje.setText(texto);
        lblMensaje.setForeground(color);
    }

    public static void main(String[] args) {
        Cliente clienteMock = new Cliente(1, "Juan", "Pérez", "juan@email.com", "123456789", "1234");
        SwingUtilities.invokeLater(() -> new ClientesView(clienteMock).mostrar());
    }
}
