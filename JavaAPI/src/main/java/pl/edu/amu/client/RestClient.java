package pl.edu.amu.client;

import pl.edu.amu.rest.dao.User;
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
        Response saveUserResponse = target.path("users").request()
                .post(Entity.json(new User("huecov", "123v", "Ukasz", "Marcin", "hue@cov.pl", "Poznan", "wichrowe 18",
                        "123456789", "12-345")));
        System.out.println("Status zapisu usera: " + saveUserResponse.getStatus());
        saveUserResponse.close();

        String usersAsJsonString = target.path("users").request().get(String.class);
        System.out.println("Pobrana lista userow: " + usersAsJsonString);

        User user = target.path("users/huecov").request().get(User.class);
        System.out.println("Pobranie usera 'huecov': " + user);
    }
}
