package api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import conf.PropertiesReader;
import db.ConnectionProvider;
import db.CounterDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class PlayApi {

    private final CounterDAO counterDAO;

    public PlayApi() throws IllegalAccessException, InstantiationException, ClassNotFoundException, IOException {
        counterDAO = new CounterDAO(new ConnectionProvider(new PropertiesReader()));
    }

    @GET
    public String ping() {
        return "server is up!";
    }

    @Path("/counter")
    @GET
    public int getCounter() {
        return counterDAO.get();
    }

    //  curl -v -X PUT http://localhost:8080/counter/12
    @Path("/counter/{newIdx : \\d+}")
    @PUT
    public void updateCounter(@PathParam("newIdx") int newIdx) {
        counterDAO.update(newIdx);
    }

    //  curl -v -X PUT http://localhost:8080/counter/reset
    @Path("/counter/reset")
    @PUT
    public void resetCounter() {
        counterDAO.reset();
    }
}
