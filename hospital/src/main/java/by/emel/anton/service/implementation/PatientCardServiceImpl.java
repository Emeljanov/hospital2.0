package by.emel.anton.service.implementation;

import by.emel.anton.entity.PatientCard;
import by.emel.anton.repository.PatientCardDao;
import by.emel.anton.service.PatientCardService;
import by.emel.anton.service.exception.PatientCardServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class PatientCardServiceImpl implements PatientCardService {

    private PatientCardDao patientCardDao;

    @Override
    public PatientCard save(PatientCard patientCard) {
        return patientCardDao.savePatientCard(patientCard).orElseThrow(() -> new PatientCardServiceException("Can't save patient card"));
    }

    @Override
    public PatientCard findById(int cardId) {
        return patientCardDao.findById(cardId).orElseThrow(() -> new PatientCardServiceException("Can't find patient card by id"));
    }

    @Override
    public PatientCard findByPatientId(int patientId) {
        return patientCardDao.findByPatientId(patientId).orElseThrow(() -> new PatientCardServiceException("Can't find patient card by patient id"));
    }

    @Override
    public List<PatientCard> getAll() {
        return patientCardDao.findAll();
    }

}
