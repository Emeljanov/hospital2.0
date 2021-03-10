package by.emel.anton.restcontroller.v1;

import by.emel.anton.api.ResponseFailDTO;
import by.emel.anton.facade.converter.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionHandlerRestController {

    private final Converter<Exception, ResponseFailDTO> converter;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ResponseFailDTO exceptionHandler(Exception exception) {
        log.error("Controller exception :" + exception.getMessage());
        return converter.convert(exception);
    }

}
