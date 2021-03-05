package by.emel.anton.repository.implementation;

import by.emel.anton.model.entity.user.User;
import by.emel.anton.repository.UserDao;
import by.emel.anton.repository.implementation.jparepository.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

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
    public void updateUser(User user) {
        userJpaRepository.save(user);
    }

    @Override
    public void saveUser(User user) {
        userJpaRepository.save(user);
    }

}
