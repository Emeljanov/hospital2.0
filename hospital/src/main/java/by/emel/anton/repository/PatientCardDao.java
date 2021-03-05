package by.emel.anton.repository;

import by.emel.anton.model.entity.card.PatientCard;

import java.util.Optional;

public interface PatientCardDao {
    void savePatientCard(PatientCard patientCard);

    Optional<PatientCard> getPatientCardById(int id);

    Optional<PatientCard> getPatientCardByPatientId(int patientId);

}
