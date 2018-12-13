package br.eti.arthurgregorio.library.infrastructure.jaxrs;

import br.eti.arthurgregorio.library.domain.model.exception.WebserviceException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 13/12/2018
 */
public final class ResponseBuilder {

    private String entity;
    private UriInfo location;

    /**
     *
     */
    private ResponseBuilder(UriInfo location) {
        this.location = location;
    }

    /**
     *
     * @param location
     * @return
     */
    public static ResponseBuilder to(UriInfo location) {
        return new ResponseBuilder(location);
    }

    /**
     *
     * @param entity
     * @return
     */
    public ResponseBuilder withEntity(Object entity) {
        this.entity = String.valueOf(entity);
        return this;
    }

    /**
     *
     * @return
     */
    public Response created() {

        final UriBuilder builder = this.location.getAbsolutePathBuilder();

        if (this.entity == null) {
            throw new WebserviceException("The response entity can't be null");
        }

        builder.path(this.entity);

        return Response.created(builder.build()).entity(this.entity).build();
    }

    /**
     *
     * @return
     */
    public Response ok() {

        final UriBuilder builder = this.location.getAbsolutePathBuilder();

        if (this.entity != null) {
            builder.path(this.entity);
        }

        return Response.ok(builder.build()).build();
    }
}
