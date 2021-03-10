package by.emel.anton.facade;

import by.emel.anton.api.ResponsePatientCardDTO;

import java.util.List;

public interface PatientCardFacade {
    void createPatientCard(int patientId);

    ResponsePatientCardDTO getPatientCard(int cardId);

    List<ResponsePatientCardDTO> getAllCards();

    ResponsePatientCardDTO getPatientCardForPatient(int cardId, int patientId);

    ResponsePatientCardDTO getCardForPatient(int patientId);
}
