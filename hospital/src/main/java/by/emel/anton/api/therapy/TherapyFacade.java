package by.emel.anton.api.therapy;

public interface TherapyFacade {

    void saveTherapy(RequestTherapyDTO requestTherapyDTO, int patientId, int doctorId);

    ResponseTherapyDTO getTherapy(int id);

}
