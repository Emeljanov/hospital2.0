package by.emel.anton.repository;

import by.emel.anton.entity.PatientCard;

import java.util.List;
import java.util.Optional;

public interface PatientCardDao {
    PatientCard save(PatientCard patientCard);

    Optional<PatientCard> findById(int id);

    Optional<PatientCard> findByPatientId(int patientId);

    List<PatientCard> findAll();

    void deleteById(int id);

    void deleteAll();

}
