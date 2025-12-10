package espe.movies.db;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConexionMongo {
    private static final String CONNECTION_STRING = "mongodb+srv://kespe:mongo1234@cluster0.yh8p2fm.mongodb.net/?appName=Cluster0";
    private static final String DATABASE_NAME = "espe_movies";

    private static MongoClient mongoClient;
    private static MongoDatabase database;

    public static MongoDatabase getDatabase(){
        if(database == null){
            try {
                mongoClient = MongoClients.create(CONNECTION_STRING);
                database = mongoClient.getDatabase(DATABASE_NAME);
                System.out.println("✅ Conexion exitosa a MongoDB: "+DATABASE_NAME);
            }catch(Exception e){
                System.out.println("❌ Error al conectar a MongoDB: "+e.getMessage());
            }
        }
        return database;
    }
}
