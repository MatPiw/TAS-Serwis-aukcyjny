package pl.edu.amu.rest.exception.mapper;

import pl.edu.amu.rest.exception.CommentConflictException;
import pl.edu.amu.rest.model.error.ErrorInfo;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by Altenfrost on 2016-01-04.
 */
@Provider
public class CommentConflictExceptionMapper implements ExceptionMapper<CommentConflictException> {
    @Override
    public Response toResponse(CommentConflictException ex) {
        ErrorInfo e=new ErrorInfo(ex.getMessage(),ex.getLocalizedMessage(),ex.hashCode());
        return Response.status(Response.Status.CONFLICT)
                .entity(e)
                .type(MediaType.APPLICATION_JSON)
                .encoding("UTF-8")
                .build();
    }
}
