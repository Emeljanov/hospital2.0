package by.emel.anton.repository;

import by.emel.anton.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> getUserByLogin(String login);

    Optional<User> getUserById(int id);

    Optional<User> updateUser(User user);

    Optional<User> saveUser(User user);

    List<User> getAllUsers();

    void deleteUserById(int id);

}
