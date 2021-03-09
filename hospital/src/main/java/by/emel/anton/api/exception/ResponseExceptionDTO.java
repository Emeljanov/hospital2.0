package by.emel.anton.api.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class ResponseExceptionDTO {
    private String name;
    private String message;
}
