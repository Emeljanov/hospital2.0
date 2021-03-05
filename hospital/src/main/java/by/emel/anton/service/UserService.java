package by.emel.anton.service;

import by.emel.anton.model.entity.user.User;

import java.util.Optional;

public interface UserService {

    void saveUser(User user);

    Optional<User> getUserById(int id);

    Optional<User> getUserByLogin(String login);

    void changeUserActiveStatus(int userId, boolean isActive);

    void updateUser(User user);

}
