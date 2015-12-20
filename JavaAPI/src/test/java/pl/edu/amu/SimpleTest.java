package pl.edu.amu;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import pl.edu.amu.rest.UserResource;
import pl.edu.amu.rest.model.User;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class SimpleTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig( UserResource.class);
    }
 
    /*@Test
    public void shouldSayHallo() {
        final String hello = target("hello").path("/Mr. White").request().get(String.class);
        assertEquals("hello: Mr. White", hello);
    }*/

    @Test
    public void shouldSaveUser() {
        final Response response = target("users").request().post(Entity.json(new User("Mr. White", "chemik")));
        assertEquals(response.getStatus(), 200);
    }

    @Test
    public void shouldReturn404() {
        final Response response = target("users").path("/12345").request().get();
        assertEquals(response.getStatus(), 404);
    }

    @Test
    public void shouldReturnUser() {
        //given
        target("users").request().post(Entity.json(new User("Mr. White", "usr123")));
        
        //when
        final User user = target("users").path("/usr123").request().get(User.class);
        
        //then
        assertEquals(user.getFirstName(), "Mr. White");
    }
}