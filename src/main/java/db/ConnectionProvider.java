package db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionProvider.class);
    private static final String CONNECTION_STRING = "jdbc:mysql://127.0.0.1:3306/playtest?user=midasdev&password=midasdev";
    private final Connection conn;

    public ConnectionProvider() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class.forName(DRIVER).newInstance();
        Connection conn;
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
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
