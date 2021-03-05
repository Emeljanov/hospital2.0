package by.emel.anton.repository.implementation;

import by.emel.anton.model.entity.therapy.Therapy;
import by.emel.anton.repository.TherapyDao;
import by.emel.anton.repository.implementation.jparepository.TherapyJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class TherapyDaoImpl implements TherapyDao {

    private TherapyJpaRepository therapyJpaRepository;

    @Override
    public void saveTherapy(Therapy therapy) {
        therapyJpaRepository.save(therapy);
    }

    @Override
    public Optional<Therapy> getTherapy(int id) {
        return therapyJpaRepository.findById(id);
    }
}
