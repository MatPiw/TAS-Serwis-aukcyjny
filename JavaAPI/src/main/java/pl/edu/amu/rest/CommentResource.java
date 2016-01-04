package pl.edu.amu.rest;

import com.sun.el.parser.BooleanNode;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import pl.edu.amu.repository.CommentRepository;
import pl.edu.amu.rest.database.MysqlDB;
import pl.edu.amu.rest.exception.CommentConflictException;
import pl.edu.amu.rest.exception.CommentNotFoundException;
import pl.edu.amu.rest.exception.OfferNotFoundException;
import pl.edu.amu.rest.exception.UserNotFoundException;
import pl.edu.amu.rest.model.Comment;
import pl.edu.amu.rest.model.User;
import scala.util.parsing.combinator.testing.Str;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collection;
import java.util.List;

@Path("/comments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/comments", description = "Operations about comments using mysql")
public class CommentResource {

    private static final MysqlDB database = new MysqlDB();

    protected MysqlDB getDatabase() {
        return database;
    }

    @Context
    private UriInfo uriInfo;

    @Path("/{commentId}")
    @GET
    @ApiOperation(value = "Get comment by id", notes = "[note]Get comment by id", response = Comment.class)
    @Produces(MediaType.APPLICATION_JSON)
    public Comment getComment(@NotBlank(message = "{getComment.commentId.empty}") @Pattern(regexp = "\\d+", message = "{commentId.notDigit}") String commentId) {
        Comment comment = getDatabase().getComment(commentId);

        if (comment == null) {
            throw new CommentNotFoundException("Comment with that id was not found");
        }
        return comment;

    }

    @GET
    @ApiOperation(value = "Get comments collection with filter", notes = "[note]Get comment by id", response = Comment.class)
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Comment> getCommentsWithFilters(@QueryParam("giver_id") Long giverId, @QueryParam("receiver_id") Long receiverId, @QueryParam("offer_id") Long offerId) {
        String giverIdString = Long.toString(giverId);
        String receiverIdString = Long.toString(receiverId);
        String offerIdString = Long.toString(offerId);

        Collection<Comment> result = getDatabase().getCommentsWithFilters(giverIdString, receiverIdString, offerIdString);
        if (result.size() != 0) {
            return result;
        } else {
            throw new CommentNotFoundException("No comment matching search criteria");
        }
    }

    @POST
    @ApiOperation(value = "Create new comment", notes = "[note]Create new comment", response = Comment.class)
    public Response createComment(@NotNull(message = "{createComment.comment.empty}") Comment comment){
        Boolean isGiverIdExist=(getDatabase().getUser(Long.toString(comment.getGiverId()))!=null)?true:false;
        Boolean isReceiverId=(getDatabase().getUser(Long.toString(comment.getRecieverId()))!=null)?true:false;
        Boolean isOfferId=(getDatabase().getOffer(Long.toString(comment.getOfferId()))!=null)?true:false;

        if (!isGiverIdExist){
            throw new  UserNotFoundException("Giver with this ID was not found",CommentResource.class);
        }else if (!isReceiverId){
            throw new UserNotFoundException("Receiver with this ID was not found",CommentResource.class);
        } else if (!isOfferId) {
            throw new OfferNotFoundException("Offer with this ID was not found, so it can't be commented",CommentResource.class);
        }
        else {
            String giverId=Long.toString(comment.getGiverId());
            String offerId=Long.toString(comment.getOfferId());
            if (getDatabase().getCommentsWithFilters(giverId,null,offerId).size()==0){
                Comment dbComment = new Comment(
                    comment.getGiverId(),
                        comment.getRecieverId(),
                        comment.getOfferId(),
                        comment.getCommentText(),
                        comment.isPositive()
                );
                Comment createdComment=getDatabase().saveComment(dbComment);
                return Response.created(URI.create(uriInfo.getPath() + "/" + createdComment.getId())).entity(createdComment).status(Response.Status.CREATED).encoding("UTF-8").build();
            } else{
                throw new CommentConflictException("Sorry, but this offer is commented by this user");
            }
        }



    }

    @Path("/{commentId}")
    @DELETE
    @ApiOperation(value = "Delete comment", notes = "Delete comment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteComment(@NotBlank(message = "{deleteComment.commentId.empty}") @Pattern(regexp = "\\d+", message = "{comment.notDigit}") @PathParam("commentId") String commentId){
        Boolean success=getDatabase().deleteComment(commentId);
        if (!success)
            throw new CommentNotFoundException("Comment with this id was not found in database");
        else
            return Response.status(Response.Status.NO_CONTENT).entity(null).encoding("UTF-8").type(MediaType.APPLICATION_JSON).build();
    }




