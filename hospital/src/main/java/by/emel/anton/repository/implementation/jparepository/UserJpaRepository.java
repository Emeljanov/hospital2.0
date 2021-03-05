package by.emel.anton.repository.implementation.jparepository;

import by.emel.anton.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserJpaRepository extends JpaRepository<User, Integer> {

    Optional<User> findByLogin(String login);

}
