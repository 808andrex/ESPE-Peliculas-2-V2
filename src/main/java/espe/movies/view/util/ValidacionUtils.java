package espe.movies.view.util;

import espe.movies.model.Genero;

public class ValidacionUtils {

    // 1. Validar Texto (Que no esté vacío)
    public static String validarTexto(String texto, String nombreCampo) throws Exception {
        if (texto == null || texto.trim().isEmpty()) {
            throw new Exception("El campo '" + nombreCampo + "' no puede estar vacío.");
        }
        // Tu regex opcional para evitar caracteres raros
        if (!texto.matches("^[a-zA-Z0-9 ñÑáéíóúÁÉÍÓÚ:.-]+$")) {
            throw new Exception("El campo '" + nombreCampo + "' contiene caracteres invalidos.");
        }
        return texto.trim();
    }

    // 2. Validar Enteros (Duración, Temporadas)
    public static int validarEntero(String texto, String nombreCampo) throws Exception {
        if (texto == null || texto.trim().isEmpty()) {
            throw new Exception("El campo '" + nombreCampo + "' es obligatorio.");
        }
        try {
            int numero = Integer.parseInt(texto.trim());
            if (numero < 0) throw new Exception("El campo '" + nombreCampo + "' no puede ser negativo.");
            return numero;
        } catch (NumberFormatException e) {
            throw new Exception("El campo '" + nombreCampo + "' debe ser un numero entero vallido.");
        }
    }

    // 3. Validar Decimales (Calificación)
    public static double validarDecimal(String texto, String nombreCampo) throws Exception {
        try {
            double numero = Double.parseDouble(texto.trim());
            if (numero < 0 || numero > 5) throw new Exception("La calificacion debe estar entre 0 y 5.");
            return numero;
        } catch (NumberFormatException e) {
            throw new Exception("El campo '" + nombreCampo + "' debe ser un numero decimal (ej: 4.5).");
        }
    }

    // 4. Validar Género (Desde texto)
    public static Genero validarGenero(String texto) throws Exception {
        try {
            return Genero.valueOf(texto.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            throw new Exception("Genero no valido. Use: ACCION, DRAMA, COMEDIA, etc.");
        }
    }
}