import com.google.inject.Guice;
import com.google.inject.Injector;
import conf.PropertiesReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.PlaytestServlet;

public class Startup {

    private static final String DEFAULT_PROPERTY_FILE = "config.properties";
    private static final String SERVER_DAEMON = "serverDaemon";
    private static final String STARTED_APPLICATION_FLAG = "Started Application";
    private static final Logger LOGGER = LoggerFactory.getLogger(Startup.class);

    public void start(String propertyFile) throws Exception {

        PropertiesReader propertiesReader = new PropertiesReader(propertyFile);
        Injector injector = Guice.createInjector(new PlaytestModule(propertiesReader));

        JettyServer jettyServer = injector.getInstance(JettyServer.class);
        jettyServer.start();

        PlaytestServlet playtestServlet = injector.getInstance(PlaytestServlet.class);
        jettyServer.registerServlet(playtestServlet);

        LOGGER.info(STARTED_APPLICATION_FLAG);

        boolean isDaemon = Boolean.parseBoolean(propertiesReader.getProperty(SERVER_DAEMON));
        if (!isDaemon) {
            jettyServer.join();
        }
    }

    public static void main(String[] args) throws Exception {
        String propertyFile = args.length == 0 ? DEFAULT_PROPERTY_FILE : args[0];
        new Startup().start(propertyFile);
    }

}
