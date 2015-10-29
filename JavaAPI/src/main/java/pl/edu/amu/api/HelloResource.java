package pl.edu.amu.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * Created by adrian.perek on 2015-20-10.
 */
@Path("/hello")
public interface HelloResource {

    @GET
    @Path("/{name}") @Produces("text/plain")
    public String getMsg(@PathParam("name") String name);
}
