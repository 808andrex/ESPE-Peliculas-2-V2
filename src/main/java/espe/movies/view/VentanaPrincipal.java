package espe.movies.view;

import espe.movies.controller.PeliculaController;
import espe.movies.model.Genero;
import espe.movies.model.Pelicula;
import espe.movies.model.Serie;
import espe.movies.view.util.ValidacionUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private DefaultTableModel modeloTablaPelicula;

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

        String[] cabecera = {"Título", "Género", "Duración", "Rating", "Oscar"};
        modeloTablaPelicula = new DefaultTableModel(cabecera, 0);
        tablaPelicula.setModel(modeloTablaPelicula);
        actualizarTabla();
        btnEliminarPelicula.addActionListener(e -> eliminarPeliculaSeleccionada());
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
            // Refrescar la tabla desde la base de datos (Mongo)
            actualizarTabla();
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
            actualizarTabla();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this,ex.getMessage(),"Error de validacion", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Metodo para borrar la tabla y volver a llenarla desde la Base de Datos
    private void actualizarTabla() {
        // 1. Limpiar filas viejas
        modeloTablaPelicula.setRowCount(0);

        // 2. Pedir lista actualizada al controlador
        var lista = controller.listarPeliculas();

        // 3. Rellenar tabla
        for (Pelicula p : lista) {
            Object[] fila = {
                    p.getTitulo(),
                    p.getGenero(),
                    p.getDuracion() + " min",
                    p.getCalificacion(),
                    p.isGanoOscar() ? "Sí" : "No"
            };
            modeloTablaPelicula.addRow(fila);
        }
    }

    // Metodo para eliminar lo que el usuario seleccionó
    private void eliminarPeliculaSeleccionada() {
        // 1. ¿Qué fila seleccionó el usuario?
        int fila = tablaPelicula.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona una película de la tabla.");
            return;
        }

        // 2. Obtener el título de esa fila (Columna 0)
        String titulo = (String) modeloTablaPelicula.getValueAt(fila, 0);

        // 3. Confirmar
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas eliminar '" + titulo + "'?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            // 4. Llamar al controlador
            controller.eliminarPelicula(titulo);

            // 5. Refrescar la tabla
            actualizarTabla();
            JOptionPane.showMessageDialog(this, "Eliminado correctamente.");
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
