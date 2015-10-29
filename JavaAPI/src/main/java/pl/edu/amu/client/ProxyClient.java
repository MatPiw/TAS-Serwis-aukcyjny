package pl.edu.amu.client;

import org.glassfish.jersey.client.proxy.WebResourceFactory;
import pl.edu.amu.api.HelloResource;
import pl.edu.amu.api.UserResource;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import pl.edu.amu.api.dto.User;

/**
 * Created by adrian.perek on 2015-20-10.
 */
public class ProxyClient {
    public static void main(String[] args) {
        WebTarget target = ClientBuilder.newClient().target("http://localhost:8080");
        HelloResource helloResource = WebResourceFactory.newResource(HelloResource.class, target);
        UserResource userResource = WebResourceFactory.newResource(UserResource.class, target);
        
        System.out.println(helloResource.getMsg("Mr. White"));

        System.out.println(userResource.saveUser(new User("Mr. White", "white1960")));

        System.out.println(userResource.getUsers());
    }
}