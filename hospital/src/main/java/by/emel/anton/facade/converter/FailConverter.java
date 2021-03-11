package by.emel.anton.facade.converter;

import by.emel.anton.api.v1.ResponseFailDTO;
import org.springframework.stereotype.Component;

@Component
public class FailConverter implements Converter<Exception, ResponseFailDTO> {
    @Override
    public ResponseFailDTO convert(Exception from) {
        return ResponseFailDTO.builder()
                .name(from.getClass().getSimpleName())
                .message(from.getMessage())
                .build();
    }
}
