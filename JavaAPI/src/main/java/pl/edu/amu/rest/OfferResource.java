package pl.edu.amu.rest;

import pl.edu.amu.repository.OfferRepository;
import pl.edu.amu.rest.model.Offer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/offers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class OfferResource {

    private static final OfferRepository offerRep = new OfferRepository();

    //@Override
    @GET
    public List<Offer> getOffers(){
        return offerRep.getOffers();
    }


    @POST
    public Offer saveOffer(Offer offer){
        if (offerRep.findById(offer.getId()) == null){
            return offerRep.save(offer);
        }
        return null;
    }

    @GET
    @Path("/{id}")
    public Offer getOffer(@PathParam("id") final int id){
        Offer offer = offerRep.findById(id);
        return offer;
    }

}

