package pl.edu.amu.rest.exception;

import pl.edu.amu.rest.BidResource;
import pl.edu.amu.rest.CommentResource;
import pl.edu.amu.rest.OfferResource;
import pl.edu.amu.rest.UsersResource;
import pl.edu.amu.rest.model.User;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ResourceBundle;

/**
 * Created by Altenfrost on 2015-12-30.
 */
public class UserNotFoundException extends NotFoundException {
    private static String messageKey = "notFoundUserMessage";
    private static long serialVersionUID = 1L;
    private String bundle;
    ResourceBundle labels;

    @Override
    public String getLocalizedMessage() {
        return labels.getString(messageKey);
    }

    public UserNotFoundException() {
        super(Response.status(Response.Status.NOT_FOUND)
                .type("application/json").build());
        bundle = "usermessage";
        labels = ResourceBundle.getBundle(bundle);

    }

    public UserNotFoundException(String message, Class throwingClass) {
        super(message);
        if (throwingClass == UsersResource.class) {
            bundle = "usermessage";
        } else if (throwingClass == OfferResource.class) {
            bundle = "offermessage";
        } else if (throwingClass == CommentResource.class) {
            bundle = "commentmessage";
        } else if (throwingClass == BidResource.class) {
            bundle = "bidmessage";
        }
        labels = ResourceBundle.getBundle(bundle);


    }

}
