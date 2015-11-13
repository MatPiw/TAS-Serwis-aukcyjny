package pl.edu.amu.repository;


import pl.edu.amu.rest.dao.Offer;
import pl.edu.amu.tools.DBConnection;
import pl.edu.amu.tools.DBDownloader;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class OfferRepository {
    private final List<Offer> offers = new ArrayList<>();


    private DBConnection database;
    private DBDownloader downloader;
    private Connection connection;

    public List<Offer> getOffers(){
        return offers;
    }

    public OfferRepository() {
        try {
            database= new DBConnection();
            connection = database.getConnection();
            downloader= new DBDownloader();
            //downloader.getAllOffers(connection, offers);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("coœ nie wysz³o");
        }
        //users.add(new User("huecov", "h123"));
    }

    public Offer save(Offer offer)  {
        offers.add(offer);
        return offer;
    }

    public Offer findById(int id) {
        for(Offer offer : offers){
            if (id == offer.getId()){
                return offer;
            }
        }
        return null;
    }
}
