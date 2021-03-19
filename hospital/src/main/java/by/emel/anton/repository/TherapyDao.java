package by.emel.anton.repository;

import by.emel.anton.entity.Therapy;

import java.util.List;
import java.util.Optional;

public interface TherapyDao {

    Therapy save(Therapy therapy);

    Optional<Therapy> findById(int id);

    List<Therapy> findAll();

    Optional<Therapy> findByIdForCardId(int therapyId, int cardId);

    List<Therapy> findAllByCardId(int patientId);

    void deleteAll();

}
