package view;

import dao.AlquilerDAO;
import model.Alquiler;
import model.Cliente;
import model.Coche;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * Vista para mostrar los detalles de un coche individual.
 */
public class DetalleCocheView extends BaseView {

    public JPanel panelPrincipal;

    private final Coche coche;
    private final Cliente cliente;

    public DetalleCocheView(Coche coche, Cliente cliente) {
        super("Detalle del Coche", 500, 450);
        this.coche = coche;
        this.cliente = cliente;
    }

    @Override
    public void inicializarComponentes() {
        if (panelPrincipal == null) {
            panelPrincipal = new JPanel();
        }

        panelPrincipal.setLayout(new BorderLayout());

        JPanel panelDatos = new JPanel();
        panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.Y_AXIS));
        panelDatos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblImagen = new JLabel();
        try {
            String path = "/utils/image/" + coche.getMarca().toLowerCase() + "_detalle.jpg";
            java.net.URL url = getClass().getResource(path);
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                Image scaled = icon.getImage().getScaledInstance(380, 150, Image.SCALE_SMOOTH);
                lblImagen.setIcon(new ImageIcon(scaled));
            } else {
                lblImagen.setText("[Imagen no disponible]");
            }
        } catch (Exception e) {
            lblImagen.setText("[Error al cargar imagen]");
        }

        lblImagen.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDatos.add(lblImagen);
        panelDatos.add(Box.createVerticalStrut(10));

        panelDatos.add(new JLabel("Marca: " + coche.getMarca()));
        panelDatos.add(new JLabel("Modelo: " + coche.getModelo()));
        panelDatos.add(new JLabel("Año: " + coche.getAnio()));
        panelDatos.add(new JLabel("Caballos: " + coche.getCaballos() + " HP"));
        panelDatos.add(new JLabel("Cilindrada: " + coche.getCilindrada() + " cc"));
        panelDatos.add(new JLabel("Precio por día: €" + coche.getPrecio()));

        JPanel panelBotones = new JPanel();
        JButton btnAlquilar = new JButton("Alquilar");
        JButton btnVolver = new JButton("Volver");

        btnAlquilar.addActionListener(e -> alquilarCoche());
        btnVolver.addActionListener(e -> {
            Window ventana = SwingUtilities.getWindowAncestor(panelPrincipal);
            if (ventana != null) ventana.dispose();
        });

        panelBotones.add(btnAlquilar);
        panelBotones.add(btnVolver);

        panelPrincipal.add(panelDatos, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
    }

    private void alquilarCoche() {
        String input = JOptionPane.showInputDialog(this.panelPrincipal, "¿Cuántos días deseas alquilar el coche?");
        if (input == null || input.trim().isEmpty()) return;

        int dias;
        try {
            dias = Integer.parseInt(input.trim());
            if (dias <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.panelPrincipal, "Número de días inválido.");
            return;
        }

        double total = dias * coche.getPrecio();
        Alquiler alquiler = new Alquiler();
        alquiler.setIdCliente(cliente.getId());
        alquiler.setIdCoche(coche.getId());
        alquiler.setFechaInicio(LocalDate.now().toString());
        alquiler.setFechaFin(LocalDate.now().plusDays(dias).toString());
        alquiler.setTotal(total);

        AlquilerDAO dao = new AlquilerDAO();
        boolean exito = dao.crearAlquiler(alquiler);

        if (exito) {
            JOptionPane.showMessageDialog(this.panelPrincipal, "¡Alquiler realizado correctamente!");
            Window ventana = SwingUtilities.getWindowAncestor(panelPrincipal);
            if (ventana != null) ventana.dispose();
            new AlquileresView(cliente).mostrar();
        } else {
            JOptionPane.showMessageDialog(this.panelPrincipal, "Error al registrar el alquiler.");
        }
    }
}
