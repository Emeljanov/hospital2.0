package by.emel.anton.api.v1;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class ResponseTherapyDTO {
    private int id;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private int doctorId;
    private int cardId;
}
