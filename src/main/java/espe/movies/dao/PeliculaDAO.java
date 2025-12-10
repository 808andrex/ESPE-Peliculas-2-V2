package espe.movies.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import espe.movies.modelo.Contenido.Pelicula;
import espe.movies.db.ConexionMongo;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class PeliculaDAO {
    private final MongoCollection<Pelicula> collection;

    public PeliculaDAO(){
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(),fromProviders(pojoCodecProvider));

        MongoDatabase database = ConexionMongo.getDatabase().withCodecRegistry(pojoCodecRegistry);

        this.collection = database.getCollection("peliculas",Pelicula.class);
    }

    public void guardar(Pelicula pelicula){
        try {
            collection.insertOne(pelicula);
            System.out.println("💾 Pelicula guardado en Mongo ID: "+pelicula.getId());
        }catch(Exception e){
            System.out.println("Error al guardar: "+e.getMessage());
        }
    }

    public List<Pelicula> listar(){
        List<Pelicula> lista = new ArrayList<>();
        collection.find().into(lista);
        return lista;
    }

    public void eliminarPorTitulo(String titulo){
        collection.deleteOne(Filters.eq("titulo"+titulo));
    }
}
