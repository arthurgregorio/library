package br.eti.arthurgregorio.library.infrastructure.jaxrs;

import br.eti.arthurgregorio.library.domain.exception.WebserviceException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Class to help building better responses for the webservices calls
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 13/12/2018
 */
public final class ResponseBuilder {

    private String entity;
    private UriInfo location;

    /**
     * Constructor...
     */
    private ResponseBuilder(UriInfo location) {
        this.location = location;
    }

    /**
     * Static factory method
     *
     * @param location the URI location
     * @return this builder
     */
    public static ResponseBuilder to(UriInfo location) {
        return new ResponseBuilder(location);
    }

    /**
     * The entity to be in the body of the response
     *
     * @param entity the entity
     * @return this builder
     */
    public ResponseBuilder withEntity(Object entity) {
        this.entity = String.valueOf(entity);
        return this;
    }

    /**
     * Create a new http-201 response
     *
     * @return the http-201 (created) response
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
     * Create a new http-200 response
     *
     * @return the http-200 (ok) response
     */
    public Response ok() {

        final UriBuilder builder = this.location.getAbsolutePathBuilder();

        if (this.entity != null) {
            builder.path(this.entity);
        }

        return Response.ok(builder.build()).build();
    }
}
