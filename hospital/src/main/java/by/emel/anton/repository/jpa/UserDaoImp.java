package by.emel.anton.repository.jpa;

import by.emel.anton.entity.User;
import by.emel.anton.repository.UserDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserDaoImp implements UserDao {

    private UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findByLogin(String login) {
        return userJpaRepository.findByLogin(login);
    }

    @Override
    public Optional<User> findById(int id) {
        return userJpaRepository.findById(id);
    }

    @Override
    public User update(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userJpaRepository.findAll();
    }

    @Override
    public void deleteById(int id) {
        userJpaRepository.deleteById(id);
    }

}
