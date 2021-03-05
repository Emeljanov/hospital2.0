package by.emel.anton.service;

import by.emel.anton.model.entity.therapy.Therapy;

public interface TherapyService {

    void saveTherapy(Therapy therapy);

    Therapy getTherapyById(int therapyId);
}
