package api;

import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PlayApiTest {

    private static final String URI = "http://localhost:8090";

    @Test
    public void serverIsUpTest() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(URI).path("/");
        Response response = target.request().get();
        assertThat(response.getStatus(), is(200));
        assertThat(response.readEntity(String.class), is("server is up!"));
    }

}
