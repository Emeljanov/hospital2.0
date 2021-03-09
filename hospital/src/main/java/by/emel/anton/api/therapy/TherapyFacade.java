package by.emel.anton.api.therapy;

import java.util.List;

public interface TherapyFacade {

    ResponseTherapyDTO saveTherapy(RequestTherapyDTO requestTherapyDTO, int doctorId);

    ResponseTherapyDTO getTherapy(int id);

    List<ResponseTherapyDTO> getAllTherapies();

    ResponseTherapyDTO getTherapyForPatient(int therapyId, int patientId);

}
