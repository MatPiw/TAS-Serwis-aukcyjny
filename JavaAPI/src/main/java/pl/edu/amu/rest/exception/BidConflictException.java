package pl.edu.amu.rest.exception;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import java.util.ResourceBundle;

/**
 * Created by Altenfrost on 2016-01-05.
 */
public class BidConflictException extends ClientErrorException {
    private static String messageKey="conflictMessage";
    private static long serialVersionUID = 1L;
    ResourceBundle labels = ResourceBundle.getBundle("bidmessage");

    @Override
    public String getLocalizedMessage() {
        return labels.getString(messageKey);
    }

    public BidConflictException() {
        super(Response.status(Response.Status.CONFLICT)
                .type("application/json")
                .encoding("UTF-8")
                .build());
    }

    public BidConflictException(String message) {
        super(message, Response.Status.CONFLICT);

    }
}
