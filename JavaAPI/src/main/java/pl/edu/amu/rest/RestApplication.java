package pl.edu.amu.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;
import org.glassfish.jersey.server.validation.ValidationFeature;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import pl.edu.amu.rest.exception.OfferNotFoundException;
import pl.edu.amu.rest.exception.mapper.*;

public class RestApplication extends ResourceConfig {
    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;

    public static SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure();
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }
    public RestApplication() {
        //createSessionFactory();
        //register(HelloResourceImpl.class);
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);
        register(InternalServerErrorMapper.class);
        register(UsersResource.class);
        register(OfferResource.class);
        register(NotFoundExceptionMapper.class);
        register(NotFoundOfferUpdateExceptionMapper.class);
        register(SellersOffersNotFoundExceptionMapper.class);
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