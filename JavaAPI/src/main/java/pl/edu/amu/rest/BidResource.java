package pl.edu.amu.rest;


import pl.edu.amu.repository.BidRepository;
import pl.edu.amu.rest.model.Bid;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/bids")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class BidResource {

    private static final BidRepository bidRep = new BidRepository();

    //@Override
    @GET
    public List<Bid> getBids(){
    /*try {
        DBConnection database= new DBConnection();
        Connection connection = database.getConnection();
        DBDownloader downloader= new DBDownloader();
        downloader.getAllUsers(connection, users);

    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("coœ nie wysz³o");
    }*/
        return bidRep.getBids();
    }


    @POST
    public Bid saveBid(Bid bid){
        //if (bidRep.findById(bid.getId()) == null){
        //    return bidRep.save(bid);
        //}
        return bidRep.save(bid);
    }

    @GET
    @Path("/{id}")
    public Bid getBid(@PathParam("id") final int id){
        Bid bid = bidRep.findById(id);
        return bid;
    }

}
