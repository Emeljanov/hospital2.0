package by.emel.anton.api.card;

import by.emel.anton.api.therapy.ResponseTherapyDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ResponsePatientCard {
    private int id;
    private int patientId;
    private List<ResponseTherapyDTO> therapyDTOList;
}
