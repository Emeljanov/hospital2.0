package by.emel.anton.service;

import by.emel.anton.model.entity.user.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    User getUserById(int id);

    User getUserByLogin(String login);

    List<User> getAllUsers();

    void changeUserActiveStatus(int userId, boolean isActive);

    User updateUser(User user);

    void deleteUserById(int id);

}
