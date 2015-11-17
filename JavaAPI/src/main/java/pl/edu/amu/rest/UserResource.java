package pl.edu.amu.rest;

import pl.edu.amu.repository.UserRepository;
import pl.edu.amu.rest.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)


public class UserResource {
    
    private static final UserRepository userRep = new UserRepository();

    //@Override
    @GET
    public List<User> getUsers(){
        return userRep.getUsers();
    }


    @POST
    public User saveUser(User user){
        if (userRep.findByLogin(user.getLogin()) == null){
            return userRep.save(user);
        }
        return null;
    }

    @GET
    @Path("/{login}")
    public User getUser(@PathParam("login") final String login){
        User user = userRep.findByLogin(login);
        return user;
   }

}
