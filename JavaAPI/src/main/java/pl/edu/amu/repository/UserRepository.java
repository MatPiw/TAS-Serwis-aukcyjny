package pl.edu.amu.repository;

import pl.edu.amu.rest.dao.User;
import pl.edu.amu.tools.DBConnection;
import pl.edu.amu.tools.DBOperator;
import pl.edu.amu.tools.DBOperator;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


public class UserRepository {

    private final List<User> users = new ArrayList<>();


    private DBConnection database;
    private DBOperator operator;
    private Connection connection;

    public List<User> getUsers(){
        return users;
    }

    public UserRepository() {
        try {
            database= new DBConnection();
            connection = database.getConnection();
            operator= new DBOperator();
            operator.getAllUsers(connection, users);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("cos nie wyszlo");
        }
        //users.add(new User("huecov", "h123"));
    }

    public User save(User user){
        User dbUser = findByLogin(user.getLogin());
        if (dbUser != null){
            dbUser.setId(user.getId());
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
                operator.saveUser(connection, user);
                users.add(user);
            } catch (Exception e) {
                e.printStackTrace();
            }

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
