package by.emel.anton.service.implementation;

import by.emel.anton.entity.Therapy;
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
    public Therapy save(Therapy therapy) {
        return therapyDao.save(therapy).orElseThrow(() -> new TherapyServiceException("Can't save therapy"));
    }

    @Override
    public Therapy findById(int therapyId) {
        return therapyDao.findById(therapyId).orElseThrow(() -> new TherapyServiceException("Can't find therapy by id"));
    }

    @Override
    public List<Therapy> findAll() {
        return therapyDao.findAll();
    }

    @Override
    public Therapy findByIdForPatientId(int therapyId, int patientId) {
        return therapyDao.findByIdForPatientId(therapyId, patientId).orElseThrow(() -> new TherapyServiceException("Can't find therapy for this patient"));
    }

    @Override
    public List<Therapy> getAllForPatientId(int patientId) {
        return therapyDao.findAllByPatientId(patientId);
    }


}
