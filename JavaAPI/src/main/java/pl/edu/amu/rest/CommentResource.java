package pl.edu.amu.rest;

import io.swagger.annotations.*;
import org.hibernate.validator.constraints.NotBlank;
import pl.edu.amu.rest.database.MysqlDB;
import pl.edu.amu.rest.exception.CommentConflictException;
import pl.edu.amu.rest.exception.CommentNotFoundException;
import pl.edu.amu.rest.exception.OfferNotFoundException;
import pl.edu.amu.rest.exception.UserNotFoundException;
import pl.edu.amu.rest.model.Comment;
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

@Path("/comments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/Comments", description = "Operations on comments using MySQL database.")
public class CommentResource {

    private static final MysqlDB database = new MysqlDB();

    protected MysqlDB getDatabase() {
        return database;
    }

    @Context
    private UriInfo uriInfo;

    @Path("/{commentId}")
    @GET
    @ApiOperation(value = "Get comment by id.", notes = "Get comment by id.", response = Comment.class)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation successful.", response = Comment.class),
            @ApiResponse(code = 404, message = "Comment not found.", response = ErrorInfo.class),
            @ApiResponse(code  =500, message = "Internal server error.", response = ErrorInfo.class)})


    public Comment getComment(
            @NotBlank(message = "{getComment.commentId.empty}")
            @Pattern(regexp = "\\d+", message = "{commentId.notDigit}")
            @ApiParam(value = "Comment id from database.", required = true) @PathParam("commentId")  String commentId) throws Exception {
        Comment comment = getDatabase().getComment(commentId);

        if (comment == null) {
            throw new CommentNotFoundException("Comment with that id was not found");
        }
        return comment;

    }

    @GET
    @ApiOperation(value = "Get comments collection with filter", notes = "Get all comments.", response = Comment.class)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation successful.", response = Comment.class, responseContainer = "LIST"),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorInfo.class)})
    public Collection<Comment> getCommentsWithFilters(
            @ApiParam(value = "Id of user which posted the comment.", required = false) @QueryParam("giver_id") Long giverId,
            @ApiParam(value = "Id of user which got the comment.", required = false) @QueryParam("receiver_id") Long receiverId,
            @ApiParam(value = "Id of offer which the comment refers to.", required = false) @QueryParam("offer_id") Long offerId)
            throws Exception {
        String giverIdString = (giverId!=null)?Long.toString(giverId):null;
        String receiverIdString = (receiverId!=null)?Long.toString(receiverId):null;
        String offerIdString = (offerId!=null)?Long.toString(offerId):null;

        Collection<Comment> result = getDatabase().getCommentsWithFilters(giverIdString, receiverIdString, offerIdString);
        if (result.size() != 0) {
            return result;
        } else {
            throw new CommentNotFoundException("No comment matching search criteria");
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create new comment", notes = "[note]Create new comment", response = Comment.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Comment created.", response = Comment.class),
            @ApiResponse(code = 400, message = "Bad request.", response =ErrorInfo.class, responseContainer = "LIST"),
            @ApiResponse(code = 409, message =  "Conflict.", response = ErrorInfo.class),
            @ApiResponse(code  =500, message = "Internal server error.", response = ErrorInfo.class)})
    public Response createComment(
            @NotNull(message = "{createComment.comment.empty}")
            @pl.edu.amu.rest.constraint.Comment
            @ApiParam(value = "Comment object to insert into database.", required = true)
            @Valid Comment comment)
            throws Exception{
        Boolean isGiverIdExist = (getDatabase().getUser(comment.getGiverId()) != null) ? true : false;
        Boolean isReceiverId = (getDatabase().getUser(comment.getRecieverId()) != null) ? true : false;
        Boolean isOfferId = (getDatabase().getOffer(comment.getOfferId()) != null) ? true : false;
        /*Boolean temp=(getDatabase().getOffer(comment.getOfferId()).getOwner_id().equals(comment.getRecieverId()))?true:false;
        System.out.println(temp);*/

        if (!isGiverIdExist) {
            throw new UserNotFoundException("Giver with this ID was not found", CommentResource.class);
        } else if (!isReceiverId) {
            throw new UserNotFoundException("Receiver with this ID was not found", CommentResource.class);
        } else if (!isOfferId || !getDatabase().getOffer(comment.getOfferId()).getOwner_id().equals(comment.getRecieverId())) {
            throw new OfferNotFoundException("Offer with this ID and this owner was not found, so it can't be commented", CommentResource.class);
        } else {
            if (getDatabase().getCommentsWithFilters(comment.getGiverId(), null, comment.getOfferId()).size() == 0) {
                Comment dbComment = new Comment(
                        comment.getGiverId(),
                        comment.getRecieverId(),
                        comment.getOfferId(),
                        comment.getCommentText(),
                        comment.isPositive()
                );
                Comment createdComment = getDatabase().saveComment(dbComment);
                return Response.created(URI.create(uriInfo.getPath() + "/" + createdComment.getId())).entity(createdComment).status(Response.Status.CREATED).encoding("UTF-8").build();
            } else {
                throw new CommentConflictException("this offer is commented by this user");
            }
        }


    }

    @Path("/{commentId}")
    @DELETE
    @ApiOperation(value = "Delete comment.", notes = "Delete existing comment.")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Comment deleted."),
            @ApiResponse(code = 404, message =  "Comment not found.", response = ErrorInfo.class),
            @ApiResponse(code  =500, message = "Internal server error.", response = ErrorInfo.class)})

    public Response deleteComment(
            @NotBlank(message = "{deleteComment.commentId.empty}")
            @Pattern(regexp = "\\d+", message = "{comment.notDigit}")
            @ApiParam(value = "Comment id from database to delete.", required = true) @PathParam("commentId") String commentId) throws Exception {
        Boolean success = getDatabase().deleteComment(commentId);
        if (!success)
            throw new CommentNotFoundException("Comment with this id was not found in database");
        else
            return Response.status(Response.Status.NO_CONTENT).entity(null).encoding("UTF-8").type(MediaType.APPLICATION_JSON).build();
    }


    @DELETE
    @ApiOperation(value = "Delete comments using filters", notes = "Delete comments using filters")
    public Response deleteCommentsWithFilters(
            @ApiParam(value = "Comments of user with id.", required = true) @QueryParam(value = "userId") Long userId,
            @QueryParam(value = "Comments to offer with id.") Long offerId) throws Exception {
        String userIdString = (userId != null) ? Long.toString(userId) : null;
        String offerIdString = (offerId != null) ? Long.toString(offerId) : null;


        if (userId != null && offerId != null) {
            /*if (Long.valueOf(getDatabase().getOffer(Long.toString(offerId)).getOwner_id())!=userId){

            }*/
            Boolean deleteSuccess;
            Collection<Comment> userCommentsFromAuction = new ArrayList<>();
            if ((userCommentsFromAuction = getDatabase().getCommentsWithFilters(userIdString, null, offerIdString)).size() != 0) {
                for (Comment comment : userCommentsFromAuction) {
                    deleteSuccess = getDatabase().deleteComment(comment.getId());
                    if (!deleteSuccess) {
                        throw new InternalServerErrorException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorInfo("Delete user's comments from this auction failed", "Nie udało się usunąć komentarzy danego użytkownika spod tej aukcji", 1022)).encoding("UTF-8").type(MediaType.APPLICATION_JSON).build());

                    }
                }
            }
            return Response.status(Response.Status.NO_CONTENT).entity(null).encoding("UTF-8").type(MediaType.APPLICATION_JSON).build();
        } else if (userId != null && offerId != null) {
            if (getDatabase().getUser(userIdString) != null) {
                if (getDatabase().deleteCommentsFromUser(userIdString) == false) {
                    throw new InternalServerErrorException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorInfo("Delete all user's comments failed", "Nie udało się usunąć wszystkich komentarzy danego użytkownika", 1021)).encoding("UTF-8").type(MediaType.APPLICATION_JSON).build());
                } else {
                    return Response.status(Response.Status.NO_CONTENT).entity(null).encoding("UTF-8").type(MediaType.APPLICATION_JSON).build();
                }

            }
        } else if (userId == null && offerId != null) {
            if (getDatabase().getOffer(offerIdString) != null) {
                if (getDatabase().deleteCommentsFromAuction(offerIdString) == false) {
                    throw new InternalServerErrorException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorInfo("Delete all auctions's comments failed", "Nie udało się usunąć wszystkich komentarzy danej aukcji", 1020)).encoding("UTF-8").type(MediaType.APPLICATION_JSON).build());
                } else {
                    return Response.status(Response.Status.NO_CONTENT).entity(null).encoding("UTF-8").type(MediaType.APPLICATION_JSON).build();
                }
            }
        }
        throw new CommentConflictException("You didn't set any data to filter");
    }

    @Path("/{commentId}")
    @PUT
    @ApiOperation(value = "Update comment.", notes = "Update existing comment.")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Comment updated.", response = Comment.class),
            @ApiResponse(code = 400, message = "Bad request.", response = ErrorInfo.class, responseContainer = "LIST"),
            @ApiResponse(code = 404, message = "Comment not found.", response = ErrorInfo.class),
            @ApiResponse(code = 409, message =  "Conflict.", response = ErrorInfo.class),
            @ApiResponse(code  =500, message = "Internal server error.", response = ErrorInfo.class)})
    public Response updateComment(
            @NotBlank(message = "{updateComment.commentId.empty}")
            @ApiParam(value = "Comment id from database.", required = true) @PathParam("commentId") String commentId,
            @NotNull(message = "{updateComment.comment.empty}")
            @ApiParam(value = "Updated Comment object.", required = true)
            @pl.edu.amu.rest.constraint.Comment Comment comment)
            throws Exception {
        if (getDatabase().getComment(commentId) != null) {
            Comment dbComment = new Comment(
                    comment.getCommentText(),
                    comment.isPositive()
            );
            Comment updatedComment=getDatabase().updateComment(commentId,dbComment);
            if (updatedComment==null){
                throw new InternalServerErrorException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorInfo("Comment update operation failed", "Nie udało się zaktualizować danego komentarza", 1019)).encoding("UTF-8").type(MediaType.APPLICATION_JSON).build());
            }
            return Response.ok().entity(updatedComment).encoding("UTF-8").build();
        } else {
            throw new CommentNotFoundException("This comment couldn't be updated, because it doesn't exist");
        }
    }

}
