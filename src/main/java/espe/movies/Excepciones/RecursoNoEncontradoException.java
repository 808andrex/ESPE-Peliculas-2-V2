package espe.movies.Excepciones;

public class RecursoNoEncontradoException extends Exception{
    public RecursoNoEncontradoException(String nombreRecurso){
        super("❌ ERROR: no pudimos encontrar '"+ nombreRecurso +"' en el catalogo.");
    }
}