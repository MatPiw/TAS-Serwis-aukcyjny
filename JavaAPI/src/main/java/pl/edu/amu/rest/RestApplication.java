package pl.edu.amu.rest;

import io.swagger.jaxrs.config.BeanConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;
import org.glassfish.jersey.server.validation.ValidationFeature;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import pl.edu.amu.rest.exception.BidNotFoundException;
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
        register(CommentResource.class);
        register(BidResource.class);
        register(BidConflictExceptionMapper.class);
        register(NotFoundExceptionMapper.class);
        register(NotFoundOfferUpdateExceptionMapper.class);
        register(SellersOffersNotFoundExceptionMapper.class);
        register(UserConflictExceptionMapper.class);
        register(CommentConflictExceptionMapper.class);
        register(JspMvcFeature.class);
        // Register your custom ExceptionMapper.
        register(ConstraintViolationExceptionMapper.class);
        // Register Bean Validation (this is optional as BV is automatically registered when jersey-bean-validation is on the classpath but it's good to know it's happening).
        register(ValidationFeature.class);

        packages("io.swagger.jaxrs.listing");

        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.2");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("");
        beanConfig.setResourcePackage("pl.edu.amu.rest");
        beanConfig.setScan(true);
        beanConfig.setTitle("Auction Service");
        beanConfig.setDescription("Rest API to handle simple auction service.");

        /*register(OfferResource.class);
        register(BidResource.class);
        register(CommentResource.class);*/
    }
}