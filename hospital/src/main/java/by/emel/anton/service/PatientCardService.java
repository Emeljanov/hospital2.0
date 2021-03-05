package by.emel.anton.service;

import by.emel.anton.model.entity.card.PatientCard;

import java.util.Optional;

public interface PatientCardService {

    void savePatientCard(PatientCard patientCard);

    Optional<PatientCard> getPatientCardById(int cardId);

    Optional<PatientCard> getPatientCardByPatientId(int patientId);

}
