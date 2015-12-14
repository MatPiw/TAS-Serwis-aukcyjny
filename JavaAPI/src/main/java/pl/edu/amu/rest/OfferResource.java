package pl.edu.amu.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.amu.repository.OfferRepository;
import pl.edu.amu.rest.model.ErrorResponse;
import pl.edu.amu.rest.model.Offer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/offers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class OfferResource {

    private static final OfferRepository offerRep = new OfferRepository();
    private static Logger LOG = LoggerFactory.getLogger(OfferResource.class);


    //@Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Offer> getOffers(){
        LOG.info("/get/offers");
        return offerRep.getOffers();
    }


    @POST
    public Response saveOffer(Offer offer){
        //if (offerRep.findById(offer.getId()) == null){
            LOG.info("/post/offers/" + offer.getId());
            Offer temp = offerRep.save(offer);
            return Response.ok(temp).build();
        //}
        /*return Response.status(Response.Status.CONFLICT)
                .entity(new ErrorResponse(Response.Status.CONFLICT, "Something went wrong when adding an offer."))
                .build();*/
    }

    @GET
    @Path("/{id}")
    public Offer getOffer(@PathParam("id") final int id){
        Offer offer = offerRep.findById(id);
        return offer;
    }

}

