package espe.movies.excepciones;

public class RecursoNoEncontradoException extends Exception{
    public RecursoNoEncontradoException(String nombreRecurso){
        super("❌ ERROR: no pudimos encontrar '"+ nombreRecurso +"' en el catalogo.");
    }
}