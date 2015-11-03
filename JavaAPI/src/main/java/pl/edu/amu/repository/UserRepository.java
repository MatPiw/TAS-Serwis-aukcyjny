package pl.edu.amu.repository;

import pl.edu.amu.rest.dao.User;
import pl.edu.amu.tools.DBConnection;
import pl.edu.amu.tools.DBDownloader;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adrian.perek on 2015-03-22.
 */
public class UserRepository {

    private final List<User> users = new ArrayList<>();

    public List<User> getUsers(){
        return users;
    }

    public UserRepository() {
        try {
            DBConnection database= new DBConnection();
            Connection connection = database.getConnection();
            DBDownloader downloader= new DBDownloader();
            downloader.getAllUsers(connection, users);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("coœ nie wysz³o");
        }
        //users.add(new User("huecov", "h123"));
    }

    public User save(User user){
        User dbUser = findByLogin(user.getLogin());
        if (dbUser != null){
            dbUser.setFirstName(user.getFirstName());
            dbUser.setLastName(user.getLastName());
            dbUser.setEmail(user.getEmail());
            dbUser.setPermissions(user.getPermissions());
            dbUser.setAddress(user.getAddress());
            dbUser.setCity(user.getCity());
            dbUser.setPhone(user.getPhone());
            dbUser.setZipCode(user.getZipCode());
            dbUser.setHashPassword(user.getHashPassword());
            dbUser.setCreatedAt(user.getCreatedAt());
        } else {
            users.add(user);
        }
        return user;
    }
    
    public User findByLogin(String login) {
        for(User user : users){
            if (login.equalsIgnoreCase(user.getLogin())){
                return user;
            }
        }
        return null;
    }
    
    public void remove(String login){
        User user = findByLogin(login);
        if (user != null){
            users.remove(user);
        }
    }
    
}
