package pl.edu.amu.rest.exception;

import pl.edu.amu.rest.OfferResource;
import pl.edu.amu.rest.UsersResource;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ResourceBundle;

/**
 * Created by Altenfrost on 2016-01-04.
 */
public class CommentNotFoundException extends NotFoundException {
    private static String messageKey = "notFoundCommentMessage";
    private static long serialVersionUID = 1L;
    private String bundle="commentmessage";
    ResourceBundle labels;

    @Override
    public String getLocalizedMessage() {
        return labels.getString(messageKey);
    }

    public CommentNotFoundException() {
        super(Response.status(Response.Status.NOT_FOUND)
                .type("application/json").build());
        bundle = "commentmessage";
        labels = ResourceBundle.getBundle(bundle);

    }

    public CommentNotFoundException(String message) {
        super(message);
        labels = ResourceBundle.getBundle(bundle);


    }
}
