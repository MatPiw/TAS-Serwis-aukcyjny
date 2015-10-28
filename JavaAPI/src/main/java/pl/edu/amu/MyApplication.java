package pl.edu.amu;
import org.glassfish.jersey.server.ResourceConfig;

public class MyApplication extends ResourceConfig {
    public MyApplication () {
        register(HelloResource.class);
    }
}
