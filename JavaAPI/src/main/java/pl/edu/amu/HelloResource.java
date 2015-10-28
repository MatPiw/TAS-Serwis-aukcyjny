package pl.edu.amu;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/hello")
public class HelloResource {

    @GET @Path("/{name}") @Produces("application/json")
        public String getMsg(@PathParam("name") String name) {

                return "hello: " + name;
        }
}
