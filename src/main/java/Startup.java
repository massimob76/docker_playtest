import conf.PropertiesReader;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class Startup {

    private static final String DEFAULT_PROPERTY_FILE = "config.properties";
    private static final String SERVER_PORT = "serverPort";
    private static final String SERVER_DAEMON = "serverDaemon";

    public void start(String propertyFile) throws Exception {
        PropertiesReader propertiesReader = new PropertiesReader(propertyFile);
        int port = Integer.parseInt(propertiesReader.getProperty(SERVER_PORT));
        boolean isDaemon = Boolean.parseBoolean(propertiesReader.getProperty(SERVER_DAEMON));

        Server server = new Server(port);

        ServletContextHandler context = new ServletContextHandler();
        server.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
        jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "servlet");

        server.start();
        server.dumpStdErr();
        if (!isDaemon) {
            server.join();
        }
    }

    public void stop() {

    }

    public static void main(String[] args) throws Exception {
        String propertyFile = args.length == 0 ? DEFAULT_PROPERTY_FILE : args[0];
        new Startup().start(propertyFile);
    }

}
