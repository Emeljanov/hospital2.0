package by.emel.anton.repository.jpa;

import by.emel.anton.entity.PatientCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface PatientCardJpaRepository extends JpaRepository<PatientCard, Integer> {

    Optional<PatientCard> findByPatientId(int patientId);

    Optional<PatientCard> findByIdAndPatientId(int id, int patientId);

}
