package pl.edu.amu.client;

import pl.edu.amu.api.dto.User;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class RestClient {

    public static void main(String[] args) {
        WebTarget target = ClientBuilder.newClient().target("http://localhost:8080");

        //Zadanie 1
        String responseData = target.path("hello/Mr. White").request().get(String.class);
        System.out.println(responseData);
        
        //Zadanie 4
        Response saveUserResponse = target.path("users").request().post(Entity.json(new User("Mr. hue", "huecov")));
        System.out.println("Status zapisu usera: " + saveUserResponse.getStatus());
        saveUserResponse.close();

        String usersAsJsonString = target.path("users").request().get(String.class);
        System.out.println("Pobrana lista userow: " + usersAsJsonString);

        User user = target.path("users/chemik").request().get(User.class);
        System.out.println("Pobranie usera 'chemik': " + user);
    }
}
