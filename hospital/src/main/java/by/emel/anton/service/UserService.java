package by.emel.anton.service;

import by.emel.anton.entity.User;

import java.util.List;

public interface UserService {

    User save(User user);

    User findById(int id);

    User findByLogin(String login);

    List<User> findAll();

    void changeActiveStatusById(int userId, boolean isActive);

    User update(User user);

    void deleteById(int id);

}
