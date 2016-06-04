import com.google.inject.Guice;
import com.google.inject.Injector;
import conf.PropertiesReader;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.PlaytestServlet;

import java.sql.SQLException;

public class Startup {

    private static final String DEFAULT_PROPERTY_FILE = "config.properties";
    private static final String SERVER_DAEMON = "serverDaemon";
    private static final String STARTED_APPLICATION_FLAG = "Started Application";
    private static final Logger LOGGER = LoggerFactory.getLogger(Startup.class);
    private static final String USE_H2_DB = "useH2DB";

    public void start(String propertyFile) throws Exception {
        PropertiesReader propertiesReader = new PropertiesReader(propertyFile);
        Injector injector = Guice.createInjector(new PlaytestModule(propertiesReader));

        boolean useH2DB = propertiesReader.getBooleanProperty(USE_H2_DB, false);
        if (useH2DB) {
            startH2DBServer();
        }

        JettyServer jettyServer = injector.getInstance(JettyServer.class);
        PlaytestServlet playtestServlet = injector.getInstance(PlaytestServlet.class);
        startServer(jettyServer, propertiesReader, playtestServlet);

    }

    private void startH2DBServer() throws SQLException {
        Server server = Server.createWebServer("-webDaemon");
        server.start();
        LOGGER.info("started h2 DB server");
    }

    private void startServer(JettyServer jettyServer, PropertiesReader propertiesReader, Object... servlets) throws Exception {
        jettyServer.start();

        jettyServer.registerServlet(servlets);

        LOGGER.info(STARTED_APPLICATION_FLAG);

        boolean isDaemon = propertiesReader.getBooleanProperty(SERVER_DAEMON);
        if (!isDaemon) {
            jettyServer.join();
        }
    }

    public static void main(String[] args) throws Exception {
        String propertyFile = args.length == 0 ? DEFAULT_PROPERTY_FILE : args[0];
        new Startup().start(propertyFile);
    }

}
