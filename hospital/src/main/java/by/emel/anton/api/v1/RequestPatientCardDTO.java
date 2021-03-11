package by.emel.anton.api.v1;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class RequestPatientCardDTO {
    @NotNull(message = "field patientId = null")
    private int patientId;
}
