package by.emel.anton.api.converter;

import by.emel.anton.api.exception.ResponseExceptionDTO;
import org.springframework.stereotype.Component;

@Component
public class ExceptionConverter implements Converter<Exception, ResponseExceptionDTO> {
    @Override
    public ResponseExceptionDTO convert(Exception from) {
        return ResponseExceptionDTO.builder()
                .name(from.getClass().getSimpleName())
                .message(from.getMessage())
                .build();
    }
}
