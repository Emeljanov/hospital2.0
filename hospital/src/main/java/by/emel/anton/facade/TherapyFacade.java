package by.emel.anton.facade;

import by.emel.anton.api.RequestTherapyDTO;
import by.emel.anton.api.ResponseTherapyDTO;

import java.util.List;

public interface TherapyFacade {

    ResponseTherapyDTO saveTherapy(RequestTherapyDTO requestTherapyDTO, int doctorId);

    ResponseTherapyDTO getTherapy(int id);

    List<ResponseTherapyDTO> getAllTherapies();

    ResponseTherapyDTO getTherapyForPatient(int therapyId, int patientId);

    List<ResponseTherapyDTO> getAllTherapiesForPatient(int patientId);

}
