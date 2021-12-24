package ua.omniway.rest.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(10)
public class MessageValidationExceptionsAdvice {

    @ExceptionHandler(HttpMessageConversionException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public String handleException(HttpMessageConversionException e) {
        log.error(e.toString());
        return e.toString();
    }
}
