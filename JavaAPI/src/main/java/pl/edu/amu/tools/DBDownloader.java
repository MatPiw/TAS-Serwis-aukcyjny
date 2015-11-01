package pl.edu.amu.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import pl.edu.amu.api.dto.User;

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
                userList.add(user);
            }
            //return userList;
        } catch (Exception e) {
            throw e;
        }
    }

}