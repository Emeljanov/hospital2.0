package by.emel.anton.facade;

import by.emel.anton.api.v1.RequestTherapyDTO;
import by.emel.anton.api.v1.ResponseTherapyDTO;

import java.util.List;

public interface TherapyFacade {

    ResponseTherapyDTO save(RequestTherapyDTO requestTherapyDTO, int doctorId);

    ResponseTherapyDTO find(int id);

    List<ResponseTherapyDTO> findAll();

    ResponseTherapyDTO findByIdForCardId(int therapyId, int patientId);

    List<ResponseTherapyDTO> findAllByCardId(int patientId);

}
