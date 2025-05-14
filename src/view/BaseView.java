package view;

import javax.swing.*;
import java.awt.*;

/**
 * Clase base para todas las vistas del sistema.
 * Define una estructura común reutilizable.
 */
public abstract class BaseView extends JFrame {

    public JPanel panelPrincipal; // ← público por si se necesita acceder externamente
    private boolean inicializado = false; // ← para evitar inicialización doble

    /**
     * Constructor base para inicializar la ventana.
     *
     * @param titulo Título de la ventana
     * @param ancho  Ancho en píxeles
     * @param alto   Alto en píxeles
     */
    public BaseView(String titulo, int ancho, int alto) {
        setTitle(titulo);
        setSize(ancho, alto);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        setContentPane(panelPrincipal);
    }

    /**
     * Método abstracto que cada vista debe implementar para agregar sus componentes.
     */
    protected abstract void inicializarComponentes();

    /**
     * Muestra la ventana después de inicializar los componentes.
     */
    public void mostrar() {
        if (!inicializado) {
            inicializarComponentes();
            inicializado = true;
        }
        setVisible(true);
    }

    @Override
    public void setVisible(boolean b) {
        if (b && !inicializado) {
            inicializarComponentes();
            inicializado = true;
        }
        super.setVisible(b);
    }
}
