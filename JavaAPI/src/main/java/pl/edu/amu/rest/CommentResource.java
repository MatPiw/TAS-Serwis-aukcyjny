package pl.edu.amu.rest;

import pl.edu.amu.repository.CommentRepository;
import pl.edu.amu.rest.model.Comment;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/comments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class CommentResource {

    private static final CommentRepository commentRep = new CommentRepository();

    //@Override
    @GET
    public List<Comment> getOffers(){
        return commentRep.getComments();
    }


    @POST
    public Comment saveOffer(Comment comment){
        if (commentRep.findById(comment.getId()) == null){
            return commentRep.save(comment);
        }
        return null;
    }

    @GET
    @Path("/{id}")
    public Comment getComment(@PathParam("id") final int id){
        Comment comment = commentRep.findById(id);
        return comment;
    }

}
