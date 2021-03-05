package by.emel.anton.service.implementation;

import by.emel.anton.model.entity.therapy.Therapy;
import by.emel.anton.repository.TherapyDao;
import by.emel.anton.service.TherapyService;
import by.emel.anton.service.exception.TherapyServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class TherapyServiceImpl implements TherapyService {

    private TherapyDao therapyDao;

    @Override
    public void saveTherapy(Therapy therapy) {
        try {
            therapyDao.saveTherapy(therapy);
        } catch (RuntimeException e) {
            throw new TherapyServiceException("Can't save therapy");
        }
    }

    @Override
    public Therapy getTherapyById(int therapyId) {
        return therapyDao.getTherapy(therapyId).orElseThrow(() -> new TherapyServiceException("Can't get therapy by id"));
    }
}
