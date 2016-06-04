package servlet;

import com.google.inject.Inject;
import db.CounterDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class PlaytestServlet {

    private final CounterDAO counterDAO;

    @Inject
    public PlaytestServlet(CounterDAO counterDAO) {
        this.counterDAO = counterDAO;
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

    //  curl -v -X PUT http://localhost:8080/counter/reset
    @Path("/counter/increment")
    @PUT
    public int incrementCounter() {
        counterDAO.increment();
        return counterDAO.get();
    }
}
