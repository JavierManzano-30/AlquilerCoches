package utils;

public class Validador {

    public static boolean esNombreValido(String nombre) {
        return nombre != null && nombre.matches("^[A-Za-zÁÉÍÓÚÑáéíóúñ\\s]+$");
    }

    public static boolean esApellidoValido(String apellido) {
        return apellido != null && apellido.matches("^[A-Za-zÁÉÍÓÚÑáéíóúñ\\s]+$");
    }

    public static boolean esEmailValido(String email) {
        if (email == null) return false;
        return email.matches("^[\\w.-]+@((gmail|hotmail|outlook|yahoo)\\.(com|es|net))$");
    }

    public static boolean esTelefonoValido(String telefono) {
        return telefono != null && telefono.matches("^\\d{9,15}$");
    }

    public static boolean esPasswordValida(String password) {
        return password != null && password.length() >= 6;
    }

    public static boolean esDniValido(String dni) {
        if (dni == null || !dni.matches("^[0-9]{8}[A-HJ-NP-TV-Z]$")) {
            return false;
        }

        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int numero = Integer.parseInt(dni.substring(0, 8));
        char letraEsperada = letras.charAt(numero % 23);
        char letraIntroducida = Character.toUpperCase(dni.charAt(8));

        return letraEsperada == letraIntroducida;
    }
}
