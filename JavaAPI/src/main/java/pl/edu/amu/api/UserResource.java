package pl.edu.amu.api;

import pl.edu.amu.api.dto.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by adrian.perek on 2015-20-10.
 */
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface UserResource {

    @GET
    public List<User> getUsers();

    @POST
    public User saveUser(User user);


    @GET
    @Path("/{login}")
    public User getUser(@PathParam("login") final String login);

}
