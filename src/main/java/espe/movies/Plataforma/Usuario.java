package espe.movies.Plataforma;

import espe.movies.Contenido.Contenido;

import java.time.LocalDateTime;

public class Usuario {
    private String nombre;
    private String correo;
    private LocalDateTime fechaRegistro;

    public Usuario(String nombre, String correo) {
        this.nombre = nombre;
        this.correo=correo;
        this.fechaRegistro = LocalDateTime.now();
    }

    public void ver(Contenido video){
        System.out.println(nombre+ " esta viendo ...");
        System.out.println(video.obtenerFichaTecnica());
        video.reproducir();
    }

    /** GETTERS Y SETTERS */
    public String getName() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

}