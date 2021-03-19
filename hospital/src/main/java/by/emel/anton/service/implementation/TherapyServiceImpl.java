package by.emel.anton.service.implementation;

import by.emel.anton.entity.Therapy;
import by.emel.anton.repository.TherapyDao;
import by.emel.anton.service.TherapyService;
import by.emel.anton.service.exception.EntityNotFoundHospitalServiceException;
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
        return therapyDao.save(therapy);
    }

    @Override
    public Therapy findById(int therapyId) {
        return therapyDao.findById(therapyId).orElseThrow(() -> new EntityNotFoundHospitalServiceException("Can't find therapy by id"));
    }

    @Override
    public List<Therapy> findAll() {
        return therapyDao.findAll();
    }

    @Override
    public Therapy findByIdForCardId(int therapyId, int cardId) {
        return therapyDao.findByIdForCardId(therapyId, cardId).orElseThrow(() -> new EntityNotFoundHospitalServiceException("Can't find therapy for this patient"));
    }

    @Override
    public List<Therapy> findAllForCardId(int patientId) {
        return therapyDao.findAllByCardId(patientId);
    }


}
