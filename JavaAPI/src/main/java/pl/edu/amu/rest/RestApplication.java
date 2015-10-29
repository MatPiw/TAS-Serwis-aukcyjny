package pl.edu.amu.rest;

import org.glassfish.jersey.server.ResourceConfig;

public class RestApplication extends ResourceConfig {
    public RestApplication () {
        register(HelloResourceImpl.class);
        register(UserResourceImpl.class);
    }
}
