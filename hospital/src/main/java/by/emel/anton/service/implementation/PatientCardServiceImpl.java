package by.emel.anton.service.implementation;

import by.emel.anton.model.entity.card.PatientCard;
import by.emel.anton.repository.PatientCardDao;
import by.emel.anton.service.PatientCardService;
import by.emel.anton.service.exception.PatientCardServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class PatientCardServiceImpl implements PatientCardService {

    private PatientCardDao patientCardDao;

    @Override
    public void savePatientCard(PatientCard patientCard) {
        try {
            patientCardDao.savePatientCard(patientCard);
        } catch (RuntimeException e) {
            throw new PatientCardServiceException("Can't save patient card");
        }
    }

    @Override
    public PatientCard getPatientCardById(int cardId) {
        return patientCardDao.getPatientCardById(cardId).orElseThrow(() -> new PatientCardServiceException("Can't get patient card by id"));
    }

    @Override
    public PatientCard getPatientCardByPatientId(int patientId) {
        return patientCardDao.getPatientCardByPatientId(patientId).orElseThrow(() -> new PatientCardServiceException("Can't get patient card by patient id"));
    }
}
