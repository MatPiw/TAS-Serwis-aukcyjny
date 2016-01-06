package pl.edu.amu.rest;


import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import org.hibernate.validator.constraints.NotBlank;
import pl.edu.amu.rest.database.MysqlDB;
import pl.edu.amu.rest.exception.BidConflictException;
import pl.edu.amu.rest.exception.BidNotFoundException;
import pl.edu.amu.rest.exception.OfferNotFoundException;
import pl.edu.amu.rest.exception.UserNotFoundException;
import pl.edu.amu.rest.model.Bid;
import pl.edu.amu.rest.model.User;
import pl.edu.amu.rest.model.error.ErrorInfo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.math.BigDecimal;
import java.net.URI;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Path("/bids")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/bids", description = "Operations about bids using mysql")
public class BidResource {

    private static final MysqlDB database = new MysqlDB();

    protected MysqlDB getDatabase() {
        return database;
    }

    @Context
    private UriInfo uriInfo;

    @Path("/{bidId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get bid by id", notes = "[note]Get bid by id", response = Bid.class)
    public Bid getBid(@NotBlank(message = "{getBid.bidId.empty}") @Pattern(regexp = "\\d+", message = "{bidId.notDigit}") @PathParam("bidId") String bidId) throws Exception {
        Bid bid = getDatabase().getBid(bidId);
        if (bid == null) {
            throw new BidNotFoundException("Bid with this id was not found");
        } else {
            return bid;
        }
    }

    @GET
    @ApiOperation(value = "Get bids collection with filter", notes = "[note]Get bids collection with filters", response = Bid.class)
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Bid> getBidsUsingFilters(@QueryParam("bidderId") Long bidderId, @QueryParam("offerId") Long offerId) throws Exception {
        String bidderIdFilter = (bidderId == null) ? null : Long.toString(bidderId);
        String offerIdFilter = (offerId == null) ? null : Long.toString(offerId);

        Collection<Bid> result = getDatabase().getBidWithFilters(bidderIdFilter, offerIdFilter);

        if (result.size() != 0) {
            return result;
        } else {
            throw new BidNotFoundException("No bid matching search criteria");
        }
    }

    @POST
    @ApiOperation(value = "Create new bid", notes = "[note] Create new bid to auction", response = Bid.class)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBid(@NotNull(message = "{createBid.bid.empty}") Bid bid) throws Exception {
        User user = getDatabase().getUser(bid.getBidderId());
        pl.edu.amu.rest.model.Offer offer = getDatabase().getOffer(bid.getOfferId());

        if (offer != null && user != null) {
            int isOwnerBidder = (offer.getOwner_id()).compareTo(bid.getBidderId());
            Boolean isOfferActive = offer.getActive();
            Timestamp isOfferActual = offer.getFinishedAt();
            BigDecimal actualBestPrice = offer.getPrices().getBest_price();
            if (isOfferActive && isOfferActual.compareTo(new Timestamp(new Date().getTime())) > 0) {

                if (isOwnerBidder != 0 && (actualBestPrice == null || bid.getPrice().compareTo(actualBestPrice) == 1)) {
                    Bid dbBid = new Bid(
                            bid.getOfferId(),
                            bid.getBidderId(),
                            bid.getPrice()
                    );
                    Bid createdBid = getDatabase().saveBid(dbBid);
                    offer.getPrices().setBest_price(bid.getPrice());
                    offer.setBuyer_id(bid.getBidderId());
                    getDatabase().updateOffer(offer.getId(), offer);

                    if (createdBid != null)
                        return Response.created(URI.create(uriInfo.getPath() + "/" + createdBid.getId())).entity(createdBid).status(Response.Status.CREATED).encoding("UTF-8").build();
                    else
                        throw new InternalServerErrorException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorInfo("the creation of new bid failed", "Nie udało się stworzyć nowej stawki do podanej oferty", 1020)).encoding("UTF-8").type(MediaType.APPLICATION_JSON).build());


                }
                throw new BidConflictException("The price, which was proposed is lower than actual best price or you cab't raise the ante if bidder is auction owner");
            }
            throw new BidConflictException("This offer is not active or it was finished");
        }
        if (user == null)
            throw new UserNotFoundException("Bidder was not found", BidResource.class);
        else
            throw new OfferNotFoundException("Bid is incorrect, because offer was not found", BidResource.class);


    }

    @Path("/{bidId}")
    @DELETE
    @ApiOperation(value = "Delete bid", notes = "[note] Delete bid using id")
    public Response deleteBid(@NotBlank(message = "{deleteBid.bidId.empty}") @Pattern(regexp = "\\d+", message = "{deleteBid.bidId.wrong}") @PathParam("bidId") String bidId) throws Exception {
        Boolean success = getDatabase().deleteBid(bidId);
        if (!success)
            throw new BidNotFoundException("This bid was not found, so it couldn't be deleted");
        else
            return Response.status(Response.Status.NO_CONTENT).entity(null).encoding("UTF-8").type(MediaType.APPLICATION_JSON).build();
    }

    /*@DELETE
    @ApiOperation(value = "Delete bid using filters", notes = "[note]Delete bid using filters")
    public Response*/


}

