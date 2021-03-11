package by.emel.anton.service.implementation;

import by.emel.anton.entity.User;
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
    public User save(User user) {
        return userDao.save(user).orElseThrow(() -> new UserServiceException("Can't save user"));
    }

    @Override
    public User findById(int id) {
        return userDao.findById(id).orElseThrow(() -> new UserServiceException("Can't find user by id"));
    }

    @Override
    public User findByLogin(String login) {
        return userDao.findByLogin(login).orElseThrow(() -> new UserServiceException("Can't find user by login"));
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void changeActiveStatusById(int userId, boolean isActive) {
        User user = userDao.findById(userId).orElseThrow(() -> new UserServiceException("Can't find user by Id"));
        user.setActive(isActive);
        userDao.update(user);
    }

    @Override
    public User update(User user) {
        return userDao.update(user).orElseThrow(() -> new UserServiceException("Can't update user"));
    }

    @Override
    public void deleteById(int id) {
        userDao.deleteById(id);
    }
}
