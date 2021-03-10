package by.emel.anton.api;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

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
