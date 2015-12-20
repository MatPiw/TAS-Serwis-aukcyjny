package pl.edu.amu.repository;

import pl.edu.amu.rest.model.User;
import pl.edu.amu.tools.DBConnection;
import pl.edu.amu.tools.DBOperator;

import java.sql.Connection;
import java.util.List;

public class UserRepository {

    private DBConnection database;
    private DBOperator operator;
    private Connection connection;


    public List<User> getUsers(){

        return operator.getAllUsers(connection);
    }

    public User findByLogin(String login) {
        return operator.getUser(login, connection);
    }

    public UserRepository() {
        try {
            database= new DBConnection();
            connection = database.getConnection();
            operator= new DBOperator();
            //operator.getAllUsers(connection);


        } catch (Exception e) {
            //e.printStackTrace();
        }
        //users.add(new User("huecov", "h123"));
    }

    public User save(User user){
        User dbUser = operator.getUser(user.getLogin(), connection);
        if (dbUser != null){
            //dbUser.setId(user.getId());
            /*dbUser.setFirstName(user.getFirstName());
            dbUser.setLastName(user.getLastName());
            dbUser.setEmail(user.getEmail());
            dbUser.setPermissions(user.getPermissions());
            dbUser.setAddress(user.getAddress());
            dbUser.setCity(user.getCity());
            dbUser.setPhone(user.getPhone());
            dbUser.setZipCode(user.getZipCode());
            dbUser.setHashPassword(user.getHashPassword());
            dbUser.setCreatedAt(user.getCreatedAt());
            dbUser.setConfirmed(user.getConfirmed());*/
        } else {
            try {
                user = operator.saveUser(connection, user);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return user;
    }

    public User update(User user) {
        User dbUser = operator.getUser(user.getLogin(), connection);
        if (dbUser != null) {
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
            dbUser = operator.updateUser(dbUser, connection);
        }
        return dbUser;
    }
    
    /*public void remove(String login){
        User user = operator.getUser());
        if (user != null){
            //users.remove(user);
        }
    }*/
    
}