  /*  @DELETE
    @ApiOperation(value = "Delete comments by userId", notes = "Delete comments using filters")
    public Response deleteCommentsWithFilter(@QueryParam(value = "userId") Long userId, @QueryParam(value = "offerId") Long offerId){
        if ()
    }*/


    /*@Path("/{offerId}")
    @ApiOperation(value = "Get offer by id", notes = "[note]Get offer by id", response = Offer.class)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @pl.edu.amu.rest.constraint.Offer
    @Valid
    public Offer getOffer(@NotBlank(message = "{getOffer.offerId.empty}") @Pattern(regexp = "\\d+", message = "{offerId.notDigit}") @PathParam("offerId") String offerId) {
        Offer offer = getDatabase().getOffer(offerId);

        if (offer == null)
            throw new OfferNotFoundException("Offer with this id was not found");
        else
            return offer;

    }


    @ApiOperation(value = "Get offers collection with filters", notes = "Get offers collection with filters", response = Offer.class, responseContainer = "LIST")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    //@Valid Wyłączone, bo są nieprawidłowe dane w bazie
    public Collection<Offer> getOffers(@QueryParam("owner_id") Long owner_id, @QueryParam("buyer_id") Long buyer_id, @QueryParam("category") String category) {

        String owner_idString = (owner_id == null) ? null : Long.toString(owner_id);
        String buyer_idString = (buyer_id == null) ? null : Long.toString(buyer_id);

        Collection<Offer> result = getDatabase().getOffersWithFilters(owner_idString, buyer_idString, category);
        if (result.size() != 0) {

            return result;
        } else {
            throw new OfferNotFoundException("No offer matching the search criteria");
        }

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create offer", notes = "Create offer", response = Offer.class)
    public Response createOffer(@NotNull(message = "{create.offer.empty}") @pl.edu.amu.rest.constraint.Offer @Valid final Offer offer) {

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
    public Response updateUser(@NotBlank(message = "{updateOffer.string.empty}") @Pattern(regexp = "\\d+", message = "{offerId.notDigit}") @PathParam("offerId") String offerId, @NotNull(message = "{update.offer.empty}") @pl.edu.amu.rest.constraint.Offer @Valid Offer offer) {
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

            throw new OffersUpdateNotFoundException("There is problem with owner's id and/or offer_id", errors);
        }
    }

    @Path("/{offerId}")
    @DELETE
    @ApiOperation(value = "Delete offer", notes = "Delete offer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOffer(@NotBlank(message = "{deleteOffer.offerId.empty}") @Pattern(regexp = "\\d+", message = "{offerId.notDigit}") @PathParam("offerId") String offerId) {
        Boolean success = getDatabase().deleteOffer(offerId);
        if (success)
            return Response.status(Response.Status.NO_CONTENT).entity(null).encoding("UTF-8").type(MediaType.APPLICATION_JSON).build();
        else
            throw new NotFoundException("offer not found");
    }

    @DELETE
    @ApiOperation(value = "Delete offer", notes = "Delete offer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOffersByOwnerId(@NotBlank(message = "{deleteOwnersOffers.offerId.empty}") @Pattern(regexp = "\\d+", message = "{offerId.notDigit}") @QueryParam("owner_id") Long owner_id) {
        if (getDatabase().getUser(Long.toString(owner_id)) == null) {
            throw new UserNotFoundException("That owner doesn't exists, so he couldn't be deleted", OfferResource.class);
        } else if (getDatabase().getOffersByOwner(Long.toString(owner_id)).size() == 0) {
            throw new SellersOffersNotFoundException("Sorry, but this user doesn't have anyy offers");
        } else {
            Boolean success = getDatabase().deleteOffersByOwnerId(Long.toString(owner_id));
            if (success)
                return Response.status(Response.Status.NO_CONTENT).entity(null).encoding("UTF-8").type(MediaType.APPLICATION_JSON).build();
            else
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorInfo("Delete operation failed", "Nie udało się usunąć wszystkich ofert powiązanych z tym użytkownikiem", 1023)).encoding("UTF-8").type(MediaType.APPLICATION_JSON).build();
        }
    }*/

}
