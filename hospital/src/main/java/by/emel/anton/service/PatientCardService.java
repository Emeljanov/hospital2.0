package by.emel.anton.service;

import by.emel.anton.entity.PatientCard;

import java.util.List;

public interface PatientCardService {

    void savePatientCard(PatientCard patientCard);

    PatientCard getPatientCardById(int cardId);

    PatientCard getPatientCardByPatientId(int patientId);

    List<PatientCard> getAllCards();

    PatientCard getPatientCardForPatient(int cardId, int patientId);

}
