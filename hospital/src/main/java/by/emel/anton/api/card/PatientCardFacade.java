package by.emel.anton.api.card;

public interface PatientCardFacade {
    void createPatientCard(int patientId);

    ResponsePatientCard getPatientCard(int cardId);
}
