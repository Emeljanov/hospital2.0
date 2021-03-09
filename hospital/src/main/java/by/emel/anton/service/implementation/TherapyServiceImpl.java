package by.emel.anton.service.implementation;

import by.emel.anton.model.entity.therapy.Therapy;
import by.emel.anton.repository.TherapyDao;
import by.emel.anton.service.TherapyService;
import by.emel.anton.service.exception.TherapyServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class TherapyServiceImpl implements TherapyService {

    private TherapyDao therapyDao;

    @Override
    public Therapy saveTherapy(Therapy therapy) {
           return therapyDao.saveTherapy(therapy).orElseThrow(() -> new TherapyServiceException("Can't save therapy"));
    }

    @Override
    public Therapy getTherapyById(int therapyId) {
        return therapyDao.getTherapy(therapyId).orElseThrow(() -> new TherapyServiceException("Can't get therapy by id"));
    }

    @Override
    public List<Therapy> getAllTherapies() {
        return therapyDao.getAllTherapies();
    }

    @Override
    public Therapy getTherapyByIdForPatient(int therapyId, int patientId) {
        return therapyDao.getTherapyByIdForPatient(therapyId,patientId).orElseThrow(() -> new TherapyServiceException("Can't get therapy for this patient"));
    }


}
