package by.emel.anton.repository;

import by.emel.anton.entity.Therapy;

import java.util.List;
import java.util.Optional;

public interface TherapyDao {

    Optional<Therapy> save(Therapy therapy);

    Optional<Therapy> findById(int id);

    List<Therapy> findAll();

    Optional<Therapy> findByIdForPatientId(int therapyId, int patientId);

    List<Therapy> findAllByPatientId(int patientId);

}
