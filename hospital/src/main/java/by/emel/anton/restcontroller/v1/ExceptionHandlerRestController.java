package by.emel.anton.restcontroller.v1;

import by.emel.anton.api.v1.ResponseFailDTO;
import by.emel.anton.facade.converter.Converter;
import by.emel.anton.service.exception.EntityNotFoundHospitalServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionHandlerRestController {

    private final Converter<Exception, ResponseFailDTO> failConverter;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityNotFoundHospitalServiceException.class)
    public ResponseFailDTO EntityNotFoundExceptionHandler(EntityNotFoundHospitalServiceException e) {
        log.error("Entity not found exception :", e);
        return failConverter.convert(e);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseFailDTO AuthenticationExceptionHandler(AuthenticationException authenticationException) {
        log.error("Authentication Exception :", authenticationException);
        return failConverter.convert(authenticationException);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseFailDTO AccessDeniedExceptionHandler(AccessDeniedException e) {
        log.error("Access denied Exception :", e);
        return failConverter.convert(e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ResponseFailDTO exceptionHandler(Exception exception) {
        log.error("Controller exception :", exception);
        return failConverter.convert(exception);
    }
}
