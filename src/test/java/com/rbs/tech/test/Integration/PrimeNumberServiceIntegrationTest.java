package com.rbs.tech.test.Integration;

import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;

import javax.ws.rs.core.MultivaluedMap;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;


public class PrimeNumberServiceIntegrationTest extends JerseyTest {

    @Override
    protected AppDescriptor configure() {
        return new WebAppDescriptor.Builder("com.rbs.tech.test")
                .contextParam("contextConfigLocation", "classpath:/spring-config.xml")
                .contextPath("/primes-tech-test").servletClass(SpringServlet.class)
                .contextListenerClass(ContextLoaderListener.class)
                .requestListenerClass(RequestContextListener.class)
                .build();
    }

    @Test
    public void primesServiceWithNoParamShouldGetPrimeNumberList() throws JSONException,
            URISyntaxException {
        WebResource webResource = resource();
        JSONObject json = webResource.path("/primes/10")
                .get(JSONObject.class);

        JSONArray primesList = (JSONArray) json.get("primes");

        assertEquals("10", json.get("initial"));
        assertEquals(2, primesList.getInt(0));
        assertEquals(3, primesList.getInt(1));
        assertEquals(5, primesList.getInt(2));
        assertEquals(7, primesList.getInt(3));
    }

    @Test
    public void primesServiceSequentialShouldGetPrimeNumberList() throws JSONException,
            URISyntaxException {
        WebResource webResource = resource();

        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add("executionMode", "sequential");

        JSONObject json = webResource.path("/primes/13")
                .queryParams(params)
                .get(JSONObject.class);

        JSONArray primesList = (JSONArray) json.get("primes");

        assertEquals("13", json.get("initial"));
        assertEquals(2, primesList.getInt(0));
        assertEquals(3, primesList.getInt(1));
        assertEquals(5, primesList.getInt(2));
        assertEquals(7, primesList.getInt(3));
        assertEquals(11, primesList.getInt(4));
        assertEquals(13, primesList.getInt(5));
    }

    @Test
    public void primesServiceParallelShouldGetPrimeNumberList() throws JSONException,
            URISyntaxException {
        WebResource webResource = resource();

        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add("executionMode", "parallel");

        JSONObject json = webResource.path("/primes/15")
                .queryParams(params)
                .get(JSONObject.class);

        JSONArray primesList = (JSONArray) json.get("primes");

        assertEquals("15", json.get("initial"));
        assertEquals(2, primesList.getInt(0));
        assertEquals(3, primesList.getInt(1));
        assertEquals(5, primesList.getInt(2));
        assertEquals(7, primesList.getInt(3));
        assertEquals(11, primesList.getInt(4));
        assertEquals(13, primesList.getInt(5));
    }


    @Test(expected = UniformInterfaceException.class)
    public void primesServiceShouldThrowException() {
        WebResource webResource = resource();
        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add("executionMode", "badParam");

        webResource.path("/primes/15")
                .queryParams(params)
                .get(JSONObject.class);
    }

    @Test
    public void blockingPrimesServiceShouldGetPrimeNumberList() throws JSONException,
            URISyntaxException {
        WebResource webResource = resource();
        JSONObject json = webResource.path("/blockingPrimes/13")
                .get(JSONObject.class);
        JSONArray primesList = (JSONArray) json.get("primes");

        assertEquals("13", json.get("initial"));
        assertEquals(2, primesList.getInt(0));
        assertEquals(3, primesList.getInt(1));
        assertEquals(5, primesList.getInt(2));
        assertEquals(7, primesList.getInt(3));
        assertEquals(11, primesList.getInt(4));
        assertEquals(13, primesList.getInt(5));
    }

    @Test
    public void nonBlockingPrimesServiceShouldGetPrimeNumberList() throws JSONException,
            URISyntaxException {
        WebResource webResource = resource();
        JSONObject json = webResource.path("/nonBlockingPrimes/15")
                .get(JSONObject.class);
        JSONArray primesList = (JSONArray) json.get("primes");

        assertEquals("15", json.get("initial"));
        assertEquals(2, primesList.getInt(0));
        assertEquals(3, primesList.getInt(1));
        assertEquals(5, primesList.getInt(2));
        assertEquals(7, primesList.getInt(3));
        assertEquals(11, primesList.getInt(4));
        assertEquals(13, primesList.getInt(5));
    }


}