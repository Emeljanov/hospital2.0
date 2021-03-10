package by.emel.anton.repository;

import by.emel.anton.entity.Therapy;

import java.util.List;
import java.util.Optional;

public interface TherapyDao {

    Optional<Therapy> saveTherapy(Therapy therapy);

    Optional<Therapy> getTherapy(int id);

    List<Therapy> getAllTherapies();

    Optional<Therapy> getTherapyByIdForPatient(int therapyId, int patientId);

    List<Therapy> getAllTherapiesForPatient(int patientId);

}
