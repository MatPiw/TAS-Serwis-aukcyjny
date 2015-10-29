package pl.edu.amu.rest;

import pl.edu.amu.api.UserResource;
import pl.edu.amu.api.dto.User;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adrian.perek on 2015-20-10.
 */
//@Singleton
public class UserResourceImpl implements UserResource {
    
    private static final List<User> users = new ArrayList<>();

    @Override
    public List<User> getUsers(){
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
