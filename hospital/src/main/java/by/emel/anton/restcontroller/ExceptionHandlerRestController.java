package by.emel.anton.restcontroller;

import by.emel.anton.api.converter.Converter;
import by.emel.anton.api.exception.ResponseExceptionDTO;
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

    private final Converter<Exception, ResponseExceptionDTO> converter;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ResponseExceptionDTO exceptionHandler(Exception exception) {
        log.error("Controller exception :" + exception.getMessage());
        return converter.convert(exception);
    }

}
