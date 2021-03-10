package by.emel.anton.service;

import by.emel.anton.entity.Therapy;

import java.util.List;

public interface TherapyService {

    Therapy saveTherapy(Therapy therapy);

    Therapy getTherapyById(int therapyId);

    List<Therapy> getAllTherapies();

    Therapy getTherapyByIdForPatient(int therapyId, int patientId);

    List<Therapy> getAllTherapiesForPatient(int patientId);
}
