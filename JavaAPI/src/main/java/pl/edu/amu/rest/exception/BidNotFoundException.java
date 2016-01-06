package pl.edu.amu.rest.exception;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ResourceBundle;

/**
 * Created by Altenfrost on 2016-01-05.
 */
public class BidNotFoundException extends NotFoundException {
    private static String messageKey = "notFoundBidMessage";
    private static long serialVersionUID = 1L;
    private String bundle="commentmessage";
    ResourceBundle labels= ResourceBundle.getBundle(bundle);

    @Override
    public String getLocalizedMessage() {
        return labels.getString(messageKey);
    }

    public BidNotFoundException() {
        super(Response.status(Response.Status.NOT_FOUND)
                .type("application/json").encoding("UTF-8").build());

    }

    public BidNotFoundException(String message) {
        super(message);

    }
}
