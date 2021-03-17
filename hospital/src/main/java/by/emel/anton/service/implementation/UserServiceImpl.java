package by.emel.anton.service.implementation;

import by.emel.anton.entity.User;
import by.emel.anton.repository.UserDao;
import by.emel.anton.service.UserService;
import by.emel.anton.service.exception.EntityNotFoundHospitalServiceException;
import by.emel.anton.service.exception.UserServiceException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//@AllArgsConstructor
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    public User save(User user) {
        return userDao.save(user);
    }

    @Override
    public User findById(int id) {
        return userDao.findById(id).orElseThrow(() -> new EntityNotFoundHospitalServiceException("Can't find user by id"));
    }

    @Override
    public User findByLogin(String login) {
        return userDao.findByLogin(login).orElseThrow(() -> new EntityNotFoundHospitalServiceException("Can't find user by login"));
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User changeActiveStatusById(int userId, boolean isActive) {
        User user = findById(userId);
        user.setActive(isActive);
        return update(user);
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public void deleteById(int id) {
        userDao.deleteById(id);
    }
}
