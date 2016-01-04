package pl.edu.amu.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;
import org.glassfish.jersey.server.validation.ValidationFeature;
import pl.edu.amu.rest.exception.OfferNotFoundException;
import pl.edu.amu.rest.exception.mapper.*;

public class RestApplication extends ResourceConfig {
    public RestApplication () {
        //register(HelloResourceImpl.class);
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);
        register(InternalServerErrorMapper.class);
        register(UsersResource.class);
        register(OfferResource.class);
        register(NotFoundExceptionMapper.class);
        register(NotFoundOfferUpdateExceptionMapper.class);
        register(UserConflictExceptionMapper.class);
        register(JspMvcFeature.class);
                // Register your custom ExceptionMapper.
        register(ConstraintViolationExceptionMapper.class);
                // Register Bean Validation (this is optional as BV is automatically registered when jersey-bean-validation is on the classpath but it's good to know it's happening).
        register(ValidationFeature.class);

        /*register(OfferResource.class);
        register(BidResource.class);
        register(CommentResource.class);*/
    }
}