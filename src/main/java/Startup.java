import com.google.inject.Guice;
import com.google.inject.Injector;
import conf.PropertiesReader;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.PlaytestServlet;

public class Startup {

    private static final String DEFAULT_PROPERTY_FILE = "config.properties";
    private static final String SERVER_PORT = "serverPort";
    private static final String SERVER_DAEMON = "serverDaemon";
    private static final String STARTED_APPLICATION_FLAG = "Started Application";
    private static final Logger LOGGER = LoggerFactory.getLogger(Startup.class);

    public void start(String propertyFile) throws Exception {

        PropertiesReader propertiesReader = new PropertiesReader(propertyFile);
        Injector injector = Guice.createInjector(new PlaytestModule(propertiesReader));

        int port = Integer.parseInt(propertiesReader.getProperty(SERVER_PORT));
        boolean isDaemon = Boolean.parseBoolean(propertiesReader.getProperty(SERVER_DAEMON));

        Server server = new Server(port);

        PlaytestServlet playtestServlet = injector.getInstance(PlaytestServlet.class);
        ResourceConfig resourceConfig = new ResourceConfig().registerInstances(playtestServlet);

        ServletContextHandler context = new ServletContextHandler();
        server.setHandler(context);

        ServletHolder servletHolder = new ServletHolder(new ServletContainer(resourceConfig));
        context.addServlet(servletHolder, "/*");

        server.start();
        server.dumpStdErr();
        LOGGER.info(STARTED_APPLICATION_FLAG);

        if (!isDaemon) {
            server.join();
        }
    }

    public static void main(String[] args) throws Exception {
        String propertyFile = args.length == 0 ? DEFAULT_PROPERTY_FILE : args[0];
        new Startup().start(propertyFile);
    }

}
