package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import java.awt.Image;
import model.Coche;
import dao.CocheDAO;
import controller.ConexionBD;
import java.sql.Connection;
import java.util.List;
import java.sql.SQLException;


public class CochesView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableCoches;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				CochesView frame = new CochesView();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CochesView() {
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setBounds(100, 100, 450, 300);

	    contentPane = new JPanel() {
	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            ImageIcon fondo = new ImageIcon("src/utils/image/CochesImage.jpg");
	            Image img = fondo.getImage();
	            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	        }
	    };
	    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	    contentPane.setLayout(null);
	    setContentPane(contentPane);

	    JLabel lblTitulo = new JLabel("Coches Disponibles");
	    lblTitulo.setBounds(150, 10, 200, 30);
	    contentPane.add(lblTitulo);
	    lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20)); // Fuente moderna, tamaño 20, negrita
	    lblTitulo.setForeground(Color.WHITE); // Letra blanca


	    JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setBounds(10, 51, 416, 169);
	    contentPane.add(scrollPane);

	    tableCoches = new JTable();
	    scrollPane.setViewportView(tableCoches);

	    DefaultTableModel model = new DefaultTableModel();
	    model.setColumnIdentifiers(new String[] {
	        "Marca", "Modelo", "Año", "Precio por día (€)", "Caballos", "Cilindrada", "Disponible"
	    });
	    
	    tableCoches.setModel(model);

	    // Cargar los coches desde la base de datos
	    try {
	        Connection conexion = ConexionBD.conectar();
	        CocheDAO dao = new CocheDAO(conexion);
	        List<Coche> coches = dao.obtenerTodosLosCoches();

	        for (Coche coche : coches) {
	            System.out.println("Coche agregado: " + coche.getMarca() + " " + coche.getModelo()); // Verifica que los datos se agregan
	            model.addRow(new Object[] {
	                coche.getMarca(),
	                coche.getModelo(),
	                coche.getAnio(),
	                coche.getPrecio(),
	                coche.getCaballos(),
	                coche.getCilindrada(),
	                coche.isDisponible() ? "Sí" : "No"
	            });
	        }

	        conexion.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	 // Hacer scrollPane y su viewport transparentes
	    scrollPane.setOpaque(false);
	    scrollPane.getViewport().setOpaque(false);
	    scrollPane.setBorder(null);

	    // Tabla transparente y sin bordes visibles
	    tableCoches.setOpaque(false);
	    tableCoches.setBackground(new Color(255, 255, 255, 100)); // Más transparencia
	    tableCoches.setForeground(Color.BLACK); // Texto claro
	    tableCoches.setGridColor(new Color(255, 255, 255, 60)); // Líneas muy suaves

	    // Encabezado también semitransparente y estético
	    JTableHeader header = tableCoches.getTableHeader();
	    header.setOpaque(false);
	    header.setBackground(new Color(255, 255, 255, 120));
	    header.setForeground(Color.BLACK);
	    header.setFont(new Font("Segoe UI", Font.BOLD, 8));
	}
}