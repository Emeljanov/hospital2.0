package by.emel.anton.repository.jpa;

import by.emel.anton.entity.PatientCard;
import by.emel.anton.repository.PatientCardDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Override
    public List<PatientCard> getAllCards() {
        return patientCardJpaRepository.findAll();
    }

    @Override
    public Optional<PatientCard> getPatientCardForPatient(int cardId, int patientId) {
        return patientCardJpaRepository.findByIdAndPatientId(cardId,patientId);
    }
}
