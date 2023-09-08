package orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {

    private static Connection CONNECTION;

    // TODO: input database user, password and scheme name
    private static final String USER = "";
    private static final String PASSWORD = "";
    private static final String DATABASE_NAME = "";

    private static final String JDBC = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_PARAMS = "?createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false";

    public static void createConnection() throws SQLException {
        if (CONNECTION != null) return;

        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASSWORD);

        String formattedJdbc = JDBC + DATABASE_NAME + DATABASE_PARAMS;

        CONNECTION = DriverManager.getConnection(formattedJdbc, props);
    }

    static Connection getConnection() throws SQLException {
        createConnection();
        return CONNECTION;
    }

    static String getDatabaseName() {
        return DATABASE_NAME;
    }
}
