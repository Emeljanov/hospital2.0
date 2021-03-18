package by.emel.anton.service.implementation;

import by.emel.anton.entity.PatientCard;
import by.emel.anton.repository.PatientCardDao;
import by.emel.anton.service.PatientCardService;
import by.emel.anton.service.exception.EntityNotFoundHospitalServiceException;
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
        return patientCardDao.save(patientCard);
    }

    @Override
    public PatientCard findById(int cardId) {
        return patientCardDao.findById(cardId).orElseThrow(() -> new EntityNotFoundHospitalServiceException("Can't find patient card by id"));
    }

    @Override
    public PatientCard findByPatientId(int patientId) {
        return patientCardDao.findByPatientId(patientId).orElseThrow(() -> new EntityNotFoundHospitalServiceException("Can't find patient card by patient id"));
    }

    @Override
    public List<PatientCard> findAll() {
        return patientCardDao.findAll();
    }

    @Override
    public void deleteById(int id) {
        patientCardDao.deleteById(id);
    }

}
