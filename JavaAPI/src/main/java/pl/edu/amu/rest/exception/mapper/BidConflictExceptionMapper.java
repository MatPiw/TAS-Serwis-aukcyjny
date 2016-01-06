package pl.edu.amu.rest.exception.mapper;

import pl.edu.amu.rest.exception.BidConflictException;
import pl.edu.amu.rest.model.error.ErrorInfo;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by Altenfrost on 2016-01-05.
 */
@Provider
public class BidConflictExceptionMapper implements ExceptionMapper<BidConflictException> {
    @Override
    public Response toResponse(BidConflictException ex) {
        ErrorInfo e=new ErrorInfo(ex.getMessage(),ex.getLocalizedMessage(),ex.hashCode());
        return Response.status(Response.Status.CONFLICT)
                .entity(e)
                .type(MediaType.APPLICATION_JSON)
                .encoding("UTF-8")
                .build();
    }
}
