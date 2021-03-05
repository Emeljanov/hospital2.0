package by.emel.anton.repository.implementation;

import by.emel.anton.model.entity.card.PatientCard;
import by.emel.anton.repository.PatientCardDao;
import by.emel.anton.repository.implementation.jparepository.PatientCardJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class PatientCardDaoImpl implements PatientCardDao {

    private PatientCardJpaRepository patientCardJpaRepository;

    @Override
    public void savePatientCard(PatientCard patientCard) {
        patientCardJpaRepository.save(patientCard);
    }

    @Override
    public Optional<PatientCard> getPatientCardById(int id) {
        return patientCardJpaRepository.findById(id);
    }

    @Override
    public Optional<PatientCard> getPatientCardByPatientId(int patientId) {
        return patientCardJpaRepository.findByPatientId(patientId);
    }
}
