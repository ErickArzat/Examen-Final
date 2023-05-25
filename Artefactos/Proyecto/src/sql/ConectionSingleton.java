package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


//ConectionSingleton es una clase que establece la conexion con la base de datos SQL, en esta clase se utiliza el patron singleton
public class ConectionSingleton {
    private static Connection c = null;
    private String user = "root";
    private String password = "";
    private String host = "127.0.0.1";
    private int port = 3306;
    private String database = "bancofinal";

    //Este metodo forma parte del patron singleton, donde devuelva la instancia creada de Connection, si no ha sido instanciado se crea la instancia
    public static Connection getConnection(){
        if (c == null)
            new ConectionSingleton();

            return c;
    }

    //Este es el metodo constructor que crea la conexion a la base de datos
    private ConectionSingleton() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = String.format("jdbc:mysql://%s:/%s", host, database);
            c = DriverManager.getConnection(url, user, password);
            System.out.println("Conexion Exitosa");
        } catch (ClassNotFoundException e) {
            System.out.println("Libreria no encontrada: " + e.getMessage());
        }catch (SQLException e) {
            System.out.println("Conexion no exitosa: " + e.getMessage());
        }

        

    }
}
