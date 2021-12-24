package ua.omniway.rest.exceptions;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Slf4j
@Provider
public class GeneralExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        log.error("Exception occurred in application: {}", e.getMessage(), e);
        return Response
                .status(Response.Status.BAD_REQUEST.getStatusCode())
                .type(MediaType.TEXT_PLAIN)
                .entity("Exception occurred in application: " + Throwables.getRootCause(e))
                .build();
    }
}

