package by.emel.anton.facade;

import by.emel.anton.api.v1.ResponsePatientCardDTO;

import java.util.List;

public interface PatientCardFacade {
    void createForPatientId(int patientId);

    ResponsePatientCardDTO findById(int cardId);

    List<ResponsePatientCardDTO> findAll();

    ResponsePatientCardDTO findByPatientId(int patientId);
}
