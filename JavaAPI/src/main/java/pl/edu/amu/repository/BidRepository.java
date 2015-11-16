package pl.edu.amu.repository;


import pl.edu.amu.rest.dao.Bid;
import pl.edu.amu.tools.DBConnection;
import pl.edu.amu.tools.DBOperator;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class BidRepository {
    private final List<Bid> bids = new ArrayList<>();


    private DBConnection database;
    private DBOperator operator;
    private Connection connection;

    public List<Bid> getBids(){
        return bids;
    }

    public BidRepository() {
        try {
            database= new DBConnection();
            connection = database.getConnection();
            operator= new DBOperator();
            operator.getAllBids(connection, bids);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("cos nie wyszlo");
        }
        //users.add(new User("huecov", "h123"));
    }

    public Bid save(Bid bid){
       /* Bid dbBid = findById(bid.getId());
        if (dbBid != null){
            dbBid.setId(bid.getId());
            dbBid.setOfferId(bid.getOfferId());
            dbBid.setBidderId(bid.getBidderId());
            dbBid.setPrice(bid.getPrice());
            dbBid.setCreatedAt(bid.getCreatedAt());

        } else {*/
            try {
                operator.saveBid(connection, bid);
                bids.add(bid);
            } catch (Exception e) {
                e.printStackTrace();
            }

        //}
        return bid;
    }

    public Bid findById(int id) {
        for(Bid bid : bids){
            if (id == bid.getId()){
                return bid;
            }
        }
        return null;
    }

    public void remove(int id){
        Bid bid = findById(id);
        if (bid != null){
            bids.remove(bid);
        }
    }
}
