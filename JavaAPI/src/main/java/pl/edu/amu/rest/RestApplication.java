package pl.edu.amu.rest;

import org.glassfish.jersey.server.ResourceConfig;
import pl.edu.amu.rest.dao.Offer;

public class RestApplication extends ResourceConfig {
    public RestApplication () {
        //register(HelloResourceImpl.class);
        register(UserResource.class);
        register(OfferResource.class);
    }
}
