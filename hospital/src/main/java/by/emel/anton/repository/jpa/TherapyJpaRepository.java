package by.emel.anton.repository.jpa;

import by.emel.anton.entity.Therapy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface TherapyJpaRepository extends JpaRepository<Therapy,Integer> {

    Optional<Therapy> findByIdAndCardId(int id, int cardId);

    List<Therapy> findAllByCardId(int patientId);
}
