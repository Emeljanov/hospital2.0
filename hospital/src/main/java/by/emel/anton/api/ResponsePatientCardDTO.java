package by.emel.anton.api;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ResponsePatientCardDTO {
    private int id;
    private int patientId;
    private List<ResponseTherapyDTO> therapyDTOList;
}
