package br.eti.arthurgregorio.library.infrastructure.jaxrs.providers;

import br.eti.arthurgregorio.library.infrastructure.jaxrs.ErrorMessage;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Default exception handler, it may apply for any error in the webservices layer
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 12/12/2018
 */
@Provider
public class DefaultExceptionProvider implements ExceptionMapper<Throwable> {

    /**
     * {@inheritDoc}
     *
     * @param exception
     * @return
     */
    @Override
    public Response toResponse(Throwable exception) {
        return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(ErrorMessage.of(exception, Status.INTERNAL_SERVER_ERROR))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}