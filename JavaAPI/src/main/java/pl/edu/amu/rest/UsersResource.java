package pl.edu.amu.rest;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import pl.edu.amu.rest.database.MysqlDB;
import pl.edu.amu.rest.exception.UserConflictException;
import pl.edu.amu.rest.exception.UserNotFoundException;
import pl.edu.amu.rest.model.User;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collection;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/users", description = "Operations about users using mysql")
public class UsersResource {
    private static final MysqlDB database = new MysqlDB();
    protected MysqlDB getDatabase() {
        return database;
    }

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get users collection", notes = "Get users collection", response = User.class, responseContainer = "LIST")
    //@Valid Wyłączony, bo w bazie jest za dużo nieprawidłowych danych
    public Collection<User> list() {
        return getDatabase().getUsers();
    }


    @Path("/{userId}")
    @ApiOperation(value = "Get user by id", notes = "[note]Get user by id", response = User.class)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Valid
    public User getUser(@NotBlank(message = "{getUser.userId.empty}") @PathParam("userId") String userId) throws Exception {
        User user = getDatabase().getUser(userId);


        if (user == null) {

            throw new UserNotFoundException("User with this id doesn't exist",UsersResource.class);
            //throw new UserException("User not found", "Użytkownik nie został znaleziony", "http://docu.pl/errors/user-not-found");
        }

        return user;
    }

    @POST
    @ApiOperation(value = "Create user", notes = "Create user", response = User.class)
    public Response createUser(@NotNull(message = "{createUser.user.empty}") @Valid final User user) {

        if (getDatabase().getUserbyLogin(user.getLogin())!=null){
            throw new UserConflictException("Error, users duplication! Insert new login");
        }
        else {
            User dbUser = new User(
                    user.getLogin(),
                    user.getHashPassword(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getCity(),
                    user.getAddress(),
                    user.getPhone(),
                    user.getZipCode()
            );
            User createdUser = getDatabase().saveUser(dbUser);
            return Response.created(URI.create(uriInfo.getPath() + "/" + createdUser.getId())).entity(createdUser).status(Response.Status.CREATED).build();
        }



    }

    @Path("/{userId}")
    @PUT
    @ApiOperation(value = "Update user", notes = "Create user", response = User.class)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @NotNull
    public Response updateUser(@NotBlank(message = "{updateUser.userId.empty}") @PathParam("userId") String userId,@NotNull @Valid User user) {
        if (getDatabase().getUser(userId)==null){
            System.out.println("Nie znaleziono takiego użytkownika");
            throw new UserNotFoundException("Sorry, but user with this id was not found, so he couldn't be updated", UsersResource.class);
        }
        else {
            User dbUser = new User(
                    user.getLogin(),
                    user.getHashPassword(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getPermissions(),
                    user.getEmail(),
                    user.getCity(),
                    user.getAddress(),
                    user.getPhone(),
                    user.getZipCode(),
                    user.getConfirmed()
            );

            User updatedUser = getDatabase().updateUser(userId, dbUser);

            return Response.ok().entity(updatedUser).encoding("UTF-8").build();
        }

    }




    @Path("/{userId}")
    @DELETE
    @ApiOperation(value = "Delete user", notes = "Delete user from database", response = User.class)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@NotBlank(message = "{deleteUser.userId.empty}") @PathParam("userId") String userId){
        Boolean success= getDatabase().deleteUser(userId);
        if (success)
            return Response.status(Response.Status.NO_CONTENT).entity(null).encoding("UTF-8").type(MediaType.APPLICATION_JSON).build();
        else
            throw new NotFoundException("user not found");

    }
}
