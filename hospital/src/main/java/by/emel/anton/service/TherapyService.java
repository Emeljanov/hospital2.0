package by.emel.anton.service;

import by.emel.anton.entity.Therapy;

import java.util.List;

public interface TherapyService {

    Therapy save(Therapy therapy);

    Therapy findById(int therapyId);

    List<Therapy> findAll();

    Therapy findByIdForPatientId(int therapyId, int patientId);

    List<Therapy> getAllForPatientId(int patientId);
}
