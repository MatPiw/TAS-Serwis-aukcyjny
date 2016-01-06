package pl.edu.amu.tools;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.edu.amu.rest.model.Bid;
import pl.edu.amu.rest.model.Comment;
import pl.edu.amu.rest.model.Offer;
import pl.edu.amu.rest.model.User;

public class DBOperator {


//-------------users------------------//

    public List<User> getAllUsers(Connection connection){
        try {
            List<User> userList = new ArrayList<>();
            // String uname = request.getParameter("uname");
            PreparedStatement ps = connection
                    .prepareStatement("SELECT * FROM users");
            // ps.setString(1,uname);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("ID"));
                user.setLogin(rs.getString("LOGIN"));
                user.setHashPassword(rs.getString("HASH_PASSWORD"));
                //user.setId(rs.getString("ID"));
                user.setPermissions(rs.getBoolean("PERMISSIONS"));
                user.setFirstName(rs.getString("FIRST_NAME"));
                user.setLastName(rs.getString("LAST_NAME"));
                user.setEmail(rs.getString("EMAIL"));
                user.setCity(rs.getString("CITY"));
                user.setAddress(rs.getString("ADDRESS"));
                user.setPhone(rs.getString("PHONE"));
                user.setZipCode(rs.getString("ZIP_CODE"));
                user.setCreatedAt(rs.getTimestamp("CREATED_AT"));
                user.setUserOffers(getUserOffers(connection, user));
                user.setUserComments(getUserComments(connection,user));
                userList.add(user);            }
            return userList;
        } catch (Exception e) {
            System.out.println("Problem z polaczeniem z Baza danych");
        }
        return Collections.emptyList();
    }

    public User getUser(String login, Connection connection) {
        List<User> users = this.getAllUsers(connection);
        for(User user : users){
            if (login.equalsIgnoreCase(user.getLogin())){
                return user;
            }
        }
        return null;
    }

    public User saveUser(Connection connection, User user) {
        int result = 0;
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO users(LOGIN, HASH_PASSWORD, PERMISSIONS, FIRST_NAME," +
                            "LAST_NAME, EMAIL, CITY, ADDRESS, PHONE, ZIP_CODE,CONFIRMED, CREATED_AT)" +
                            " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1,user.getLogin());
            ps.setString(2,user.getHashPassword());
            ps.setBoolean(3,user.getPermissions());
            ps.setString(4,user.getFirstName());
            ps.setString(5,user.getLastName());
            ps.setString(6,user.getEmail());
            ps.setString(7,user.getCity());
            ps.setString(8,user.getAddress());
            ps.setString(9,user.getPhone());
            ps.setString(10,user.getZipCode());
            ps.setBoolean(11,user.getConfirmed());
            ps.setTimestamp(12,user.getCreatedAt());

            result = ps.executeUpdate();
            System.out.println("Dodano uzytkownika " + user.getLogin() + " do bazy danych.");
        } catch (Exception e) {
            System.out.println("Query Status: " + result);
            e.printStackTrace();
        }
        return this.getUser(user.getLogin(), connection);
    }

    public User updateUser(User user, Connection connection) {
        int result = 0;
        try {
            PreparedStatement ps = connection.prepareStatement(
                "UPDATE users SET " +
                "FIRST_NAME = ?, LAST_NAME=?, EMAIL = ?, CITY = ?," +
                "ADDRESS = ?, PHONE = ?, ZIP_CODE = ?" +
                "WHERE LOGIN = ?"
                );
            //ps.setString(1,user.getHashPassword());
            ps.setString(1,user.getFirstName());
            ps.setString(2,user.getLastName());
            ps.setString(3,user.getEmail());
            ps.setString(4,user.getCity());
            ps.setString(5,user.getAddress());
            ps.setString(6,user.getPhone());
            ps.setString(7,user.getZipCode());
            ps.setString(8,user.getLogin());

            result = ps.executeUpdate();
            //System.out.println("Dodano uzytkownika " + user.getLogin() + " do bazy danych.");
        } catch (Exception e) {
            System.out.println("Query Status: " + result);
            e.printStackTrace();
        }
        return this.getUser(user.getLogin(), connection);
    }

    //-------------------offers-------------------------//
    public List<Offer> getAllOffers(Connection connection) {
        try {
            List<Offer> offers = new ArrayList<>();
            // String uname = request.getParameter("uname");
            PreparedStatement ps = connection
                    .prepareStatement("SELECT * FROM offers");
            // ps.setString(1,uname);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Offer offer = new Offer();
                offer.setId(rs.getInt("ID"));
                offer.setTitle(rs.getString("TITLE"));
                offer.setDescription(rs.getString("DESCRIPTION"));
                offer.setPicturePath(rs.getString("PICTURE_PATH"));
                offer.setOwnerId(rs.getInt("OWNER_ID"));
                offer.setBuyNowPrice(rs.getFloat("BUY_NOW_PRICE"));
                offer.setActive(rs.getBoolean("ACTIVE"));
                offer.setCreatedAt(rs.getTimestamp("CREATED_AT"));
                offer.setFinishedAt(rs.getTimestamp("FINISHED_AT"));
                offers.add(offer);
            }
            return offers;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Problem w polaczeniu z baza danych.");
        }
        return Collections.emptyList();
    }

    public List<Offer> getUserOffers(Connection connection, User user) {
        List<Offer> offers = new ArrayList<>();

        try {

            int id = user.getId();
            // String uname = request.getParameter("uname");
            PreparedStatement ps = connection
                    .prepareStatement("SELECT * FROM offers WHERE OWNER_ID = ?");
            ps.setInt(1,id);
            // ps.setString(1,uname);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Offer offer = new Offer();
                offer.setId(rs.getInt("ID"));
                offer.setTitle(rs.getString("TITLE"));
                offer.setDescription(rs.getString("DESCRIPTION"));
                offer.setPicturePath(rs.getString("PICTURE_PATH"));
                offer.setOwnerId(rs.getInt("OWNER_ID"));
                offer.setBuyNowPrice(rs.getFloat("BUY_NOW_PRICE"));
                offer.setActive(rs.getBoolean("ACTIVE"));
                offer.setCreatedAt(rs.getTimestamp("CREATED_AT"));
                offer.setFinishedAt(rs.getTimestamp("FINISHED_AT"));
                offers.add(offer);
            }
    } catch (Exception e) {
            System.out.println("Blad w polaczeniu z baza danych.");
            e.printStackTrace();
    }
        return offers;
    }

    public Offer getOffer(int id, Connection connection) {
        List<Offer> offers = this.getAllOffers(connection);
        for(Offer offer : offers){
            if (id == offer.getId()){
                return offer;
            }
        }
        return null;
    }

    public Offer saveOffer(Connection connection, Offer offer) {

        offer.setCreatedAt();
        offer.setFinishedAt();
        int result = 0;
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO offers (TITLE, DESCRIPTION, PICTURE_PATH, OWNER_ID," +
                            "BUY_NOW_PRICE, CREATED_AT )" +
                            " VALUES(?,?,?,?,?,?)");
            ps.setString(1,offer.getTitle());
            ps.setString(2,offer.getDescription());
            ps.setString(3,offer.getPicturePath());
            ps.setInt(4, offer.getOwnerId());
            ps.setFloat(5, offer.getBuyNowPrice());
            //ps.setBoolean(6, offer.getActive());
            ps.setTimestamp(6, offer.getCreatedAt());
            //ps.setTimestamp(8, offer.getFinishedAt());
            //System.out.println(ps);
            result = ps.executeUpdate();
            //offer.setId()
            System.out.println("Dodano oferte o tytule " + offer.getTitle() + " do bazy danych.");

        } catch (Exception e) {
            System.out.println("Query Status: " + result);
            e.printStackTrace();
        }
        return offer;
    }

    //------------------bids--------------------//
    public List<Bid> getAllBids(Connection connection) {
        try {
            List<Bid> bids = new ArrayList<>();
            // String uname = request.getParameter("uname");
            PreparedStatement ps = connection
                    .prepareStatement("SELECT * FROM bids");
            // ps.setString(1,uname);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Bid bid = new Bid();
                bid.setId(rs.getInt("ID"));
                bid.setOfferId(rs.getInt("OFFER_ID"));
                bid.setBidderId(rs.getInt("BIDDER_ID"));
                bid.setPrice(rs.getFloat("PRICE"));
                bid.setCreatedAt(rs.getTimestamp("CREATED_AT"));
                bids.add(bid);
            }
            return bids;
        } catch (Exception e) {
            System.out.println("Problem z polaczeniem z Baza danych.");
        }
        return Collections.emptyList();
    }

    public void saveBid(Connection connection, Bid bid) {
        bid.setCreatedAt();
        int result = 0;
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO bids (OFFER_ID, BIDDER_ID, PRICE, CREATED_AT)" +
                            " VALUES(?,?,?,?)");
            ps.setInt(1, bid.getOfferId());
            ps.setInt(2, bid.getBidderId());
            ps.setFloat(3, bid.getPrice());
            ps.setTimestamp(4, bid.getCreatedAt());

            //System.out.println(ps);
            result = ps.executeUpdate();
            System.out.println("Dodano oferte o wartosci " + bid.getPrice() + " do aukcji o numerze "+ bid.getOfferId() +" do bazy danych.");
        } catch (Exception e) {
            System.out.println("Query Status: " + result);
            e.printStackTrace();
        }
    }

    //-------------comments-------------//
    public List<Comment> getAllComments(Connection connection) {
        try {
            List<Comment> comments = new ArrayList<>();
            // String uname = request.getParameter("uname");
            PreparedStatement ps = connection
                    .prepareStatement("SELECT * FROM comments");
            // ps.setString(1,uname);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setId(rs.getInt("ID"));
                comment.setOfferId(rs.getInt("OFFER_ID"));
                comment.setGiverId(rs.getInt("GIVER_ID"));
                comment.setRecieverId(rs.getInt("RECIEVER_ID"));
                comment.setComment(rs.getString("COMMENT"));
                comment.setPositive(rs.getBoolean("POSITIVE"));
                comment.setCreatedAt(rs.getTimestamp("CREATED_AT"));
                comments.add(comment);

            }
            return comments;
        } catch (Exception e) {
            System.out.println("Blad polaczenia z baza danych");
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<Comment> getUserComments(Connection connection, User user) {
        List<Comment> comments = new ArrayList<>();

        try {
            int id = user.getId();
            // String uname = request.getParameter("uname");
            PreparedStatement ps = connection
                    .prepareStatement("SELECT * FROM comments WHERE RECIEVER_ID = ?");
            ps.setInt(1,id);
            // ps.setString(1,uname);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setId(rs.getInt("ID"));
                comment.setComment(rs.getString("COMMENT"));
                comment.setPositive(rs.getBoolean("POSITIVE"));
                comment.setGiverId(rs.getInt("GIVER_ID"));
                comment.setRecieverId(rs.getInt("RECIEVER_ID"));
                comment.setOfferId(rs.getInt("OFFER_ID"));
                comment.setCreatedAt(rs.getTimestamp("CREATED_AT"));
                comments.add(comment);
            }
        } catch (Exception e) {
            System.out.println("Blad w polaczeniu z baza danych.");
            e.printStackTrace();
        }
        return comments;
    }

    public void saveComment(Connection connection, Comment comment) {
        comment.setCreatedAt();
        int result = 0;
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO comments (OFFER_ID, GIVER_ID, RECIEVER_ID, COMMENT, POSITIVE, CREATED_AT)" +
                            " VALUES(?,?,?,?,?,?)");
            ps.setInt(1,comment.getOfferId());
            ps.setInt(2,comment.getgiverId());
            ps.setInt(3,comment.getRecieverId());
            ps.setString(4, comment.getComment());
            ps.setBoolean(5, comment.getPositive());
            ps.setTimestamp(6, comment.getCreatedAt());

            //System.out.println(ps);
            result = ps.executeUpdate();
            System.out.println("Dodano nowy komentarz do bazy danych. ");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Query Status: " + result);
        }
    }

}