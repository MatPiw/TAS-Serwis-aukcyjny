package pl.edu.amu.rest.exception;





import javax.ws.rs.WebApplicationException;

public class UserDbException extends WebApplicationException {
    /*public UserDbException(String message, String userMessage) {
        super(Response.status(Response.Status.NOT_FOUND)
                .entity(createError(message, userMessage)).build());
    }

    private static ErrorMessage createError(String message, String userMessage) {
        return new ErrorMessage(message, userMessage);
    }*/
}
