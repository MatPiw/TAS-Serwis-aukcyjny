package pl.edu.amu.rest;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;

import pl.edu.amu.rest.database.MysqlDB;
import pl.edu.amu.rest.exception.OffersUpdateNotFoundException;
import pl.edu.amu.rest.exception.OfferNotFoundException;
import pl.edu.amu.rest.exception.SellersOffersNotFoundException;
import pl.edu.amu.rest.exception.UserNotFoundException;
import pl.edu.amu.rest.model.Offer;
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

import java.util.Collection;
import java.util.HashMap;


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
    @pl.edu.amu.rest.constraint.Offer
    @Valid
    public Offer getOffer(@NotBlank(message = "{getOffer.offerId.empty}") @Pattern(regexp = "\\d+", message = "{offerId.notDigit}") @PathParam("offerId") String offerId) throws Exception{
        Offer offer = getDatabase().getOffer(offerId);

        if (offer == null)
            throw new OfferNotFoundException("Offer with this id was not found",OfferResource.class);
        else
            return offer;

    }


    @ApiOperation(value = "Get offers collection with filters", notes = "Get offers collection with filters", response = Offer.class, responseContainer = "LIST")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    //@Valid Wyłączone, bo są nieprawidłowe dane w bazie
    public Collection<Offer> getOffers(@QueryParam("owner_id")  Long owner_id, @QueryParam("buyer_id") Long buyer_id, @QueryParam("category") String category) throws Exception {
        String owner_idString = (owner_id == null) ? null : Long.toString(owner_id);
        String buyer_idString = (buyer_id == null) ? null : Long.toString(buyer_id);
        Collection<Offer> result = getDatabase().getOffersWithFilters(owner_idString, buyer_idString, category);
        if (result.size() != 0) {

            return result;
        } else {
            throw new OfferNotFoundException("No offer matching the search criteria",OfferResource.class);
        }

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create offer", notes = "Create offer", response = Offer.class)
    public Response createOffer(@NotNull(message = "{create.offer.empty}") @pl.edu.amu.rest.constraint.Offer @Valid final Offer offer) throws Exception {

        if (getDatabase().getUser(offer.getOwner_id().toString()) != null) {

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
            Offer createdOffer = getDatabase().saveOffer(dbOffer);

            return Response.created(URI.create(uriInfo.getPath() + "/" + createdOffer.getId())).entity(createdOffer).status(Response.Status.CREATED).build();

        } else {

            throw new UserNotFoundException("This owner doesn't exist", OfferResource.class);
        }


    }

    @Path("/{offerId}")
    @PUT
    @ApiOperation(value = "Update offer", notes = "Update offer", response = Offer.class)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @NotNull
    public Response updateOffer(@NotBlank(message = "{updateOffer.offerId.empty}") @Pattern(regexp = "\\d+", message = "{offerId.notDigit}") @PathParam("offerId") String offerId, @NotNull(message = "{update.offer.empty}") @pl.edu.amu.rest.constraint.Offer @Valid Offer offer) throws Exception {
        Boolean notFoundOfferId = (getDatabase().getOffer(offerId) == null) ? true : false;
        Boolean notFoundOwnerId = (offer.getOwner_id() == null || getDatabase().getUser(offer.getOwner_id().toString()) == null) ? true : false;
        if (!notFoundOfferId && !notFoundOwnerId) {
            Offer dbOffer = new Offer(
                    offer.getTitle(),
                    offer.getDescription(),
                    offer.getPicture_path(),
                    offer.getOwner_id(),
                    offer.getPrices(),
                    offer.getActive(),
                    offer.getFinishedAt(),
                    offer.getBuyer_id(),
                    offer.getWeight(),
                    offer.getSize(),
                    offer.getShipment(),
                    offer.getCategory()
            );
            Offer updatedOffer = getDatabase().updateOffer(offerId, dbOffer);

            return Response.ok().entity(updatedOffer).encoding("UTF-8").build();


        } else {
            HashMap<String, Boolean> errors = new HashMap<>();
            errors.put("notFoundOfferId", notFoundOfferId);
            errors.put("notFoundOwnerId", notFoundOwnerId);

            throw new OffersUpdateNotFoundException("There is problem with finding owner's id and/or offer_id", errors);
        }
    }

    @Path("/{offerId}")
    @DELETE
    @ApiOperation(value = "Delete offer", notes = "Delete offer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOffer(@NotBlank(message = "{deleteOffer.offerId.empty}") @Pattern(regexp = "\\d+", message = "{offerId.notDigit}") @PathParam("offerId") String offerId)throws Exception {
        Boolean success = getDatabase().deleteOffer(offerId);



        if (success)
            if (getDatabase().deleteBidFromAuction(offerId))
                return Response.status(Response.Status.NO_CONTENT).entity(null).encoding("UTF-8").type(MediaType.APPLICATION_JSON).build();
            else
                throw  new InternalServerErrorException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorInfo("Deleting bids from deleted auction failed", "Nie udało się usunąć wszystkich stawek powiązanych z usuwaną ofertami", 1019)).encoding("UTF-8").type(MediaType.APPLICATION_JSON).build());

        else
            throw new NotFoundException("offer not found");
    }

    @DELETE
    @ApiOperation(value = "Delete offer", notes = "Delete offer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOffersByOwnerId(@NotBlank(message = "{deleteOwnersOffers.offerId.empty}") @Pattern(regexp = "\\d+", message = "{offerId.notDigit}") @QueryParam("owner_id") Long owner_id)throws Exception {
        if (getDatabase().getUser(Long.toString(owner_id)) == null) {
            throw new UserNotFoundException("That owner doesn't exists, so he couldn't be deleted", OfferResource.class);
        } else if (getDatabase().getOffersByOwner(Long.toString(owner_id)).size() == 0) {
            throw new SellersOffersNotFoundException("Sorry, but this user doesn't have any offers");
        } else {
            Boolean deleteOffersSuccess = getDatabase().deleteOffersByOwnerId(Long.toString(owner_id));
            Collection<Offer> offerList=getDatabase().getOffersByOwner(Long.toString(owner_id));
            for (Offer offer:offerList){
                if (!getDatabase().deleteBidFromAuction(offer.getId())){
                    throw  new InternalServerErrorException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorInfo("Deleting bids from deleted auctions failed", "Nie udało się usunąć wszystkich stawek powiązanych ze wszystkimi usuwanym ofertami", 1019)).encoding("UTF-8").type(MediaType.APPLICATION_JSON).build());
                }
            }
            if (deleteOffersSuccess)
                return Response.status(Response.Status.NO_CONTENT).entity(null).encoding("UTF-8").type(MediaType.APPLICATION_JSON).build();
            else
                throw  new InternalServerErrorException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorInfo("Delete offer operation failed", "Nie udało się usunąć wszystkich ofert powiązanych z tym użytkownikiem", 1023)).encoding("UTF-8").type(MediaType.APPLICATION_JSON).build());
                //return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorInfo("Delete operation failed", "Nie udało się usunąć wszystkich ofert powiązanych z tym użytkownikiem", 1023)).encoding("UTF-8").type(MediaType.APPLICATION_JSON).build();
        }
    }

}

