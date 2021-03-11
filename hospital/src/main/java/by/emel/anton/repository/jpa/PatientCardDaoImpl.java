package by.emel.anton.repository.jpa;

import by.emel.anton.entity.PatientCard;
import by.emel.anton.repository.PatientCardDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PatientCardDaoImpl implements PatientCardDao {

    private final PatientCardJpaRepository patientCardJpaRepository;

    @Override
    public Optional<PatientCard> save(PatientCard patientCard) {
        return Optional.of(patientCardJpaRepository.save(patientCard));
    }

    @Override
    public Optional<PatientCard> findById(int id) {
        return patientCardJpaRepository.findById(id);
    }

    @Override
    public Optional<PatientCard> findByPatientId(int patientId) {
        return patientCardJpaRepository.findByPatientId(patientId);
    }

    @Override
    public List<PatientCard> findAll() {
        return patientCardJpaRepository.findAll();
    }

}
