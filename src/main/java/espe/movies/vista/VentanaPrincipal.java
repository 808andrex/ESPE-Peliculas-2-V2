package espe.movies.vista;

import espe.movies.controlador.PeliculaController;
import espe.movies.modelo.Genero;
import espe.movies.modelo.Pelicula;
import espe.movies.modelo.Serie;
import espe.movies.vista.util.ValidacionUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame{
    private JPanel MainFrame;
    private JButton btnGuardarPelicula;
    private JButton btnLimpiarPelicula;
    private JTextField tituloPelicula;
    private JTextField generoPelicula;
    private JTextField duracionPelicula;
    private JTextField calificacionPelicula;
    private JRadioButton ganoOscarSi;
    private JRadioButton ganoOscarNo;
    private JTable tablaPelicula;
    private JButton btnEliminarPelicula;
    private JTable tablaContenido;
    private JTable tablaSugerencias;
    private JTextField tituloSerie;
    private JTextField generoSerie;
    private JTextField duracionSerie;
    private JTextField calificacionSerie;
    private JTextField nTemporadasSerie;
    private JButton btnGuardarSerie;
    private JButton btnLimpiarSerie;
    private JButton btnEliminarSerie;
    private JTable tablaSerie;
    private PeliculaController controller;

    public VentanaPrincipal(){
        setContentPane(MainFrame);
        setTitle("Netflix ESPE - Gestion de peliculas");
        setSize(800,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.controller = new PeliculaController("ESPE movies");

        ButtonGroup grupoOscar = new ButtonGroup();
        grupoOscar.add(ganoOscarSi);
        grupoOscar.add(ganoOscarNo);
        ganoOscarNo.setSelected(true);

        btnGuardarPelicula.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnGuardarPelicula();
            }
        });

        btnLimpiarPelicula.addActionListener(e ->limpiarCamposPelicula());

        btnGuardarSerie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnGuardarSerie();
            }
        });

        btnLimpiarSerie.addActionListener(e -> limpiarCamposSerie());
    }

    private void btnGuardarPelicula(){
        try{
            String titulo = ValidacionUtils.validarTexto(tituloPelicula.getText(),"Titulo");
            String generoTxt = ValidacionUtils.validarTexto(generoPelicula.getText(),"Genero");

            Genero genero1 = ValidacionUtils.validarGenero(generoTxt);
            int duracion = ValidacionUtils.validarEntero(duracionPelicula.getText(), "Duracion");
            double rating = ValidacionUtils.validarDecimal(calificacionPelicula.getText(), "Calificacion");
            boolean tieneOscar = ganoOscarSi.isSelected();
            Pelicula pelicula = new Pelicula(titulo,genero1,duracion,rating,tieneOscar);
            controller.agregarContenido(pelicula);
            JOptionPane.showMessageDialog(this,"Guardado exitosamente");
            limpiarCamposPelicula();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this,ex.getMessage(),"Error de validacion", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void btnGuardarSerie(){
        try{
            String titulo = ValidacionUtils.validarTexto(tituloSerie.getText(),"Titulo");
            String generoTxt = ValidacionUtils.validarTexto(generoSerie.getText(),"Genero");

            Genero genero1 = ValidacionUtils.validarGenero(generoTxt);
            int duracion = ValidacionUtils.validarEntero(duracionSerie.getText(), "Duracion");
            double rating = ValidacionUtils.validarDecimal(calificacionSerie.getText(), "Calificacion");
            int nTemporadas = ValidacionUtils.validarEntero(nTemporadasSerie.getText(), "Numero de temporadas");
            Serie serie = new Serie(titulo,genero1,duracion,rating,nTemporadas);
            controller.agregarContenido(serie);
            JOptionPane.showMessageDialog(this,"Guardado exitosamente");
            limpiarCamposSerie();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this,ex.getMessage(),"Error de validacion", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void limpiarCamposPelicula(){
        tituloPelicula.setText("");
        generoPelicula.setText("");
        duracionPelicula.setText("");
        calificacionPelicula.setText("");
        ganoOscarNo.setSelected(true);
    }

    private void limpiarCamposSerie(){
        tituloSerie.setText("");
        generoSerie.setText("");
        duracionSerie.setText("");
        calificacionSerie.setText("");
        nTemporadasSerie.setText("");
    }
}
