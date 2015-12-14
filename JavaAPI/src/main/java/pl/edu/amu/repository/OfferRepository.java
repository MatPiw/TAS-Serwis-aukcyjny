package pl.edu.amu.repository;


import pl.edu.amu.rest.model.Offer;
import pl.edu.amu.tools.DBConnection;
import pl.edu.amu.tools.DBOperator;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class OfferRepository {
    //private final List<Offer> offers = new ArrayList<>();


    private DBConnection database;
    private DBOperator operator;
    private Connection connection;

    public List<Offer> getOffers(){
        return operator.getAllOffers(connection);
    }

    public OfferRepository() {
        try {
            database= new DBConnection();
            connection = database.getConnection();
            operator= new DBOperator();
            //operator.getAllOffers(connection, offers);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("coœ nie wysz³o");
        }
        //users.add(new User("huecov", "h123"));
    }

    public Offer save(Offer offer)  {

        //tu jest problem, bo id przydziela baza danych a nie bezposrednio api. przez to jest problem z zapisywaniem ofert
        Offer dbOffer = null; /* = findById(offer.getId());
        if (dbOffer != null) {
            offer.setId(dbOffer.getId());
            offer.setTitle(dbOffer.getTitle());
            offer.setDescription(dbOffer.getDescription());
            offer.setPicturePath(dbOffer.getPicturePath());
            offer.setActive(dbOffer.getActive());
            offer.setCreatedAt(dbOffer.getCreatedAt());
            offer.setFinishedAt(dbOffer.getFinishedAt());
        }
        else {*/
            try {
                dbOffer = operator.saveOffer(connection, offer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        //}

        return dbOffer;
    }

    public Offer findById(int id) {
        return operator.getOffer(id, connection);
    }
}
