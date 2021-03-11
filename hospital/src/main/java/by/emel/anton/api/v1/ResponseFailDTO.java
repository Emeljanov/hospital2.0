package by.emel.anton.api.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class ResponseFailDTO {
    private String name;
    private String message;
}
