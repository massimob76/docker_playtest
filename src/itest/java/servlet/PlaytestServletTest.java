package servlet;

import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;

import static org.glassfish.jersey.client.ClientProperties.SUPPRESS_HTTP_COMPLIANCE_VALIDATION;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PlaytestServletTest {

    private static final String URI = "http://localhost:8090";

    @Test
    public void serverIsUpTest() {
        assertThat(call("GET", "/", String.class), is("server is up!"));
    }

    @Test
    public void getUpdateAndResetCounterTest() {
        call("PUT", "/counter/reset");

        Integer response = call("GET", "/counter", Integer.class);
        assertThat(response, is(0));

        call("PUT", "/counter/12");

        response = call("GET", "/counter", Integer.class);
        assertThat(response, is(12));

        call("PUT", "/counter/reset");

        response = call("GET", "/counter", Integer.class);
        assertThat(response, is(0));
    }

    @Test
    public void incrementCounterTest() {
        call("PUT", "/counter/reset");

        Integer response = call("GET", "/counter", Integer.class);
        assertThat(response, is(0));

        call("PUT", "/counter/increment");

        response = call("GET", "/counter", Integer.class);
        assertThat(response, is(1));
    }

    private void call(String method, String path) {
        call(method, path, null);
    }

    private <T> T call(String method, String path, Class<T> responseType) {
        Invocation.Builder builder = ClientBuilder.newClient()
                .property(SUPPRESS_HTTP_COMPLIANCE_VALIDATION, true)
                .target(URI)
                .path(path)
                .request();

        switch(method) {
            case "GET":
                if (responseType == null) {
                    builder.get();
                    return null;
                } else {
                    return builder.get(responseType);
                }
            case "PUT":
                if (responseType == null) {
                    builder.put(null);
                    return null;
                } else {
                    return builder.put(null, responseType);
                }
            default:
                throw new IllegalArgumentException("Unsupported method: " + method);
        }
    }

}
