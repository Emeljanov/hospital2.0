package by.emel.anton.api.v1;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder
@Getter
public class RequestTherapyDTO {

    @NotNull(message = "field description = null")
    private String description;
    @NotNull(message = "field startDate = null")
    private LocalDate startDate;
    @NotNull(message = "field endDate = null")
    private LocalDate endDate;
    @NotNull(message = "field patientId = null")
    private int patientId;

}
