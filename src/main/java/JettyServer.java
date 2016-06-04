import com.google.inject.Inject;
import conf.PropertiesReader;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class JettyServer {

    private static final String SERVER_PORT = "serverPort";

    private final PropertiesReader propertiesReader;
    private Server server;
    private ServletContextHandler context;

    @Inject
    public JettyServer(PropertiesReader propertiesReader) {
        this.propertiesReader = propertiesReader;
    }

    public void start() throws Exception {
        int port = propertiesReader.getIntProperty(SERVER_PORT);

        server = new Server(port);
        context = new ServletContextHandler();
        server.setHandler(context);

        server.start();
    }

    public void registerServlet(Object... servlets) {
        ResourceConfig resourceConfig = new ResourceConfig().registerInstances(servlets);
        ServletHolder servletHolder = new ServletHolder(new ServletContainer(resourceConfig));
        context.addServlet(servletHolder, "/*");
    }

    public void join() throws InterruptedException {
        server.join();
    }
}
