package by.emel.anton.service.implementation;

import by.emel.anton.model.entity.user.User;
import by.emel.anton.repository.UserDao;
import by.emel.anton.service.UserService;
import by.emel.anton.service.exception.UserServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Override
    public User saveUser(User user) {
        return userDao.saveUser(user).orElseThrow(() -> new UserServiceException("Can't save user"));
    }

    @Override
    public User getUserById(int id) {
        return userDao.getUserById(id).orElseThrow(() -> new UserServiceException("Can't get user by id"));
    }

    @Override
    public User getUserByLogin(String login) {
        return userDao.getUserByLogin(login).orElseThrow(() -> new UserServiceException("Can't get user by login"));
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public void changeUserActiveStatus(int userId, boolean isActive) {
        User user = userDao.getUserById(userId).orElseThrow(() -> new UserServiceException("Can't find user by Id"));
        user.setActive(isActive);
        userDao.updateUser(user);
    }

    @Override
    public User updateUser(User user) {
        return userDao.updateUser(user).orElseThrow(() -> new UserServiceException("Can't update user"));
    }

    @Override
    public void deleteUserById(int id) {
        userDao.deleteUserById(id);
    }
}
