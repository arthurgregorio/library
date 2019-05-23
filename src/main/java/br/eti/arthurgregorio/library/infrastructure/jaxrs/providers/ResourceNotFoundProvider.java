package br.eti.arthurgregorio.library.infrastructure.jaxrs.providers;

import br.eti.arthurgregorio.library.domain.exception.ResourceNotFoundException;
import br.eti.arthurgregorio.library.infrastructure.jaxrs.ErrorMessage;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Provider for {@link ResourceNotFoundException} exceptions
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 12/12/2018
 */
@Provider
public class ResourceNotFoundProvider implements ExceptionMapper<ResourceNotFoundException> {

    /**
     * {@inheritDoc}
     *
     * @param exception
     * @return
     */
    @Override
    public Response toResponse(ResourceNotFoundException exception) {

        final Status status = Status.fromStatusCode(exception.getHttpStatus());

        return Response.status(status)
                .entity(ErrorMessage.of(exception))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}