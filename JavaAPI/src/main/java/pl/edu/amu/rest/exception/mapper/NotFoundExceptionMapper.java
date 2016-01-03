package pl.edu.amu.rest.exception.mapper;

import pl.edu.amu.rest.exception.OfferNotFoundException;
import pl.edu.amu.rest.exception.UserNotFoundException;
import pl.edu.amu.rest.model.error.ErrorInfo;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by Altenfrost on 2015-12-30.
 */
@Provider
//W razie czego poprawić na UserNotFoundException
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException exception) {
        ErrorInfo e = new ErrorInfo(exception.getMessage(), exception.getLocalizedMessage(), exception.hashCode());

        return Response.status(Response.Status.NOT_FOUND)
                .entity(e)
                .encoding("UTF-8")
                .type("application/json")
                .build();
    }

}