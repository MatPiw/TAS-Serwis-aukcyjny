package pl.edu.amu.tools;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import pl.edu.amu.rest.dao.Bid;
import pl.edu.amu.rest.dao.Offer;
import pl.edu.amu.rest.dao.User;

public class DBOperator {


//-------------users------------------//

    public void getAllUsers(Connection connection, List<User> userList) throws Exception {
        try {
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
                user.setCreatedAt(rs.getDate("CREATED_AT"));
                user.setUserOffers(getUserOffers(connection, user));
                userList.add(user);            }
            //return userList;
        } catch (Exception e) {
            throw e;
        }
    }

    public void saveUser(Connection connection, User user) throws Exception {
        String login = user.getLogin();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        Boolean permissions = user.getPermissions();
        String address = user.getAddress();
        String city = user.getCity();
        String phone = user.getPhone();
        String zipCode = user.getZipCode();
        String hashPassword = user.getHashPassword();
        Date createdAt = user.getCreatedAt();
        Boolean confirmed = user.getConfirmed();
        int result = 0;
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO users(LOGIN, HASH_PASSWORD, PERMISSIONS, FIRST_NAME," +
                            "LAST_NAME, EMAIL, CITY, ADDRESS, PHONE, ZIP_CODE,CONFIRMED, CREATED_AT)" +
                            " VALUES('"
                            + login + "','" + hashPassword + "',"
                            + permissions + ",'" + firstName + "','"
                            + lastName + "','" + email + "','"
                            + city + "','" + address + "','"
                            + phone + "','" + zipCode + "',"
                            + confirmed + ",'" + createdAt + "')");
            //System.out.println(ps);
            result = ps.executeUpdate();
            System.out.println("Dodano uzytkownika " + login + " do bazy danych.");
        } catch (Exception e) {
            System.out.println("Query Status: " + result);
            throw e;
        }
    }

    //-------------------offers-------------------------//
    public void getAllOffers(Connection connection, List<Offer> offers) throws Exception {
        try {
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
                offer.setCreatedAt(rs.getDate("CREATED_AT"));
                offer.setFinishedAt(rs.getDate("FINISHED_AT"));
                offers.add(offer);
            }
            //return userList;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Offer> getUserOffers(Connection connection, User user) throws Exception {
        List<Offer> offers = new ArrayList<>();

        try {

            int id = user.getId();
            // String uname = request.getParameter("uname");
            PreparedStatement ps = connection
                    .prepareStatement("SELECT * FROM offers WHERE OWNER_ID = " + id);
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
                offer.setCreatedAt(rs.getDate("CREATED_AT"));
                offer.setFinishedAt(rs.getDate("FINISHED_AT"));
                offers.add(offer);
            }
    } catch (Exception e) {
        throw e;
    }
        return offers;
    }

    public void saveOffer(Connection connection, Offer offer) throws Exception {

        //int id = offer.getId();
        String title = offer.getTitle();
        String description = offer.getDescription();
        String picturePath = offer.getPicturePath();
        int ownerId = offer.getOwnerId();
        float buyNowPrice = offer.getBuyNowPrice();
        Boolean active = offer.getActive();
        Date createdAt = offer.getCreatedAt();
        Date finishedAt = offer.getFinishedAt();

        int result = 0;
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO offers (TITLE, DESCRIPTION, PICTURE_PATH, OWNER_ID," +
                            "BUY_NOW_PRICE, ACTIVE, CREATED_AT, FINISHED_AT)" +
                            " VALUES('"
                            + title + "','"
                            + description + "','" + picturePath + "',"
                            + ownerId + "," + buyNowPrice + ","
                            + active + ",'" + createdAt + "','"
                            + finishedAt+ "')");
            //System.out.println(ps);
            result = ps.executeUpdate();
            System.out.println("Dodano oferte o tytule " + title + " do bazy danych.");
        } catch (Exception e) {
            System.out.println("Query Status: " + result);
            throw e;
        }
    }

    //------------------bids--------------------//
    public void getAllBids(Connection connection, List<Bid> bids) throws Exception {
        try {
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
                bid.setCreatedAt(rs.getDate("CREATED_AT"));

            }
            //return userList;
        } catch (Exception e) {
            throw e;
        }
    }

    public void saveBid(Connection connection, Bid bid) throws Exception {

        //int id = bid.getId();
        int offerId = bid.getOfferId();
        float price = bid.getPrice();
        int bidderId = bid.getBidderId();
        Date createdAt = bid.getCreatedAt();

        int result = 0;
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO bids (OFFER_ID, BIDDER_ID, PRICE, CREATED_AT)" +
                            " VALUES("
                            + offerId + "," + bidderId + ","
                            + price +  ",'" + createdAt + "')");
            //System.out.println(ps);
            result = ps.executeUpdate();
            System.out.println("Dodano oferte o wartosci " + price + " do aukcji o numerze "+ offerId +" do bazy danych.");
        } catch (Exception e) {
            System.out.println("Query Status: " + result);
            throw e;
        }
    }

}