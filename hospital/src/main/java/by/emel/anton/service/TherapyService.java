package by.emel.anton.service;

import by.emel.anton.model.entity.therapy.Therapy;

import java.util.Optional;

public interface TherapyService {

    void saveTherapy(Therapy therapy);

    Optional<Therapy> getTherapyById(int therapyId);
}
