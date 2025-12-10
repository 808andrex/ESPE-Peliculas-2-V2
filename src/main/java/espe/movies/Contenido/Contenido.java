package espe.movies.Contenido;

import org.bson.types.ObjectId;

import java.time.LocalDate;

public abstract class Contenido implements Comparable<Contenido>{
    public static final double CALIFICACION_MINIMA = 0.0;
    public static final double CALIFICACION_MAXIMA = 5.0;
    public static final double CALIFICACION_PARA_SER_POPULAR = 4.0;

    private   String titulo;
    private Genero genero;
    private  int duracion;
    private LocalDate fechaEstreno;
    private  double calificacion;
    private  boolean disponible;
    private  String descripcion;
    private ObjectId id;

    public Contenido(String titulo, Genero genero, int duracion, double calificacion){
        this.titulo=titulo;
        this.genero = genero;
        this.duracion = duracion;
        this.disponible=true;
        this.fechaEstreno = LocalDate.now();
        setCalificacion(calificacion);
    }

    public Contenido(){}

    /** METODOS*/
    public void reproducir(){
        System.out.println("Reproduciendo: "+this.titulo);
    }

    public boolean isPopular(){
        return getCalificacion() >= CALIFICACION_PARA_SER_POPULAR;
    }

    public abstract String toJSON();

    public String obtenerFichaTecnica(){
        return this.titulo + " ("+this.fechaEstreno +") - "+this.genero +", Duracion: "+this.duracion;
    }

    @Override
    public int compareTo(Contenido otroVideo){
        // Usamos la lógica de String para ordenar por TÍTULO alfabéticamente
        // compareToIgnoreCase hace que "avatar" y "Avatar" se traten igual
        return this.getTitulo().compareToIgnoreCase(otroVideo.getTitulo());
    }

    /** GETTERS */
    public String getTitulo() {
        return titulo;
    }

    public Genero getGenero() {
        return genero;
    }

    public int getDuracion() {
        return duracion;
    }

    public LocalDate getFechaEstreno() {
        return fechaEstreno;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ObjectId getId() {
        return id;
    }

    /** SETTERS */
    public void setCalificacion(double calificacion) {
        if(calificacion >= CALIFICACION_MINIMA && calificacion <= CALIFICACION_MAXIMA){
            this.calificacion = calificacion;
        }else{
            System.out.println("Error: La calificacion debe estar entre "+CALIFICACION_MINIMA+"y "+ CALIFICACION_MAXIMA);
        }
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}
