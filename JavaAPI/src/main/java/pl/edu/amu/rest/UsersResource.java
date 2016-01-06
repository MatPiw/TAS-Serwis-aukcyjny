package pl.edu.amu.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import pl.edu.amu.rest.database.MysqlDB;
import pl.edu.amu.rest.exception.UserConflictException;
import pl.edu.amu.rest.exception.UserNotFoundException;
import pl.edu.amu.rest.model.User;
import pl.edu.amu.rest.model.error.ErrorInfo;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/users", description = "Operations about users using mysql")
public class UsersResource {
    private static final MysqlDB database = new MysqlDB();

    protected MysqlDB getDatabase() {
        return database;
    }
    private boolean isNumeric(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }
    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get users collection", notes = "Get users collection", response = User.class, responseContainer = "LIST")
    //@Valid Wyłączony, bo w bazie jest za dużo nieprawidłowych danych
    public Collection<User> getUsers() {

        Collection<User> final_result=new ArrayList<User>();
        Collection<User> result=getDatabase().getUsers();
        for (User user: result){
            user.setUserOffers((List)getDatabase().getOffersByOwner(user.getId()));
            user.setUserComments((List)getDatabase().getCommentsByUser(user.getId()));

            final_result.add(user);

        }
        return final_result;


    }
    @Path("/{userId}")
    @ApiOperation(value = "Get user by id", notes = "[note]Get user by id", response = User.class)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Valid
    public User getUser(@NotBlank(message = "{getUser.userId.empty}") @Pattern(regexp = "(\\d|[a-zA-Z0-9ęóąśłżźćńĘÓĄŚŁŻŹĆŃ])+", message = "{userId.notDigitOrLogin}") @PathParam("userId") String userUniqueIdentificator) throws Exception {
        User user;
        if (isNumeric(userUniqueIdentificator)) {
            user = getDatabase().getUser(userUniqueIdentificator);
        } else {
            user = getDatabase().getUserbyLogin(userUniqueIdentificator);
        }


        if (user == null) {

            throw new UserNotFoundException("User with this id doesn't exist", UsersResource.class);
            //throw new UserException("User not found", "Użytkownik nie został znaleziony", "http://docu.pl/errors/user-not-found");
        }
        user.setUserOffers((List) getDatabase().getOffersByOwner(user.getId()));
        return user;
    }
    /*@Path("/{userId}")
    @ApiOperation(value = "Get user by id", notes = "[note]Get user by id", response = User.class)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Valid
    // w argumentach było jeszcze
    public User getUser(@NotBlank(message = "{getUser.userId.empty}") @Pattern(regexp = "\\d+", message = "{userId.notDigit}") @PathParam("userId") String userId) throws Exception {


        User user = getDatabase().getUser(userId);


        if (user == null) {

            throw new UserNotFoundException("User with this id doesn't exist", UsersResource.class);
            //throw new UserException("User not found", "Użytkownik nie został znaleziony", "http://docu.pl/errors/user-not-found");
        }
        user.setUserOffers((List) getDatabase().getOffersByOwner(userId));
        return user;
    }
*/
    @POST
    @ApiOperation(value = "Create user", notes = "Create user", response = User.class)
    public Response createUser(@NotNull(message = "{createUser.user.empty}") @Valid final User user) throws Exception {

        if (getDatabase().getUserbyLogin(user.getLogin()) != null) {
            throw new UserConflictException("Error, users duplication! Insert new login");
        } else {
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
    public Response updateUser(@NotBlank(message = "{updateUser.userId.empty}") @Pattern(regexp = "\\d+", message = "{userId.notDigit}") @PathParam("userId") String userId, @NotNull(message = "{updateUser.user.empty}") @Valid User user) throws Exception{
        if (getDatabase().getUser(userId) == null) {
            throw new UserNotFoundException("Sorry, but user with this id was not found, so he couldn't be updated", UsersResource.class);
        } else {
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
            if (updatedUser==null){
                throw  new InternalServerErrorException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorInfo("User Update failed", "Nie udało się zaktualizować danych tego użytkownika", 1021)).encoding("UTF-8").type(MediaType.APPLICATION_JSON).build());
            }
            updatedUser.setUserOffers((List) getDatabase().getOffersByOwner(userId));
            updatedUser.setUserComments((List)getDatabase().getCommentsByUser(userId));

            return Response.ok().entity(updatedUser).encoding("UTF-8").build();
        }

    }


    @Path("/{userId}")
    @DELETE
    @ApiOperation(value = "Delete user", notes = "Delete user from database", response = User.class)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@NotBlank(message = "{deleteUser.userId.empty}") @Pattern(regexp = "\\d+", message = "{userId.notDigit}") @PathParam("userId") String userId) throws Exception {
        Boolean success = getDatabase().deleteUser(userId);
        if (!success)
            throw new NotFoundException("user not found");
        else if (getDatabase().getOffersByOwner(userId).size() != 0) {
            if (!getDatabase().deleteOffersByOwnerId(userId)){
                throw  new InternalServerErrorException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorInfo("Delete user operation failed", "Nie udało się usunąć tego użytkownika", 1024)).encoding("UTF-8").type(MediaType.APPLICATION_JSON).build());

            } else {
                getDatabase().deleteCommentsFromUser(userId);
                getDatabase().deleteBidFromUser(userId);
            }
        }
        return Response.status(Response.Status.NO_CONTENT).entity(null).encoding("UTF-8").type(MediaType.APPLICATION_JSON).build();


    }
}
