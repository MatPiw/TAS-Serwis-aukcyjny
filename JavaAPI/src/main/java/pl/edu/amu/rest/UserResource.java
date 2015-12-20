package pl.edu.amu.rest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.amu.repository.UserRepository;
import pl.edu.amu.rest.model.ErrorResponse;
import pl.edu.amu.rest.model.User;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)


public class UserResource {
    
    private static final UserRepository userRep = new UserRepository();
    private static Logger LOG = LoggerFactory.getLogger(UserResource.class);

    //@Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers(){
        LOG.info("/get/users");
        return userRep.getUsers();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveUser(User user){
        if (userRep.findByLogin(user.getLogin()) == null){
            LOG.info("/post/users");

            User temp =  userRep.save(user);
            return Response.ok(temp).build();
        }
        return Response.status(Response.Status.CONFLICT)
                .entity(new ErrorResponse(Response.Status.CONFLICT, "That login is already taken"))
                .build();
    }

    @GET
    @Path("/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("login") final String login){
        LOG.info("/get/users/{}", login);
        User user = userRep.findByLogin(login);
        if (user == null) {
            throw new NotFoundException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(Response.Status.NOT_FOUND, "User not found"))
                    .build());
        }
        return user;
    }

    @PUT
    @Path("/{login}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("login") final String login, User user){
        if (userRep.findByLogin(login) == null){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(Response.Status.NOT_FOUND, "User not found"))
                    .build();

        } else {
            LOG.info("/put/users");
            User temp =  userRep.update(user);
            return Response.ok(temp).build();
        }

    }


}