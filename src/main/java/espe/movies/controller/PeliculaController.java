package espe.movies.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import espe.movies.dao.PeliculaDAO;
import espe.movies.excepciones.RecursoNoEncontradoException;
import espe.movies.model.Alquilable;
import espe.movies.model.Contenido;
import espe.movies.model.Pelicula;

public class PeliculaController {
    private String nombre;
    private ArrayList<Contenido> catalogo;
    private PeliculaDAO peliculaDAO;

    public PeliculaController(String nombre){
        this.nombre = nombre;
        this.catalogo = new ArrayList<>();
        this.peliculaDAO = new PeliculaDAO();
    }

    public void agregarContenido(Contenido video){
        this.catalogo.add(video);

        if(video instanceof Pelicula){
            peliculaDAO.guardar(((Pelicula) video));
            System.out.println("✅ Guardado en Memoria y Base de Datos "+video.getTitulo());
        }
    }

    public void eliminarContenido(String titulo){
        boolean borrador = catalogo.removeIf(video -> video.getTitulo().equalsIgnoreCase(titulo));

        if(borrador){
            System.out.println("✅ se elimino '"+titulo+"' del catalogo");
        }else{
            System.out.println("❌ No se encontro nada con ese el nombre '"+titulo+"' para borrar");
        }
    }

    public void mostrarCatalogo(){
        System.out.println("\n---- CATALOGO DE "+this.nombre+" ----");

        if(catalogo.isEmpty()){
            System.out.println("El catalogo esta vacio");
        }else{
            Collections.sort(this.catalogo);
            catalogo.forEach(video -> System.out.println(video.obtenerFichaTecnica()));
        }
    }

    /** sirve para encontrar una pelicula*/
    public Contenido encontrarCotenido(String tituloParaBuscar) throws RecursoNoEncontradoException {
        return catalogo
                .stream()
                .filter(video -> video.getTitulo().equalsIgnoreCase(tituloParaBuscar))
                .findFirst()
                .orElseThrow(()-> new RecursoNoEncontradoException(tituloParaBuscar));
    }

    public void sugerirPopular(){
        System.out.println("\n----- SUGERENCIA POPULARES --------");
        catalogo.stream()
                .filter(Contenido ::isPopular)
                .forEach(video -> System.out.println("🔥"+video.getTitulo()));
    }

    public void mostrarPelículasDisponibles(){
        System.out.println("\n-------- Peliculas disponibles para alquilar --------");

        var disponibles = catalogo.stream()
                .filter(video -> video instanceof  Alquilable && video.isDisponible())
                .toList();

        if(disponibles.isEmpty()){
            System.out.println("No hay peliculas disponibles en este momento");
        }else{
            disponibles.forEach(video -> System.out.println("🎬 "+video.getTitulo()));
        }
    }

    public void mostrarPeliculasAlquiladas(){
        System.out.println("\n-------- Peliculas Alquiladas --------");

        var disponibles = catalogo.stream()
                .filter(video -> video instanceof  Alquilable && !video.isDisponible())
                .toList();

        if(disponibles.isEmpty()){
            System.out.println("No hay peliculas disponibles en este momento");
        }else{
            disponibles.forEach(video -> System.out.println("🎬 "+video.getTitulo()));
        }
    }

    public void mostrarEstadisticas(){
        long totalPelis = catalogo.stream()
                .filter(video -> video.toJSON().contains("Pelicula"))
                .count();

        long totalSeries = catalogo.size() - totalPelis;

        System.out.println("\n📊 ESTADISTICA");
        System.out.println("Total de videos: "+catalogo.size());
        System.out.println("Peliculas: "+ totalPelis);
        System.out.println("Series: "+totalSeries);
    }

    public void mostrarMejoresPeliculas(){
        System.out.println("\n------ RANKING POR CALIFICACION -----");
        ArrayList<Contenido> copiaLista = new ArrayList<>(this.catalogo);

        copiaLista.sort((v1, v2) -> Double.compare(v2.getCalificacion(),v1.getCalificacion()));

        copiaLista.forEach(video -> System.out.println("⭐ "+video.getCalificacion()+" - "+video.getTitulo()));
    }

    public void alquilarTitulo(String titulo){
        try {
            Contenido video = encontrarCotenido(titulo);

            if(video instanceof Alquilable){
                ((Alquilable) video).alquilar();
            }else{
                System.out.println("⚠️ '" + video.getTitulo() + "' es una Serie y no se puede alquilar.");
            }
        }catch(RecursoNoEncontradoException e){
            System.out.println(e.getMessage());
            System.out.println("Sugerencia: Revisa si escribiste bien el nombre.");
        }
    }

    public void devolverTitulo(String titulo){
        try {
            Contenido video = encontrarCotenido(titulo);

            if(video instanceof Alquilable){
                ((Alquilable) video).devolver();
            }else{
                System.out.println("⚠️ '" + video.getTitulo() + "' es una Serie (o no se alquila), así que no se puede devolver.");
            }
        }catch(RecursoNoEncontradoException e){
            System.out.println(e.getMessage());
            System.out.println("Sugerencia: Revisa si escribiste bien el nombre.");
        }
    }

    public void guardarJSON(){
        try(PrintWriter escribir = new PrintWriter(new FileWriter("datos.json"))){
            escribir.println("[");

            for(int i = 0; i<this.catalogo.size(); i++){
                Contenido video = this.catalogo.get(i);

                escribir.print(video.toJSON());

                if(i< catalogo.size()-1){
                    escribir.println(",");
                }else{
                    escribir.println();
                }
            }
            escribir.println("]");
            System.out.println("!JSON guardado exitosamente en 'datos.json'¡");
        }catch(IOException e){
            System.out.println("❌ Error al guardar el JSON "+e.getMessage());
        }
    }

    public List<Pelicula> listarPeliculas(){
        return peliculaDAO.listar();
    }

    public void eliminarPelicula(String titulo){
        peliculaDAO.eliminarPorTitulo(titulo);
        System.out.println("Se solicito eliminar: "+titulo);
    }
}