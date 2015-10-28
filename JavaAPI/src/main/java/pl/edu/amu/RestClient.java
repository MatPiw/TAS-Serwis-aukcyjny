package pl.edu.amu;

import org.glassfish.jersey.client.ClientConfig;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

public class RestClient {

    public static void main(String[] args) {
        Client client = ClientBuilder.newClient(new ClientConfig());

        String responseData = client.target("http://localhost:8080")
                .path("hello/{name}").resolveTemplate("name", "Mr. White!")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get(String.class);

        System.out.println(responseData);
    }
}