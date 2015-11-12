package pl.edu.amu.tools;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import pl.edu.amu.rest.dao.User;

public class DBDownloader {

    public void getAllUsers(Connection connection, List<User> userList) throws Exception {
        try {
            // String uname = request.getParameter("uname");
            PreparedStatement ps = connection
                    .prepareStatement("SELECT * FROM users");
            // ps.setString(1,uname);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
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
                    "INSERT INTO users VALUES('"
                            + login + "','" + hashPassword + "',"
                            + permissions + ",'" + firstName + "','"
                            + lastName + "','" + email + "','"
                            + city + "','" + address + "','"
                            + phone + "','" + zipCode + "',"
                            + confirmed + ",'" + createdAt + "')");
            System.out.println(ps);
            result = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Query Status: " + result);
            throw e;
        }
    }

}