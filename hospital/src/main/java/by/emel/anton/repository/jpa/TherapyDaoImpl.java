package by.emel.anton.repository.jpa;

import by.emel.anton.entity.Therapy;
import by.emel.anton.repository.TherapyDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TherapyDaoImpl implements TherapyDao {

    private TherapyJpaRepository therapyJpaRepository;

    @Override
    public Therapy save(Therapy therapy) {
        return therapyJpaRepository.save(therapy);
    }

    @Override
    public Optional<Therapy> findById(int id) {
        return therapyJpaRepository.findById(id);
    }

    @Override
    public List<Therapy> findAll() {
        return therapyJpaRepository.findAll();
    }

    @Override
    public Optional<Therapy> findByIdForCardId(int therapyId, int cardId) {
        return therapyJpaRepository.findByIdAndCardId(therapyId, cardId);
    }

    @Override
    public List<Therapy> findAllByCardId(int patientId) {
        return therapyJpaRepository.findAllByCardId(patientId);
    }

    @Override
    public void deleteAll() {
        therapyJpaRepository.deleteAll();
    }
}
