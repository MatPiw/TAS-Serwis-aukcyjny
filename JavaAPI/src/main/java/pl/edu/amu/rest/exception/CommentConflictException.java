package pl.edu.amu.rest.exception;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import java.util.ResourceBundle;

/**
 * Created by Altenfrost on 2016-01-04.
 */
public class CommentConflictException extends ClientErrorException {
    private static String messageKey="conflictMessage";
    private static long serialVersionUID = 1L;
    ResourceBundle labels = ResourceBundle.getBundle("usermessage");

    @Override
    public String getLocalizedMessage() {
        return labels.getString(messageKey);
    }

    public CommentConflictException() {
        super(Response.status(Response.Status.CONFLICT)
                .type("application/json")
                .encoding("UTF-8")
                .build());
    }

    public CommentConflictException(String message) {
        super(message, Response.Status.CONFLICT);

    }
}
