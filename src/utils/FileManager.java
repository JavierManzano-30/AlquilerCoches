package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.Coche;
import model.Cliente;
import model.DetalleAlquiler;

public class FileManager {

    private static final String SEPARADOR = ";";
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void guardarCoches(List<Coche> lista, Path ruta) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta.toFile()))) {
            for (Coche c : lista) {
                bw.write(String.join(SEPARADOR,
                        String.valueOf(c.getId()),
                        c.getMarca(),
                        c.getModelo(),
                        String.valueOf(c.getAnio()),
                        String.valueOf(c.getPrecio()),
                        String.valueOf(c.getCaballos()),
                        String.valueOf(c.getCilindrada()),
                        String.valueOf(c.isDisponible())));
                bw.newLine();
            }
        }
    }

    public static List<Coche> cargarCoches(Path ruta) throws IOException {
        List<Coche> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta.toFile()))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(SEPARADOR);
                if (partes.length != 8) continue;
                try {
                    Coche c = new Coche(
                            Integer.parseInt(partes[0]),
                            partes[1],
                            partes[2],
                            Integer.parseInt(partes[3]),
                            Double.parseDouble(partes[4]),
                            Boolean.parseBoolean(partes[7]),
                            Integer.parseInt(partes[5]),
                            Integer.parseInt(partes[6])
                    );
                    lista.add(c);
                } catch (NumberFormatException ex) {
                    // Ignorar línea con error
                }
            }
        }
        return lista;
    }

    public static void generarFacturaTxt(Cliente cliente, List<DetalleAlquiler> detalles, Path rutaTxt) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaTxt.toFile()))) {
            bw.write("====================================================\n");
            bw.write("     🚗 JAPANESE SPORTS CAR RENTAL - FACTURA 🚗     \n");
            bw.write("====================================================\n\n");
            bw.write(String.format("Cliente : %s %s\n", cliente.getNombre(), cliente.getApellido()));
            bw.write(String.format("Email   : %s\n", cliente.getEmail()));
            bw.write(String.format("Teléfono: %s\n", cliente.getTelefono()));
            bw.write(String.format("Fecha   : %s\n", LocalDate.now().format(FMT)));
            bw.write("\n----------------------------------------------------\n");

            if (detalles.isEmpty()) {
                bw.write("No hay alquileres registrados.\n");
            } else {
                bw.write(String.format("%-6s %-12s %-12s %-5s %-10s\n", "ID", "Marca", "Modelo", "Días", "Total (€)"));
                bw.write("----------------------------------------------------\n");

                double suma = 0;
                for (DetalleAlquiler d : detalles) {
                    Coche c = d.getCoche();
                    bw.write(String.format("%-6d %-12s %-12s %-5d %-10.2f\n",
                            c.getId(), c.getMarca(), c.getModelo(), d.getDias(), d.getTotal()));
                    suma += d.getTotal();
                }

                bw.write("----------------------------------------------------\n");
                bw.write(String.format("%-36s %10.2f €\n", "TOTAL A PAGAR:", suma));
            }

            bw.write("====================================================\n");
            bw.write("Gracias por confiar en nosotros. ¡Conduce con pasión!\n");
            bw.write("====================================================\n");
        }
    }

    public static String leerFactura(Path ruta) throws IOException {
        return Files.readString(ruta);
    }
}
