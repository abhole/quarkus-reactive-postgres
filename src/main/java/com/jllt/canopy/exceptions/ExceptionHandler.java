package com.jllt.canopy.exceptions;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import javax.persistence.PersistenceException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.exception.JDBCConnectionException;
import org.jboss.logging.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletionException;

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {

    private static final Logger LOG = Logger.getLogger(ExceptionHandler.class);

    @Override
    public Response toResponse(Exception exception) {

        if(exception instanceof PersistenceException) {
            Throwable cause = exception.getCause();
            if(cause instanceof JDBCConnectionException ||
                    cause instanceof GenericJDBCException) {
                if(cause.getCause() != null && cause.getCause().getMessage().contains("FATAL")) {
                    LOG.fatal("Postgres Server is Down or unreachable");
                }
            }
        }

        if(exception instanceof CompletionException) {
            if(exception.getMessage().contains("Connection refused")
                    && Arrays.stream(exception.getStackTrace())
                    .anyMatch(stackTraceElement -> stackTraceElement.toString().contains("RedisClient"))) {
                LOG.fatal("Redis Server is Down or unreachable");
            }
        }

        if(exception instanceof NotAllowedException) {
            Set<String> methodSet = ((NotAllowedException) exception).getResponse().getAllowedMethods();
            String allowedMethods = String.join(",", methodSet);
            String message = "See headers for methods allowed on this resource";
            LOG.error(message);
            return Response.status(Response.Status.METHOD_NOT_ALLOWED)
                    .entity(new ErrorResponseBody(Response.Status.METHOD_NOT_ALLOWED, message))
                    .header(HttpHeaders.ALLOW, allowedMethods)
                    .build();
        }

        if(exception instanceof NotAuthorizedException) {
            List<Object> challenges = ((NotAuthorizedException) exception).getChallenges();
            String message = ObjectUtils.isNotEmpty(challenges)
                    ? challenges.get(0).toString()
                    : exception.getMessage();
            LOG.error(message);
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new ErrorResponseBody(Response.Status.UNAUTHORIZED, message))
                    .build();
        }

        if(exception instanceof ForbiddenException) {
            LOG.error(exception.getMessage());
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorResponseBody(Response.Status.FORBIDDEN,
                            exception.getMessage()))
                    .build();
        }

        if(exception instanceof BadRequestException
                || exception instanceof InvalidFormatException
                || exception instanceof JsonParseException
                || exception instanceof IllegalArgumentException
                || exception instanceof NotPermittedException) {
            LOG.error(exception.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponseBody(Response.Status.BAD_REQUEST, exception.getMessage()))
                    .build();
        }

        if(exception instanceof ResourceNotFoundException) {
            LOG.error(exception.getMessage());
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponseBody(Response.Status.NOT_FOUND, exception.getMessage()))
                    .build();
        }

        if (LOG.isDebugEnabled()) {
            LOG.error(Arrays.toString(exception.getStackTrace()));
        } else {
            LOG.error(exception);
        }

        LOG.errorf("Internal Server Error - %s ", exception.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponseBody(Response.Status.INTERNAL_SERVER_ERROR,"Something unexpected happened. Try again"))
                .build();
    }

    public record ErrorResponseBody(Response.Status status, String message) {

    }

}
