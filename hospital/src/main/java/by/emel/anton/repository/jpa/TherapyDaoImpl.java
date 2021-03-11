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
    public Optional<Therapy> save(Therapy therapy) {
       return Optional.of(therapyJpaRepository.save(therapy));
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
    public Optional<Therapy> findByIdForPatientId(int therapyId, int patientId) {
        return therapyJpaRepository.findTherapyByIdAndPatientId(therapyId,patientId);
    }

    @Override
    public List<Therapy> findAllByPatientId(int patientId) {
        return therapyJpaRepository.findAllByPatientId(patientId);
    }
}
