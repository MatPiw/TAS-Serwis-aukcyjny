package pl.edu.amu.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import pl.edu.amu.api.dto.User;

public class DBDownloader {

    public ArrayList<User> getAllUsers(Connection connection) throws Exception {
        ArrayList<User> userList = new ArrayList<User>();
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
                userList.add(user);
            }
            return userList;
        } catch (Exception e) {
            throw e;
        }
    }

}