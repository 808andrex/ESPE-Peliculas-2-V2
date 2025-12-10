package espe.movies;

import espe.movies.view.VentanaPrincipal;


public class Main {
    public static final String VERSION = "2.0.0";
    public static final String NOMBRE_PLATAFORMA  = "ESPE Movies \uD83C\uDFAC";

    public static void main(String[] args) {
        System.out.println(NOMBRE_PLATAFORMA +" v"+VERSION);
        VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
        ventanaPrincipal.setVisible(true);
    }
}