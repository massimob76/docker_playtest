package db;

import com.google.inject.Inject;
import conf.PropertiesReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {

    private static final String DRIVER_KEY = "dbDriver";
    private static final String CONNECTION_STRING_KEY = "dbConnectionString";
    private static final String DB_USERNAME = "dbUsername";
    private static final String DB_PASSWORD = "dbPassword";
    private final String connectionString;
    private final String userName;
    private final String password;

    @Inject
    public ConnectionProvider(PropertiesReader propertiesReader) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Class.forName(propertiesReader.getProperty(DRIVER_KEY)).newInstance();
        connectionString = propertiesReader.getProperty(CONNECTION_STRING_KEY);
        userName = propertiesReader.getProperty(DB_USERNAME);
        password = propertiesReader.getProperty(DB_PASSWORD);
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, userName, password);
    }
}
