package pl.edu.amu.rest.exception.mapper;





import pl.edu.amu.rest.model.error.ErrorInfo;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InternalServerErrorMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(createEntity(exception))
                .build();
    }

    private ErrorInfo createEntity(Exception exception) {
        return new ErrorInfo(exception.getMessage(), exception.getLocalizedMessage(),exception.hashCode());
    }
}
