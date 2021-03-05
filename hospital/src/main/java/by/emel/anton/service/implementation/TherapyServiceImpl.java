package by.emel.anton.service.implementation;

import by.emel.anton.model.entity.therapy.Therapy;
import by.emel.anton.repository.TherapyDao;
import by.emel.anton.service.TherapyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class TherapyServiceImpl implements TherapyService {

    private TherapyDao therapyDao;

    @Override
    public void saveTherapy(Therapy therapy) {
        therapyDao.saveTherapy(therapy);
    }

    @Override
    public Optional<Therapy> getTherapyById(int therapyId) {
        return therapyDao.getTherapy(therapyId);
    }
}
