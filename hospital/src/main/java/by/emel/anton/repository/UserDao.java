package by.emel.anton.repository;

import by.emel.anton.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> findByLogin(String login);

    Optional<User> findById(int id);

    Optional<User> update(User user);

    Optional<User> save(User user);

    List<User> findAll();

    void deleteById(int id);

}
