package by.emel.anton.service.implementation;

import by.emel.anton.model.entity.user.User;
import by.emel.anton.repository.UserDao;
import by.emel.anton.service.UserService;
import by.emel.anton.service.exception.UserServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Override
    public void saveUser(User user) {
        try {
            userDao.saveUser(user);
        } catch (RuntimeException e) {
            throw new UserServiceException("Can't save user");
        }
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
    public void changeUserActiveStatus(int userId, boolean isActive) {
        User user = userDao.getUserById(userId).orElseThrow(() -> new UserServiceException("Can't find user by Id"));
        user.setActive(isActive);
        userDao.updateUser(user);
    }

    @Override
    public void updateUser(User user) {
        try {
            userDao.updateUser(user);
        } catch (RuntimeException e) {
            throw new UserServiceException("Can't update user");
        }
    }
}
