package by.emel.anton.service;

import by.emel.anton.model.entity.therapy.Therapy;

import java.util.List;

public interface TherapyService {

    Therapy saveTherapy(Therapy therapy);

    Therapy getTherapyById(int therapyId);

    List<Therapy> getAllTherapies();

    Therapy getTherapyByIdForPatient(int therapyId, int patientId);
}
