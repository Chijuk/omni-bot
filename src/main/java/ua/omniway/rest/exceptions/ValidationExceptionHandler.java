package ua.omniway.rest.exceptions;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Slf4j
@Provider
public class ValidationExceptionHandler implements ExceptionMapper<ValidationException> {

    @Override
    public Response toResponse(ValidationException e) {
        log.error("Exception occurred in application: {}", e.getMessage(), e);
        final StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> cv : ((ConstraintViolationException) e).getConstraintViolations()) {
            strBuilder.append(cv.getPropertyPath().toString() + " " + cv.getMessage()).append("\n");
        }
        return Response
                .status(Response.Status.PRECONDITION_FAILED.getStatusCode()) // 412
                .type(MediaType.TEXT_PLAIN)
                .entity("Exception occurred in application: " + "\n" + strBuilder.toString())
                .build();
    }
}
