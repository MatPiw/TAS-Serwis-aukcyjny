package pl.edu.amu.rest.database;

import pl.edu.amu.rest.model.User;

import java.util.Collection;

/**
 * Created by Altenfrost on 2015-12-31.
 */
public interface UserDatabase {
    User getUser(String id);

    User updateUser(String id, User user);

    User saveUser(User user);

    User getUserbyLogin(String login);

    Boolean deleteUser(final String uid);

    Collection<User> getUsers();
}