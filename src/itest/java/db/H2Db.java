package db;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class H2Db {

    private static final Logger LOGGER = LoggerFactory.getLogger(H2Db.class);

    private final Server server;

    public H2Db() throws SQLException {
//        server = Server.createWebServer("-webDaemon");
        server = Server.createWebServer();
    }

    public void start() throws SQLException {
        LOGGER.info("starting h2 DB server...");
        server.start();
    }

    public void stop() {
        server.stop();
    }

    public static void main(String[] args) throws SQLException {
        new H2Db().start();
    }
}
