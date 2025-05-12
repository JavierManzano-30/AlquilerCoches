package view;

import java.awt.EventQueue;
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
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.table.JTableHeader;

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

		// Panel personalizado con fondo
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
		lblTitulo.setBounds(150, 10, 163, 30);
		contentPane.add(lblTitulo);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 51, 380, 169);
		contentPane.add(scrollPane);

		tableCoches = new JTable();
		scrollPane.setViewportView(tableCoches);

		// Crear el modelo de tabla con columnas
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(new String[] {
			"ID", "Marca", "Modelo", "Año", "Precio por día (€)", "Disponible"
		});

		model.addRow(new Object[] {1, "Nissan", "GT-R", 2020, 150, "Sí"});
		model.addRow(new Object[] {2, "Toyota", "Supra", 2019, 130, "Sí"});
		model.addRow(new Object[] {3, "Mazda", "RX-7", 2002, 110, "No"});
		model.addRow(new Object[] {4, "Honda", "NSX", 2021, 180, "Sí"});

		tableCoches.setModel(model);
		
		// Hacer scrollPane y su viewport transparentes
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(null);

		// Hacer la tabla semitransparente
		tableCoches.setOpaque(false);
		tableCoches.setBackground(new Color(255, 255, 255, 150)); // blanco con alpha 150
		tableCoches.setForeground(Color.BLACK);
		tableCoches.setShowGrid(false); // opcional: oculta bordes de celdas

		// Ajustar el color del encabezado de columna
		JTableHeader header = tableCoches.getTableHeader();
		header.setOpaque(false);
		header.setBackground(new Color(255, 255, 255, 180));
		header.setForeground(Color.BLACK);

		JButton btnAlquilar = new JButton("Alquilar Coche");
		btnAlquilar.setBounds(148, 232, 141, 21);
		contentPane.add(btnAlquilar);
	}
}
