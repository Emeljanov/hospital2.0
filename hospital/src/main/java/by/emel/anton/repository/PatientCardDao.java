package by.emel.anton.repository;

import by.emel.anton.entity.PatientCard;

import java.util.List;
import java.util.Optional;

public interface PatientCardDao {
    void savePatientCard(PatientCard patientCard);

    Optional<PatientCard> getPatientCardById(int id);

    Optional<PatientCard> getPatientCardByPatientId(int patientId);

    List<PatientCard> getAllCards();

}
