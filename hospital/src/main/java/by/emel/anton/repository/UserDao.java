package by.emel.anton.repository;

import by.emel.anton.model.entity.user.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> getUserByLogin(String login);

    Optional<User> getUserById(int id);

    void updateUser(User user);

    void saveUser(User user);

}
