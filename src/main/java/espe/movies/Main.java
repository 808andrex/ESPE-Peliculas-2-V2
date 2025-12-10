package espe.movies;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import espe.movies.modelo.Contenido.Genero;
import espe.movies.modelo.Contenido.Pelicula;
import espe.movies.modelo.Contenido.Serie;
import espe.movies.dao.PeliculaDAO;
import espe.movies.controlador.PeliculaController;
import espe.movies.vista.Util.ScannerUtils;


public class Main {
    public static final String VERSION = "1.0.0";
    public static final String NOMBRE_PLATAFORMA  = "ESPE Movies \uD83C\uDFAC";
    public static final int INICIAR_SESSION  = 1;
    public static final int REGISTRO_DE_CONTENIDO = 2;
    public static final int MOSTRAR_CONTENIDO  = 3;
    public static final int ELIMINAR_CONTENIDO = 4;
    public static final int SUGERIR_POPULAR  = 5;
    public static final int ALQUILAR_PELICULA  = 6;
    public static final int DEVOLVER_PELICULA  = 7;
    public static final int SALIR  = 8;
    private static PeliculaController netflixESPE = new PeliculaController(NOMBRE_PLATAFORMA);
    private static PeliculaDAO netflixESPEMongo = new PeliculaDAO();

    public static void main(String[] args) {
        System.out.println(NOMBRE_PLATAFORMA +" v"+VERSION);
        cargarPeliculas(netflixESPE);
        menu();
    }

    public static void menu(){
        int opcion =0;
        do {
            System.out.println("\n----- MENU ");
            System.out.println("1. Iniciar sesion");
            System.out.println("2. Regitro de contenido");
            System.out.println("3. Mostrar contenido registrado al catalogo");
            System.out.println("4. Eliminar Pelicula del Catalogo");
            System.out.println("5. Sugerencias Populares");
            System.out.println("6. Alquilar Pelicula");
            System.out.println("7. Devolver Pelicula");
            System.out.println("8. Salir");
            opcion=ScannerUtils.capturarNumero("Eliga una opcion: ");

            switch (opcion){
                case REGISTRO_DE_CONTENIDO -> registroContenido();
                case MOSTRAR_CONTENIDO -> mostrarContenido();
                case INICIAR_SESSION -> iniciarSessionUsuario();
                case SUGERIR_POPULAR -> sugerenciasPopulares();
                case ALQUILAR_PELICULA -> alquilarPelicula();
                case DEVOLVER_PELICULA -> devolverPelicula();
                case ELIMINAR_CONTENIDO -> eliminarContenido();
                case SALIR -> System.out.println("¡Gracias por usar ESPE Movies! \uD83D\uDC4B");
                default -> System.out.println("Error: opcion incorrecto debe ser del (1-5)");
            }
        }while (opcion != SALIR);
    }

    public static void iniciarSessionUsuario(){
        System.out.println("Proximamente.....");
    }

    public static void registroContenido(){
        char otro;
        do {
            String titulo = ScannerUtils.capturarTexto("Nombre");
            Genero genero = ScannerUtils.capturarGenero("Selecciona el Genero");
            double calificacion = ScannerUtils.capturarDecimal("Calificaciones (0-5)");
            int duracion = ScannerUtils.capturarNumero("Duracion (min)");
            boolean esPelicula = ScannerUtils.capturarBooleano("¿Es pelicula? (true/false)");

            if(esPelicula){
                boolean ganoOscar = ScannerUtils.capturarBooleano("Gano un oscar? (True/false)");
                Pelicula movies = new Pelicula(titulo, genero, duracion, calificacion, ganoOscar);
                netflixESPE.agregarContenido(movies);
                netflixESPEMongo.guardar(movies);
            }else{
                int numTemporadas = ScannerUtils.capturarNumero("Num temporadas");
                Serie serie = new Serie(titulo, genero, duracion, calificacion, numTemporadas);
                netflixESPE.agregarContenido(serie);
            }

            otro = ScannerUtils.capturarChar("¿Deseas agregar otro contenido? (s/n)");
        }while(otro == 's'|| otro == 'S'|| otro == 'y' || otro == 'Y');
        netflixESPE.guardarJSON();
    }

    public static void mostrarContenido(){
        netflixESPE.mostrarCatalogo();
    }

    public static void sugerenciasPopulares(){
        netflixESPE.sugerirPopular();
    }

    public static void alquilarPelicula(){
        char otroAlquilar;
        do{
            netflixESPE.mostrarPelículasDisponibles();

            String tituloRentado = ScannerUtils.capturarTexto("¿Cual es el titulo que deseas alquilar");
            netflixESPE.alquilarTitulo(tituloRentado);
            otroAlquilar = ScannerUtils.capturarChar("¿Deseas alquilar otra pelicula (s/n)");
        }while(otroAlquilar == 's'|| otroAlquilar == 'S'|| otroAlquilar == 'y'|| otroAlquilar == 'Y');
    }


    public static void devolverPelicula(){
        char otroDevolver;
        do{
            netflixESPE.mostrarPeliculasAlquiladas();
            String tituloDevuelto = ScannerUtils.capturarTexto("¿Cual es el titulo que deseas devolver");
            netflixESPE.devolverTitulo(tituloDevuelto);
            otroDevolver = ScannerUtils.capturarChar("¿Deseas devolver otra pelicula (s/n)");
        }while(otroDevolver == 's'|| otroDevolver == 'S'|| otroDevolver == 'y'|| otroDevolver == 'Y');
    }

    public static void eliminarContenido(){
        char otroEliminar;
        System.out.println("\n------ ELIMINAR CONTENIDO ------");
        netflixESPE.mostrarCatalogo();
        do{
            String eliminarContenido = ScannerUtils.capturarTexto("¿Cual titulo deseas eliminar del catalogo?");
            netflixESPE.eliminarContenido(eliminarContenido);
            otroEliminar = ScannerUtils.capturarChar("¿Quieres eliminar otra titulo del catalogo?(s/n)");
        }while (otroEliminar == 's'|| otroEliminar == 'S' || otroEliminar == 'y'|| otroEliminar == 'Y');
    }
    public static void cargarPeliculas(PeliculaController plataforma) {
        plataforma.agregarContenido(new Pelicula("Inception", Genero.CIENCIA_FICCION, 148, 4.9, true));
        plataforma.agregarContenido(new Pelicula("Titanic", Genero.DRAMA, 195, 4.6, true));
        plataforma.agregarContenido(new Pelicula("John Wick", Genero.ACCION, 101, 4.2, true));
        plataforma.agregarContenido(new Pelicula("El Conjuro", Genero.TERROR, 112, 3.0, true));
        plataforma.agregarContenido(new Pelicula("Coco", Genero.COMEDIA, 105, 4.7, true));
        plataforma.agregarContenido(new Pelicula("Interstellar", Genero.CIENCIA_FICCION, 169, 5.0, true));
        plataforma.agregarContenido(new Pelicula("Joker", Genero.DRAMA, 122, 4.3, true));
        plataforma.agregarContenido(new Pelicula("Toy Story", Genero.COMEDIA, 81, 4.5, true));
        plataforma.agregarContenido(new Pelicula("Shrek", Genero.COMEDIA, 90, 4.0, true));
        plataforma.agregarContenido(new Pelicula("Avengers: Endgame", Genero.ACCION, 181, 3.9, true));
    }

}