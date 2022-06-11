package py.una.pol.personas.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.inject.Inject;

public class Bd {

    
    private static final String url = "jdbc:mysql://localhost:3306/db_tareas";
    private static final String user = "root";
    private static final String password = "root";
 
    /**
     * @return objeto Connection 
     */
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    

}
