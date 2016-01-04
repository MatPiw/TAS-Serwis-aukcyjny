package pl.edu.amu.rest.exception;

import pl.edu.amu.rest.CommentResource;
import pl.edu.amu.rest.OfferResource;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ResourceBundle;

/**
 * Created by Altenfrost on 2016-01-02.
 */
public class OfferNotFoundException extends NotFoundException {
    private static String messageKey="notFoundOfferMessage";
    String bundle=new String();
    ResourceBundle labels = ResourceBundle.getBundle("offermessage");

    @Override
    public String getLocalizedMessage() {
        return labels.getString(messageKey);
    }

    public OfferNotFoundException() {
        super(Response.status(Response.Status.NOT_FOUND)
                .type("application/json")
                .encoding("UTF-8")
                .build());
        bundle="offermessage";
        labels=ResourceBundle.getBundle(bundle);
    }

    public OfferNotFoundException(String message, Class throwingClass) {
        super(message);
        if (throwingClass==OfferResource.class){
            bundle="offermessage";
        }
        else if(throwingClass== CommentResource.class){
            bundle="commentmessage";
        }
        labels=ResourceBundle.getBundle(bundle);

    }
}
