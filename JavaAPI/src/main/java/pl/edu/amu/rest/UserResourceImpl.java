package pl.edu.amu.rest;

import pl.edu.amu.api.UserResource;
import pl.edu.amu.api.dto.User;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import pl.edu.amu.tools.DBConnection;
import pl.edu.amu.tools.DBDownloader;

//@Singleton
public class UserResourceImpl implements UserResource {
    
    private static final List<User> users = new ArrayList<>();

    @Override
    public List<User> getUsers(){
        ArrayList<User> userList = null;
        try {
            DBConnection database= new DBConnection();
            Connection connection = database.getConnection();
            DBDownloader downloader= new DBDownloader();
            downloader.getAllUsers(connection, users);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("coœ nie wysz³o");
        }
        return users;
    }

    @Override
    public User saveUser(User user){
        users.add(user);
        return user;
    }

    @Override
    public User getUser(final String login){
        return users
                .stream()
                .filter(user -> login.equals(user.getLogin()))
                .findFirst()
                .orElseThrow(NotFoundException::new);
   }

}
