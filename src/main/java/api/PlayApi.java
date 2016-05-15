package api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/ping")
public class PlayApi {

    @GET
    public String ping() {
        return "pong";
    }
}
