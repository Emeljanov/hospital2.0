package by.emel.anton.repository.implementation.jparepository;

import by.emel.anton.model.entity.card.PatientCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface PatientCardJpaRepository extends JpaRepository<PatientCard, Integer> {

    Optional<PatientCard> findByPatientId(int patientId);

}
