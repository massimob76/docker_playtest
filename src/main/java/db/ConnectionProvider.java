package db;

import com.google.inject.Inject;
import conf.PropertiesReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionProvider.class);
    private static final String CONNECTION_STRING_KEY = "dbConnectionString";
    private final Connection conn;

    @Inject
    public ConnectionProvider(PropertiesReader propertiesReader) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class.forName(DRIVER).newInstance();
        Connection conn;
        try {
            conn = DriverManager.getConnection(propertiesReader.getProperty(CONNECTION_STRING_KEY));
        } catch (SQLException e) {
            conn = null;
            LOGGER.error("SQL exception: {}", e);
        }
        this.conn = conn;
        LOGGER.info("connection acquired!");
    }

    public Connection getConnetion() {
        return this.conn;
    }
}
