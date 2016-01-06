package pl.edu.amu.repository;


import pl.edu.amu.rest.model.Bid;
import pl.edu.amu.rest.model.User;
import pl.edu.amu.tools.DBConnection;
import pl.edu.amu.tools.DBOperator;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class BidRepository {
    //private final List<Bid> bids = new ArrayList<>();


    private DBConnection database;
    private DBOperator operator;
    private Connection connection;

    public List<Bid> getBids(){
        return operator.getAllBids(connection);
    }

    public BidRepository() {
        try {
            database= new DBConnection();
            connection = database.getConnection();
            operator= new DBOperator();
            //operator.getAllBids(connection);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("cos nie wyszlo");
        }
        //users.add(new User("huecov", "h123"));
    }

    public Bid save(Bid bid){
        try {
            operator.saveBid(connection, bid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bid;
    }

    public Bid findById(int id) {
        List<Bid> bids = getBids();
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
            //bids.remove(bid);
        }
    }
}
