package by.emel.anton.service.implementation;

import by.emel.anton.model.entity.card.PatientCard;
import by.emel.anton.repository.PatientCardDao;
import by.emel.anton.service.PatientCardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class PatientCardServiceImpl implements PatientCardService {

    private PatientCardDao patientCardDao;

    @Override
    public void savePatientCard(PatientCard patientCard) {
        patientCardDao.savePatientCard(patientCard);
    }

    @Override
    public Optional<PatientCard> getPatientCardById(int cardId) {
        return patientCardDao.getPatientCardById(cardId);
    }

    @Override
    public Optional<PatientCard> getPatientCardByPatientId(int patientId) {
        return patientCardDao.getPatientCardByPatientId(patientId);
    }
}
