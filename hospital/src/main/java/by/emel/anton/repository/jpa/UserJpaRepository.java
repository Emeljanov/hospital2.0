package by.emel.anton.repository.jpa;

import by.emel.anton.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserJpaRepository extends JpaRepository<User, Integer> {

    Optional<User> findByLogin(String login);

}
