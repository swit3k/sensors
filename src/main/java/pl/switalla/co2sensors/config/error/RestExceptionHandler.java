package pl.switalla.co2sensors.config.error;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.switalla.co2sensors.sensor.http.exception.SensorNotFoundException;

/**
 * Exception Handlers
 */

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return buildBadRequest(ex.getMessage());
    }
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildBadRequest(ex.getMessage());
    }

    @ExceptionHandler({ IllegalArgumentException.class })
    protected ResponseEntity<Object> handleOthers(RuntimeException ex) {
        return buildBadRequest(ex.getMessage());
    }

    @ExceptionHandler({ SensorNotFoundException.class })
    protected ResponseEntity<Object> handle404(RuntimeException ex) {
        return new ResponseEntity<>(new HttpError(HttpStatus.NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<Object> buildBadRequest(String message) {
        return new ResponseEntity<>(new HttpError(HttpStatus.BAD_REQUEST, message), HttpStatus.BAD_REQUEST);
    }

}
