package pl.edu.amu.rest.exception;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import java.util.ResourceBundle;

/**
 * Created by Altenfrost on 2015-12-30.
 */
public class UserConflictException extends ClientErrorException {
    private static String messageKey="conflictMessage";
    private static long serialVersionUID = 1L;
    ResourceBundle labels = ResourceBundle.getBundle("usermessage");

    @Override
    public String getLocalizedMessage() {
        return labels.getString(messageKey);
    }

    public UserConflictException() {
        super(Response.status(Response.Status.CONFLICT)
                .type("application/json").build());
    }

    public UserConflictException(String message) {
        super(message, Response.Status.CONFLICT);

    }
}
