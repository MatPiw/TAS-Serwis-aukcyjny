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


    private DBConnection database;
    private DBDownloader downloader;
    private Connection connection;

    public List<User> getUsers(){
        return users;
    }

    public UserRepository() {
        try {
            database= new DBConnection();
            connection = database.getConnection();
            downloader= new DBDownloader();
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
            dbUser.setConfirmed(user.getConfirmed());
        } else {
            try {
                downloader.saveUser(connection, user);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
