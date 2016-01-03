package pl.edu.amu.rest.exception.mapper;



import pl.edu.amu.rest.model.error.ValidationError;

import javax.annotation.Priority;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Priorities;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Altenfrost on 2015-12-31.
 */
@Provider
@Priority(Priorities.USER)
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> temp= e.getConstraintViolations();



            ArrayList<ValidationError> errorList=new ArrayList<ValidationError>();

            for (ConstraintViolation<?> t:temp){
                String invalidValue=(t.getInvalidValue()==null)?null:t.getInvalidValue().toString();
                errorList.add(new ValidationError(t.getMessage(),t.getMessageTemplate(),invalidValue));
            }




        return Response
                .status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .encoding("UTF-8")
                .entity(errorList)
                .build();
    }
}
