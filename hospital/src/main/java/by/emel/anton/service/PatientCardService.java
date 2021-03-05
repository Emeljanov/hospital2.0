package by.emel.anton.service;

import by.emel.anton.model.entity.card.PatientCard;

public interface PatientCardService {

    void savePatientCard(PatientCard patientCard);

    PatientCard getPatientCardById(int cardId);

    PatientCard getPatientCardByPatientId(int patientId);

}
