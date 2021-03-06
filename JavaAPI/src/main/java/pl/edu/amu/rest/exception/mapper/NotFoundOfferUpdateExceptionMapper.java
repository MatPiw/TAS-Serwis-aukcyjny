package pl.edu.amu.rest.exception.mapper;

import pl.edu.amu.rest.exception.OffersUpdateNotFoundException;
import pl.edu.amu.rest.model.error.ErrorInfo;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by Altenfrost on 2016-01-03.
 */
@Provider
public class NotFoundOfferUpdateExceptionMapper implements ExceptionMapper<OffersUpdateNotFoundException> {

    @Override
    public Response toResponse(OffersUpdateNotFoundException e) {
        ErrorInfo v=new ErrorInfo(e.getMessage(),e.getLocalizedMessage(),e.hashCode());
        return Response.status(Response.Status.NOT_FOUND)
                .entity(v)
                .type(MediaType.APPLICATION_JSON)
                .encoding("UTF-8")
                .build();
    }
}
