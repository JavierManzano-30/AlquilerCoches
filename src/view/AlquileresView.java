package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class AlquileresView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableAlquileres;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				AlquileresView frame = new AlquileresView();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public AlquileresView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 300);

		// Fondo de imagen
		contentPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon fondo = new ImageIcon("src/utils/AlquileresImage.jpg");
				Image img = fondo.getImage();
				g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
			}
		};
		contentPane.setLayout(null);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblTitulo = new JLabel("Mis Alquileres");
		lblTitulo.setBounds(180, 10, 200, 30);
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		contentPane.add(lblTitulo);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 50, 420, 150);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		contentPane.add(scrollPane);

		tableAlquileres = new JTable();
		tableAlquileres.setOpaque(false);
		tableAlquileres.setBackground(new Color(255, 255, 255, 100));
		tableAlquileres.setForeground(Color.BLACK);
		tableAlquileres.setShowGrid(false);
		tableAlquileres.setFillsViewportHeight(true);

		JTableHeader header = tableAlquileres.getTableHeader();
		header.setOpaque(false);
		header.setBackground(new Color(255, 255, 255, 180));
		header.setForeground(Color.BLACK);
		header.setFont(new Font("Tahoma", Font.BOLD, 12));
		scrollPane.setViewportView(tableAlquileres);

		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(new String[] {
			"ID", "Marca", "Modelo", "Fecha Alquiler", "Días", "Total (€)"
		});
		model.addRow(new Object[] {1, "Toyota", "Supra", "2025-04-30", 3, 390});
		model.addRow(new Object[] {2, "Mazda", "RX-7", "2025-04-28", 1, 110});
		tableAlquileres.setModel(model);

		// Botón con estilo
		JButton btnCancelar = new JButton("Cancelar alquiler");
		btnCancelar.setBounds(170, 215, 160, 25);
		btnCancelar.setBackground(new Color(30, 30, 30));
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFocusPainted(false);
		btnCancelar.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(btnCancelar);
	}
}
