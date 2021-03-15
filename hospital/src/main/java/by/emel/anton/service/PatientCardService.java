package by.emel.anton.service;

import by.emel.anton.entity.PatientCard;

import java.util.List;

public interface PatientCardService {

    PatientCard save(PatientCard patientCard);

    PatientCard findById(int cardId);

    PatientCard findByPatientId(int patientId);

    List<PatientCard> findAll();

}
