package pl.edu.amu.rest;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.amu.repository.OfferRepository;
import pl.edu.amu.rest.database.MysqlDB;
import pl.edu.amu.rest.exception.NotFoundOfferUpdateException;
import pl.edu.amu.rest.exception.OfferNotFoundException;
import pl.edu.amu.rest.exception.UserNotFoundException;
import pl.edu.amu.rest.model.Offer;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Path("/offers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/offers", description = "Operations about offers using mysql")
public class OfferResource {
    @Context
    private UriInfo uriInfo;

    private static final MysqlDB database = new MysqlDB();
    protected MysqlDB getDatabase() {
        return database;
    }


    //@Override
    /*@GET
    @ApiOperation(value = "Get offers collection", notes = "Get offers collection", response = Offer.class, responseContainer = "LIST")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Offer> getOffers(){
        System.out.println("działam 2");
        return getDatabase().getOffers();
    }*/


    @Path("/{offerId}")
    @ApiOperation(value = "Get offer by id", notes = "[note]Get offer by id", response = Offer.class)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Valid
    public Offer getOffer(@NotBlank(message = "{getOffer.offerId.empty}") @PathParam("offerId") String offerId){
        Offer offer=getDatabase().getOffer(offerId);

        if (offer==null)
            throw new OfferNotFoundException("Offer with this id was not found");
        else
            return offer;

    }



    @ApiOperation(value = "Get offers collection with filters", notes = "Get offers collection with filters", response = Offer.class, responseContainer = "LIST")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Collection<Offer> getOffers(@QueryParam("owner_id") Long owner_id, @QueryParam("buyer_id") Long buyer_id, @QueryParam("category") String category){

        String owner_idString=(owner_id==null)?null:Long.toString(owner_id);
        String buyer_idString=(buyer_id==null)?null:Long.toString(buyer_id);

        Collection<Offer> result=getDatabase().getOffersWithFilters(owner_idString,buyer_idString,category);
        if (result.size()!=0){

            return result;
        }
        else {
            throw  new OfferNotFoundException("No offer matching the search criteria");
        }

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create user", notes = "Create offer", response = Offer.class)
    public Response createOffer(@NotNull(message = "{create.offer.empty}") @Valid final Offer offer) {
        System.out.println(offer.getPrices().toString());

        if (getDatabase().getUser(offer.getOwner_id().toString())!=null){

            Offer dbOffer = new Offer(
                    offer.getTitle(),
                    offer.getPicture_path(),
                    offer.getDescription(),
                    offer.getOwner_id(),
                    offer.getPrices(),
                    offer.getWeight(),
                    offer.getSize(),
                    offer.getShipment(),
                    offer.getCategory()
            );
            Offer createdOffer= getDatabase().saveOffer(dbOffer);

            return Response.created(URI.create(uriInfo.getPath() + "/" +createdOffer.getId())).entity(createdOffer).status(Response.Status.CREATED).build();

        }
        else{

            throw new UserNotFoundException("This owner doesn't exist",OfferResource.class);
        }



    }
    @Path("/{offerId}")
    @PUT
    @ApiOperation(value = "Update offer", notes = "Update offer", response = Offer.class)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @NotNull
    public Response updateUser(@NotBlank(message = "{updateOffer.string.empty}") @PathParam("offerId") String offerId, @NotNull @Valid Offer offer) {
        Boolean notFoundOfferId=(getDatabase().getOffer(offerId)==null)?true:false;
        Boolean notFoundOwnerId=(offer.getOwner_id()==null || getDatabase().getUser(offer.getOwner_id().toString())==null)?true:false;
        if (!notFoundOfferId && !notFoundOwnerId){
            Offer dbOffer=new Offer(
                    offer.getTitle(),
                    offer.getDescription(),
                    offer.getPicture_path(),
                    offer.getOwner_id(),
                    offer.getPrices(),
                    offer.getActive(),
                    offer.getFinished_at(),
                    offer.getBuyer_id(),
                    offer.getWeight(),
                    offer.getSize(),
                    offer.getShipment(),
                    offer.getCategory()
            );
            Offer updatedOffer=getDatabase().updateOffer(offerId,dbOffer);

            return Response.ok().entity(updatedOffer).encoding("UTF-8").build();


        }
        else{
            HashMap<String, Boolean> errors=new HashMap<>();
            errors.put("notFoundOfferId",notFoundOfferId);
            errors.put("notFoundOwnerId",notFoundOwnerId);

            throw new NotFoundOfferUpdateException("There is problem with owner's id and/or offer_id",errors);
        }
    }

    @Path("/{offerId}")
    @DELETE
    @ApiOperation(value = "Delete offer", notes = "Delete offer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOffer(@NotBlank(message = "{deleteOffer.offerId.empty}") @PathParam("offerId") String offerId){
        Boolean success= getDatabase().deleteOffer(offerId);
        if (success)
            return Response.status(Response.Status.NO_CONTENT).entity(null).encoding("UTF-8").type(MediaType.APPLICATION_JSON).build();
        else
            throw new NotFoundException("offer not found");
    }
    /*private static final MysqlDB database = new MysqlDB();
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
    public User getUser(@NotBlank(message = "{getUser.string.empty}") @PathParam("userId") String userId) throws Exception {
        User user = getDatabase().getUser(userId);


        if (user == null) {

            throw new UserNotFoundException("User with this id doesn't exist");
            //throw new UserException("User not found", "Użytkownik nie został znaleziony", "http://docu.pl/errors/user-not-found");
        }

        return user;
    }

    @POST
    @ApiOperation(value = "Create user", notes = "Create user", response = User.class)
    public Response createUser(@NotNull(message = "{create.user.empty}") @Valid final User user) {



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
        if (getDatabase().getUserbyLogin(user.getLogin())==null){
            User createdUser = getDatabase().saveUser(dbUser);
            return Response.created(URI.create(uriInfo.getPath() + "/" + createdUser.getId())).entity(createdUser).status(Response.Status.CREATED).build();
        }
        else {
            throw new UserConflictException("Error, users duplication! Insert new login");
        }


    }

    @Path("/{userId}")
    @PUT
    @ApiOperation(value = "Update user", notes = "Create user", response = User.class)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @NotNull
    public Response updateUser(@NotBlank(message = "{updateUser.string.empty}") @PathParam("userId") String userId,@NotNull @Valid User user) {

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




    @Path("/{userId}")
    @DELETE
    @ApiOperation(value = "Delete user", notes = "Delete user from database", response = User.class)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUSer(@NotBlank(message = "{deleteUser.string.empty}") @PathParam("userId") String userId){
        Boolean success= getDatabase().deleteUser(userId);
        if (success)
            return Response.status(Response.Status.NO_CONTENT).entity(null).encoding("UTF-8").type(MediaType.APPLICATION_JSON).build();
        else
            throw new NotFoundException("user not found");

    }*/
}

