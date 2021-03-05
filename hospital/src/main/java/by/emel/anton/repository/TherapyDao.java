package by.emel.anton.repository;

import by.emel.anton.model.entity.therapy.Therapy;

import java.util.Optional;

public interface TherapyDao {

    void saveTherapy(Therapy therapy);

    Optional<Therapy> getTherapy(int id);

}
