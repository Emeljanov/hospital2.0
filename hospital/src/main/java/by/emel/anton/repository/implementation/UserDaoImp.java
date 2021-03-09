package by.emel.anton.repository.implementation;

import by.emel.anton.model.entity.user.User;
import by.emel.anton.repository.UserDao;
import by.emel.anton.repository.implementation.jparepository.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserDaoImp implements UserDao {

    private UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> getUserByLogin(String login) {
        return userJpaRepository.findByLogin(login);
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userJpaRepository.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
        return Optional.of(userJpaRepository.save(user));
    }

    @Override
    public Optional<User> saveUser(User user) {
        return Optional.of(userJpaRepository.save(user));

    }

    @Override
    public List<User> getAllUsers() {
        return userJpaRepository.findAll();
    }

    @Override
    public void deleteUserById(int id) {
        userJpaRepository.deleteById(id);
    }

}
