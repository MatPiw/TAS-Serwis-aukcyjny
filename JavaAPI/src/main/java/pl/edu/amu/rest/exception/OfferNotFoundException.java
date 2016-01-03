package pl.edu.amu.rest.exception;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ResourceBundle;

/**
 * Created by Altenfrost on 2016-01-02.
 */
public class OfferNotFoundException extends NotFoundException {
    private static String messageKey="notFoundOfferMessage";
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
    }

    public OfferNotFoundException(String message) {
        super(message);

    }
}
