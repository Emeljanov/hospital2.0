package by.emel.anton.service;

import by.emel.anton.model.entity.user.User;

public interface UserService {

    void saveUser(User user);

    User getUserById(int id);

    User getUserByLogin(String login);

    void changeUserActiveStatus(int userId, boolean isActive);

    void updateUser(User user);

}
